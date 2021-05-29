package ru.mirea.inbo05.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Assets {
    AssetManager manager = new AssetManager();

    ArrayList<String> loadedTextures = new ArrayList<>();

    public Assets()
    {
        FileHandle[] files = Gdx.files.internal("images").list();
        for (FileHandle file : files) {
            String stringPath = file.path();
            manager.load(stringPath, Texture.class);
            loadedTextures.add(stringPath.substring(stringPath.lastIndexOf("/") + 1));
        }
        manager.finishLoading();
    }

    public Texture getTexture(String name) throws Exception {
        if (manager.isLoaded("images/" + name, Texture.class))
        {
            return manager.get("images/" + name, Texture.class);
        }
        throw new Exception(name + " texture does not exist");
    }
}
