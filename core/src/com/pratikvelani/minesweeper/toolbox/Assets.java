package com.pratikvelani.minesweeper.toolbox;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;

/**
 * Created by pratikvelani
 */

public class Assets {
    private static Assets instance = new Assets();

    static final public String TEXTURE_TILE = "tile.jpg";

    static final public String MODEL_CUBE = "cube/cube.obj";

    private AssetManager manager = new AssetManager();

    public static Assets getInstance() {
        return instance;
    }

    static public void createInstance () {
        instance = new Assets();
    }

    public void load () {
        manager.load(TEXTURE_TILE, Texture.class);

        manager.load(MODEL_CUBE, Model.class);

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

    public TextureAtlas getTextureAtlas (String name) {
        return manager.get(name, TextureAtlas.class);
    }

    public Model getModel (String asset) {
        return manager.get(asset, Model.class);
    }
}
