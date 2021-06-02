package ru.mirea.inbo05.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mirea.inbo05.project.logic.GameState;
import ru.mirea.inbo05.project.logic.PlayerState;
import ru.mirea.inbo05.project.logic.cards.Base;
import ru.mirea.inbo05.project.logic.cards.BaseInfo;
import ru.mirea.inbo05.project.logic.cards.Card;
import ru.mirea.inbo05.project.logic.cards.CardInfo;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class StarRealms extends ApplicationAdapter {
	public static Assets assets;
	public static Stage stage;

	public static PlayerState playerState;
	public static PlayerState enemyState;
	public static GameState gameState;

	static int width;
	static int height;

	SpriteBatch batch;
	Color test = new Color((float) 0.537, (float) 0.756, (float) 0.439, (float) 0.5);

	static TextButton healthPoints, enemyHealthPoints, moneyPoints, attackPoints;

	Client client;
	Server server;

	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor()));

		batch = new SpriteBatch();
		assets = new Assets();
		stage = new Stage(new ScreenViewport());

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		Gdx.input.setInputProcessor(stage);
	}

	void host()
	{
		playerState = new PlayerState();
		enemyState = new PlayerState();
		gameState = new GameState();

		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

		try {
			playerState = om.readValue(new File("playerState.json"), PlayerState.class);
			enemyState = om.readValue(new File("playerState.json"), PlayerState.class);
			gameState = om.readValue(new File("gameState.json"), GameState.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 3; i++)
			playerState.draw();

		gameState.refill();

		createButtons();

		server = new Server();
		server.start();
		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Kryo kryo = server.getKryo();
		kryo.register(GameStateRequest.class);

		server.addListener(new Listener(){
			@Override
			public void received(Connection connection, Object object) {
				if (object instanceof GameStateRequest)
				{
					playerState = ((GameStateRequest) object).enemyState;
					enemyState = ((GameStateRequest) object).playerState;
					gameState = ((GameStateRequest) object).gameState;

					ArrayList<BaseInfo> newBases = playerState.bases;

					setHealth(playerState.getHealth());
					for (Actor actor : playerState.basesGroup.getChildren())
					{
						actor.remove();
						playerState.bases.remove(((Base)actor).getCardInfo());
					}
					for (BaseInfo baseInfo : playerState.bases)
					{
						baseInfo.instance.play();
						for (Actor actor : StarRealms.playerState.basesGroup.getChildren())
							actor.setTouchable(Touchable.disabled);
					}
					for (Actor actor : enemyState.basesGroup.getChildren())
						actor.remove();
					int i = 0;
					for (BaseInfo baseInfo : enemyState.bases)
					{
						Base base = (Base)baseInfo.instance;

						int width = Gdx.graphics.getWidth();
						int height = Gdx.graphics.getHeight();

						base.setScale(0.55f);
						base.setRotation(-90);
						base.setPosition(width - (1 + i) * base.getHeight() * base.getScaleY(), height/2f + base.getWidth(), Align.bottomLeft); // Расположить карту над рукой. Надо бы сделать покрасивше

						StarRealms.playerState.basesGroup.addActor(base);
						i++;
					}
					i = 0;
					if (enemyState.yourTurn)
					{
						for (CardInfo cardInfo : enemyState.playedCards)
						{
							Card card = cardInfo.instance;
							card.setScale(0.6f);
							card.setPosition(i * card.getWidth() * card.getScaleX(), card.getHeight() * 0.7f, Align.bottomLeft); // Расположить карту над рукой. Надо бы сделать покрасивше
							StarRealms.playerState.playedCards.add(cardInfo);
							StarRealms.playerState.hand.remove(cardInfo);

							StarRealms.playerState.playedCardsGroup.addActor(card);
							i++;
						}
					}
					if (playerState.yourTurn)
					{
						for (Actor actor : StarRealms.stage.getActors())
							actor.setTouchable(Touchable.enabled);
					}
				}
			}
		});
	}

	void connect()
	{
		createButtons();

		for (Actor actor : StarRealms.stage.getActors())
			actor.setTouchable(Touchable.enabled);

		for (int i = 0; i < 5; i++)
			playerState.draw();

		client = new Client();
		InetAddress address = client.discoverHost(54777, 5000);
		client.start();
		try {
			client.connect(5000, address, 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Kryo kryo = client.getKryo();
		kryo.register(GameStateRequest.class);

		client.addListener(new Listener(){
			@Override
			public void received(Connection connection, Object object) {
				if (object instanceof GameStateRequest)
				{
					playerState = ((GameStateRequest) object).enemyState;
					enemyState = ((GameStateRequest) object).playerState;
					gameState = ((GameStateRequest) object).gameState;

					ArrayList<BaseInfo> newBases = playerState.bases;

					setHealth(playerState.getHealth());
					for (Actor actor : playerState.basesGroup.getChildren())
					{
						actor.remove();
						playerState.bases.remove(((Base)actor).getCardInfo());
					}
					for (BaseInfo baseInfo : playerState.bases)
					{
						baseInfo.instance.play();
						for (Actor actor : StarRealms.playerState.basesGroup.getChildren())
							actor.setTouchable(Touchable.disabled);
					}
					for (Actor actor : enemyState.basesGroup.getChildren())
						actor.remove();
					int i = 0;
					for (BaseInfo baseInfo : enemyState.bases)
					{
						Base base = (Base)baseInfo.instance;

						int width = Gdx.graphics.getWidth();
						int height = Gdx.graphics.getHeight();

						base.setScale(0.55f);
						base.setRotation(-90);
						base.setPosition(width - (1 + i) * base.getHeight() * base.getScaleY(), height/2f + base.getWidth(), Align.bottomLeft); // Расположить карту над рукой. Надо бы сделать покрасивше

						StarRealms.playerState.basesGroup.addActor(base);
						i++;
					}
					i = 0;
					if (enemyState.yourTurn)
					{
						for (CardInfo cardInfo : enemyState.playedCards)
						{
							Card card = cardInfo.instance;
							card.setScale(0.6f);
							card.setPosition(i * card.getWidth() * card.getScaleX(), card.getHeight() * 0.7f, Align.bottomLeft); // Расположить карту над рукой. Надо бы сделать покрасивше
							StarRealms.playerState.playedCards.add(cardInfo);
							StarRealms.playerState.hand.remove(cardInfo);

							StarRealms.playerState.playedCardsGroup.addActor(card);
							i++;
						}
					}
					if (playerState.yourTurn)
					{
						for (Actor actor : StarRealms.stage.getActors())
							actor.setTouchable(Touchable.enabled);
					}
				}
			}
		});
	}

	void createButtons()
	{
		final TextButton endTurn = new TextButton("End turn", assets.getSkin());
		endTurn.setTransform(true);
		endTurn.setScale(2);
		endTurn.setPosition(width - (endTurn.getScaleX() - 1) * endTurn.getWidth(), 0, Align.bottomRight);
		endTurn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setMoney(0);
				setAttack(0);

				for (CardInfo card : playerState.playedCards)
					playerState.discard(card.instance);
				playerState.playedCards.clear();

				for (CardInfo card : playerState.hand)
					playerState.discard(card.instance);
				playerState.hand.clear();

				while (playerState.hand.size() < 5 && !(playerState.discardDeck.isEmpty() && playerState.deck.isEmpty())) {
					playerState.draw();
				}
				playerState.yourTurn = false;
				enemyState.yourTurn = true;

				for (Actor actor : stage.getActors())
					actor.setTouchable(Touchable.disabled);

				GameStateRequest request = new GameStateRequest();
				request.gameState = gameState;
				request.playerState = playerState;
				request.enemyState = enemyState;

				if (server != null)
					server.sendToAllTCP(request);
				if (client != null)
					client.sendTCP(request);
			}
		});

		healthPoints = new TextButton("Health: " + playerState.getHealth(), assets.getSkin());
		healthPoints.setTransform(true);
		healthPoints.setScale(2);
		healthPoints.setPosition(endTurn.getX() - (healthPoints.getScaleX() - 1) * healthPoints.getWidth(), 0,  Align.bottomRight);

		moneyPoints = new TextButton("Money: " + playerState.getMoney(), assets.getSkin());
		moneyPoints.setTransform(true);
		moneyPoints.setScale(2);
		moneyPoints.setPosition(endTurn.getX() - (moneyPoints.getScaleX() - 1) * moneyPoints.getWidth(), healthPoints.getHeight() * healthPoints.getScaleY(),  Align.bottomRight);

		attackPoints = new TextButton("Attack: " + playerState.getAttack(), assets.getSkin());
		attackPoints.setTransform(true);
		attackPoints.setScale(2);
		attackPoints.setPosition(endTurn.getX() - (moneyPoints.getScaleX() - 1) * moneyPoints.getWidth(), healthPoints.getHeight() * healthPoints.getScaleY() + moneyPoints.getHeight() * moneyPoints.getScaleY(),  Align.bottomRight);


		enemyHealthPoints = new TextButton("Health: " + enemyState.getHealth(), assets.getSkin());
		enemyHealthPoints.setTransform(true);
		enemyHealthPoints.setScale(2);
		enemyHealthPoints.setPosition(endTurn.getX() - (healthPoints.getScaleX() - 1) * healthPoints.getWidth(), height - enemyHealthPoints.getHeight() * enemyHealthPoints.getScaleY(),  Align.bottomRight);

		StarRealms.stage.addActor(endTurn);
		StarRealms.stage.addActor(healthPoints);
		StarRealms.stage.addActor(moneyPoints);
		StarRealms.stage.addActor(enemyHealthPoints);
		StarRealms.stage.addActor(attackPoints);
	}

	@Override
	public void render ()
	{
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && server == null && client == null)
			host();
		if (Gdx.input.isKeyJustPressed(Input.Keys.W) && server == null && client == null)
			connect();
		Gdx.gl.glClearColor(test.r,test.g,test.b,test.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
		assets.dispose();
	}

	public static void setHealth(int health)
	{
		playerState.setHealth(health);
		healthPoints.setText("Health: " + playerState.getHealth());
	}
	public static void setEnemyHealth(int health)
	{
		enemyState.setHealth(health);
		enemyHealthPoints.setText("Health: " + enemyState.getHealth());
	}
	public static void setMoney(int money)
	{
		playerState.setMoney(money);
		moneyPoints.setText("Money: " + playerState.getMoney());
	}
	public static void setAttack(int attack)
	{
		playerState.setAttack(attack);
		attackPoints.setText("Attack: " + playerState.getAttack());
	}
}
