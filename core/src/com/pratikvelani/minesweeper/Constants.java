package com.pratikvelani.minesweeper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class Constants {
    public static final float WORLD_SIZE = 1000.0f;
    public static final float WORLD_WIDTH = 1024.0f;
    public static final float WORLD_HEIGHT = 600.0f;
    public static final Color BACKGROUND_COLOR = Color.BLUE;

    public static final float M2P = 30.0f;
    public static final float P2M = 0.03333333333f;

    public static final float m2p(float m) {
        return Math.round(m * M2P * 10) / 10;
    }

    public static final float p2m(float p) {
        return Math.round(p * P2M * 10) / 10;
    }

    public static final int THREED_SCALE = 10;

}