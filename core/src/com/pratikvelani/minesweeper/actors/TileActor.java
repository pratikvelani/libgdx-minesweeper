package com.pratikvelani.minesweeper.actors;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by pratikvelani on 04/07/17.
 */

public class TileActor extends BaseActor {

    public Vector3 point1;
    public Vector3 point2;

    public TileActor(Model model, Matrix4 transform) {
        super(model, transform);
    }

    public void updateStructure () {

    }
}
