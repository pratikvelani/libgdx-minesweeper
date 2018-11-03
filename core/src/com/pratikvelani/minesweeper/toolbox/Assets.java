package com.pratikvelani.minesweeper.toolbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;

/**
 * Created by pratikvelani
 */

public class Assets {
    private static Assets instance = new Assets();

    static final public String TEXTURE_TILE = "tile.jpg";
    static final public String TEXTURE_CROSSHAIR = "crosshair.png";

    static final public String MODEL_CUBE = "cube/cube.obj";
    static final public String[] MODEL_CUBES = new String[] {
            "cube/0/cube.obj", "cube/1/cube.obj", "cube/2/cube.obj", "cube/3/cube.obj", "cube/4/cube.obj", "cube/5/cube.obj"
    };
    /*static final public String MODEL_CUBE_0 = "cube/0/cube.obj";
    static final public String MODEL_CUBE_1 = "cube/1/cube.obj";
    static final public String MODEL_CUBE_2 = "cube/2/cube.obj";
    static final public String MODEL_CUBE_3 = "cube/3/cube.obj";
    static final public String MODEL_CUBE_4 = "cube/4/cube.obj";
    static final public String MODEL_CUBE_5 = "cube/5/cube.obj";*/

    private AssetManager manager = new AssetManager();
    private Skin uiSkin;
    private HashMap<String, Model> modelCache = new HashMap<String, Model>();

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
        /*manager.load(MODEL_CUBE_0, Model.class);
        manager.load(MODEL_CUBE_1, Model.class);
        manager.load(MODEL_CUBE_2, Model.class);
        manager.load(MODEL_CUBE_3, Model.class);
        manager.load(MODEL_CUBE_4, Model.class);
        manager.load(MODEL_CUBE_5, Model.class);*/
        manager.load(MODEL_CUBES[0], Model.class);
        manager.load(MODEL_CUBES[1], Model.class);
        manager.load(MODEL_CUBES[2], Model.class);
        manager.load(MODEL_CUBES[3], Model.class);
        manager.load(MODEL_CUBES[4], Model.class);
        manager.load(MODEL_CUBES[5], Model.class);
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
        if (modelCache.containsKey(asset)) {
            return modelCache.get(asset);
        }

        Model model = manager.get(asset, Model.class);
        modelCache.put(asset, model);
        return model;
    }

    public Skin getUISkin () {
        if (uiSkin == null) {
             uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        }

        return uiSkin;
    }
}
