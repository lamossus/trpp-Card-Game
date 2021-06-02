package ru.mirea.inbo05.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();
		stage = new Stage(new ScreenViewport());
		playerState = new PlayerState();
		enemyState = new PlayerState();
		gameState = new GameState();

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		// Тестовое, потом удалить
		Random rand = new Random();

		for (int i = 0; i < 30; i ++)
		{
			int random = rand.nextInt() % 2;
			if (random == 1)
			{
				final CardInfo testCard = new CardInfo("Corvette.png");
				gameState.tradeDeck.add(testCard);
			}
			else
			{
				final BaseInfo testCard = new BaseInfo("Capitol World.png");
				gameState.tradeDeck.add(testCard);
			}
		}
		for (int i = 0; i < 10; i ++)
		{
			final CardInfo testCard = new CardInfo("Scout.png");
			playerState.deck.add(testCard);
		}
		for (int i = 0; i < 5; i++)
			playerState.draw();

		gameState.refill();

		Gdx.input.setInputProcessor(stage);

		final TextButton endTurn = new TextButton("End turn", assets.getSkin());
		endTurn.setPosition(width, height/2f + 10, Align.right);
		endTurn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				playerState.setMoney(0);
				playerState.setAttack(0);

				for (CardInfo card : playerState.playedCards)
					playerState.discard(card.instance);
				playerState.playedCards.clear();

				for (CardInfo card : playerState.hand)
					playerState.discard(card.instance);
				playerState.hand.clear();

				while (playerState.hand.size() < 5) {
					playerState.draw();
				}
			}
		});
		StarRealms.stage.addActor(endTurn);
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
}
