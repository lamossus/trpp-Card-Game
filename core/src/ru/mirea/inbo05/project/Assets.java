package ru.mirea.inbo05.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;

public class Assets {
    AssetManager manager = new AssetManager();

    final String FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    public Assets()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("default.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        FileHandle[] files = Gdx.files.internal("images").list();
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
        if (manager.isLoaded("images/" + name, Texture.class))
            return manager.get("images/" + name, Texture.class);
        return null;
    }

    public Skin getSkin()
    {
        return manager.get("uiskin.json", Skin.class);
    }
}
