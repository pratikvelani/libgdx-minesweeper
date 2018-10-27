package com.pratikvelani.minesweeper.toolbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by pratikvelani
 */

public class Assets {
    private static Assets instance = new Assets();

    static final public String TEXTURE_TILE = "tile.jpg";
    static final public String TEXTURE_CROSSHAIR = "crosshair.png";

    static final public String MODEL_CUBE = "cube/cube.obj";

    private AssetManager manager = new AssetManager();

    private Skin uiSkin;

    public static Assets getInstance() {
        return instance;
    }

    static public void createInstance () {
        instance = new Assets();
    }

    public void load () {
        manager.load(TEXTURE_TILE, Texture.class);
        manager.load(TEXTURE_CROSSHAIR, Texture.class);

        manager.load(MODEL_CUBE, Model.class);

    }

    public void loadSync () {
        load();
        manager.finishLoading();
    }

    public void dispose () {
        manager.dispose();

        if (uiSkin != null) {
            uiSkin.dispose();
        }
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

    public Skin getUISkin () {
        if (uiSkin == null) {
             uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        }

        return uiSkin;
    }
}
