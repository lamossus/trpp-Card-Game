package ru.mirea.inbo05.project.desktop;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameApplication extends Game {
    public SpriteBatch batcher;

    @Override
    public void create() {
        batcher = new SpriteBatch();
    }

}
