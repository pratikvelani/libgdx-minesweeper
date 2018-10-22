package com.pratikvelani.minesweeper.toolbox;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by pratikvelani on 20/07/17.
 */

public class Assets {
    private static Assets instance = new Assets();

    static final public String TEXTURE_BADLOGIC = "badlogic.jpg";
    static final public String TEXTURE_TILE = "tile.jpg";

    private AssetManager manager = new AssetManager();

    public static Assets getInstance() {
        return instance;
    }

    static public void createInstance () {
        instance = new Assets();
    }

    public void load () {
        manager.load(TEXTURE_BADLOGIC, Texture.class);
        manager.load(TEXTURE_TILE, Texture.class);
    }

    public void loadSync () {
        load();
        manager.finishLoading();
    }

    public void dispose () {
        manager.dispose();
    }

    public Texture getTexture (String asset) {
        return manager.get(asset, Texture.class);
    }
}
