package com.pratikvelani.minesweeper.toolbox;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by pratikvelani on 20/07/17.
 */

public class Assets {
    private static Assets instance = new Assets();

    static final public String TEXTURE_TILE = "tile.jpg";
    static final public String[] TEXTURE_NUMBERS = new String[] {"numbers/0.jpg", "numbers/1.jpg", "numbers/2.jpg", "numbers/3.jpg",
            "numbers/4.jpg", "numbers/5.jpg", "numbers/6.jpg", "numbers/7.jpg", "numbers/8.jpg", "numbers/9.jpg"};

    static final public String TEXTURE_NUMBERS_ATLAS = "numbers/numbers.txt";

    private AssetManager manager = new AssetManager();

    public static Assets getInstance() {
        return instance;
    }

    static public void createInstance () {
        instance = new Assets();
    }

    public void load () {
        manager.load(TEXTURE_TILE, Texture.class);

        manager.load(TEXTURE_NUMBERS[0], Texture.class);
        manager.load(TEXTURE_NUMBERS[1], Texture.class);
        manager.load(TEXTURE_NUMBERS[2], Texture.class);
        manager.load(TEXTURE_NUMBERS[3], Texture.class);
        manager.load(TEXTURE_NUMBERS[4], Texture.class);
        manager.load(TEXTURE_NUMBERS[5], Texture.class);
        manager.load(TEXTURE_NUMBERS[6], Texture.class);
        manager.load(TEXTURE_NUMBERS[7], Texture.class);
        manager.load(TEXTURE_NUMBERS[8], Texture.class);
        manager.load(TEXTURE_NUMBERS[9], Texture.class);

        manager.load(TEXTURE_NUMBERS_ATLAS, TextureAtlas.class);

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
}
