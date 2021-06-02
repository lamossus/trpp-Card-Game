package ru.mirea.inbo05.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mirea.inbo05.project.logic.GameState;
import ru.mirea.inbo05.project.logic.PlayerState;
import ru.mirea.inbo05.project.logic.cards.Base;
import ru.mirea.inbo05.project.logic.cards.BaseInfo;
import ru.mirea.inbo05.project.logic.cards.CardInfo;

import java.io.IOException;

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
	Group enemyBases;

	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor()));

		enemyBases = new Group();

		batch = new SpriteBatch();
		assets = new Assets();
		stage = new Stage(new ScreenViewport());

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		Gdx.input.setInputProcessor(stage);
		playerState = new PlayerState();
		enemyState = new PlayerState();
		gameState = new GameState();

		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

		try {
			playerState = om.readValue(Gdx.files.internal("json/playerState.json").file(), PlayerState.class);
			enemyState = om.readValue(Gdx.files.internal("json/playerState.json").file(), PlayerState.class);
			gameState = om.readValue(Gdx.files.internal("json/gameState.json").file(), GameState.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		playerState.shuffle();
		enemyState.shuffle();
		gameState.shuffle();


		for (int i = 0; i < 3; i++)
			playerState.draw();

		gameState.refill();

		createButtons();

		stage.addActor(enemyBases);
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
				{
					if(card.mainEffect != null)
						card.mainEffect.setActive(true);
					if(card.allyEffect != null)
						card.allyEffect.setActive(true);
					if(card.trashEffect != null)
						card.trashEffect.setActive(true);
					playerState.discard(card.instance);
				}

				playerState.playedCards.clear();

				for (CardInfo card : playerState.hand)
					playerState.discard(card.instance);
				playerState.hand.clear();


				PlayerState temp = enemyState;
				enemyState = playerState;
				playerState = temp;

				for (Actor actor : enemyBases.getChildren())
					actor.remove();
				for (Actor actor : playerState.basesGroup.getChildren())
					actor.remove();

				while (playerState.hand.size() < 5 && !(playerState.discardDeck.isEmpty() && playerState.deck.isEmpty())) {
					playerState.draw();
				}

				setHealth(playerState.getHealth());
				setEnemyHealth(enemyState.getHealth());

				int i = 0;

				for (final BaseInfo baseInfo : enemyState.bases)
				{
					final Image button = new Image(new TextureRegionDrawable(assets.getTexture(baseInfo.textureName)));

					int width = Gdx.graphics.getWidth();
					int height = Gdx.graphics.getHeight();

					button.setScale(0.55f);
					button.setRotation(-90);
					button.setPosition(width - (1 + i) * button.getHeight() * button.getScaleY(), height/2f + button.getWidth() * button.getScaleX(), Align.bottomLeft); // Расположить карту над рукой. Надо бы сделать покрасивше

					enemyBases.addActor(button);

					button.addListener(new ClickListener() {
						@Override
						public void clicked(InputEvent event, float x, float y) {
							for (int i = 0; i < enemyState.bases.size(); i++)
							{
								BaseInfo otherBase = enemyState.bases.get(i);
								if (!baseInfo.isTaunt && otherBase.isTaunt && otherBase != baseInfo)
									return;
							}

							if (baseInfo.health <= playerState.getAttack())
							{
								button.remove();
								enemyState.bases.remove(baseInfo);
								enemyState.discard(baseInfo.instance);

								StarRealms.setAttack(playerState.getAttack() - baseInfo.health);
							}
						}
					});

					i++;
				}
				i = 0;
				for (BaseInfo baseInfo : playerState.bases)
				{
					Base base =(Base) baseInfo.instance;
					base.clearListeners(); // Очистить список слушателей, чтобы разыгранную карту нельзя было разыграть ещё раз
					base.remove();

					int width = Gdx.graphics.getWidth();
					int height = Gdx.graphics.getHeight();

					base.setScale(0.55f);
					base.setRotation(-90);
					base.setPosition(width - (1 + i) * base.getHeight() * base.getScaleY(), height/2f, Align.bottomLeft); // Расположить карту над рукой. Надо бы сделать покрасивше

					StarRealms.playerState.basesGroup.addActor(base);
					base.createEffectButtons();

					i++;
				}

				enemyHealthPoints.remove(); // Я пытался сделать clearListeners() но почему-то из-за этого кнопка перестаёт работать
				enemyHealthPoints = new TextButton("Health: " + enemyState.getHealth(), assets.getSkin());
				enemyHealthPoints.setTransform(true);
				enemyHealthPoints.setScale(2);
				enemyHealthPoints.setPosition(endTurn.getX() - (healthPoints.getScaleX() - 1) * healthPoints.getWidth(), height - enemyHealthPoints.getHeight() * enemyHealthPoints.getScaleY(),  Align.bottomRight);
				enemyHealthPoints.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						setEnemyHealth(enemyState.getHealth() - playerState.getAttack());
						setAttack(0);
					}
				});
				stage.addActor(enemyHealthPoints);
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
		enemyHealthPoints.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enemyState.setHealth(enemyState.getHealth() - playerState.getAttack());
				playerState.setAttack(0);
			}
		});
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

		if (enemyState.getHealth() <= 0)
		{
			for (Actor actor : stage.getActors())
				actor.remove();
			TextArea win = new TextArea("You win!", assets.getSkin());
		}
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
