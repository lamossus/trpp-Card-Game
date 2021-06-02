package ru.mirea.inbo05.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    AssetManager manager = new AssetManager();

    public Assets()
    {
        FileHandle[] files = Gdx.files.internal("images/cards").list();
        for (FileHandle file : files) {
            String stringPath = file.path();
            manager.load(stringPath, Texture.class);
        }
        manager.load("uiskin.json", Skin.class);
        manager.finishLoading();
    }

    /** Получить текстуру по её названию */
    public Texture getTexture(String name)
    {
        if (manager.isLoaded("images/cards/" + name, Texture.class))
            return manager.get("images/cards/" + name, Texture.class);
        return null;
    }

    public Skin getSkin()
    {
        return manager.get("uiskin.json", Skin.class);
    }

    /** Избавиться от всех загруженных ресурсов */
    public void dispose()
    {
        manager.dispose();
    }
}
