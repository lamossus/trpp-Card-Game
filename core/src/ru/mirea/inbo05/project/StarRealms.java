package ru.mirea.inbo05.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mirea.inbo05.project.logic.GameState;
import ru.mirea.inbo05.project.logic.PlayerState;
import ru.mirea.inbo05.project.logic.cards.BaseInfo;
import ru.mirea.inbo05.project.logic.cards.Card;
import ru.mirea.inbo05.project.logic.cards.CardInfo;

import java.io.File;
import java.io.IOException;
import java.util.Random;

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

	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();
		stage = new Stage(new ScreenViewport());
		playerState = new PlayerState();
		enemyState = new PlayerState();
		gameState = new GameState();

		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor()));

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

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

		Gdx.input.setInputProcessor(stage);

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
