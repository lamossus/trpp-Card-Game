package ru.mirea.inbo05.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
import ru.mirea.inbo05.project.logic.cards.Card;

import java.util.Random;

public class StarRealms extends ApplicationAdapter {
	public static Assets assets;
	public static Stage stage;

	public static PlayerState playerState = new PlayerState();
	public static PlayerState enemyState = new PlayerState();
	public static GameState gameState = new GameState();

	final static int width = Gdx.graphics.getWidth();
	final static int height = Gdx.graphics.getHeight();

	SpriteBatch batch;
	Color test = new Color((float) 0.537, (float) 0.756, (float) 0.439, (float) 0.5);

	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();
		stage = new Stage(new ScreenViewport());

		// Тестовое, потом удалить
		Random rand = new Random();

		for (int i = 0; i < 30; i ++)
		{
			final Card testCard = new Card(rand.nextInt() % 2 == 0 ? "alliance-transport.jpg" : "trade-star.jpg");
			gameState.tradeDeck.add(testCard);
		}
		for (int i = 0; i < 10; i ++)
		{
			final Card testCard = new Card("alliance-transport.jpg");
			playerState.deck.add(testCard);
		}
		for (int i = 0; i < 5; i++)
			playerState.draw();

		gameState.refill();

		Gdx.input.setInputProcessor(stage);

		final TextButton endTurn = new TextButton("End turn", assets.getSkin());
		endTurn.setPosition(width/2f, height/2f + 10, Align.center);
		endTurn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				playerState.setMoney(0);
				playerState.setAttack(0);

				playerState.discardDeck.addAll(playerState.playedCards);
				playerState.playedCards.clear();

				while (playerState.hand.size() != 5) {
					playerState.draw();
				}
			}
		});
		StarRealms.stage.addActor(endTurn);
	}

	@Override
	public void render ()
	{
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
