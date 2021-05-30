package ru.mirea.inbo05.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.mirea.inbo05.project.logic.GameState;
import ru.mirea.inbo05.project.logic.PlayerState;
import ru.mirea.inbo05.project.logic.cards.Card;

public class StarRealms extends ApplicationAdapter {
	public static Assets assets;
	public static Stage stage;

	SpriteBatch batch;
	Color test = new Color(1,0,0,1);

	public static PlayerState playerState = new PlayerState(), enemyState = new PlayerState();
	public static GameState gameState = new GameState();

	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		assets = new Assets();
		stage = new Stage(new ScreenViewport());

		// Тестовое, потом удалить
		for (int i = 0; i < 30; i ++)
		{
			final Card testCard = new Card("alliance-transport.jpg");
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
	}
}
