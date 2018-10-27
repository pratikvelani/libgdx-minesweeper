package com.pratikvelani.minesweeper.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.pratikvelani.minesweeper.toolbox.Assets;

/**
 * Created by pratikvelani
 */

public class BaseActor extends ModelInstance {
    public final Vector3 center = new Vector3();
    public final Vector3 dimensions = new Vector3();
    public float radius;

    public BoundingBox bounds = new BoundingBox();

    public BaseActor(Model model) {
        super(model);

        calculateBoundingBox(bounds);
        bounds.getCenter(center);
        bounds.getDimensions(dimensions);

        //radius = dimensions.len();
        radius = 50;

        //Gdx.app.log("ROT", "" + radius);
    }

    public BaseActor(Model model, Matrix4 transform) {
        super(model, transform);
        calculateBoundingBox(bounds);
        bounds.getCenter(center);
        bounds.getDimensions(dimensions);

        radius = dimensions.len() / 2f;
    }

    public BaseActor(Model model, String nodeId, boolean mergeTransform) {
        super(model, nodeId, mergeTransform);

        calculateBoundingBox(bounds);
        bounds.getCenter(center);
        bounds.getDimensions(dimensions);

        //Gdx.app.log("ROT", "" + bounds);

        radius = dimensions.len() / 2f;
    }

    public void updateBounds () {
        calculateTransforms();
        calculateBoundingBox(bounds);
        bounds.mul(transform);
        bounds.getCenter(center);
        bounds.getDimensions(dimensions);

        radius = dimensions.len() / 2f;

        //Gdx.app.log("ROT", "" + bounds);
    }

    public void act (float deltaTime) {

    }
}
