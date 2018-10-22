package com.pratikvelani.minesweeper.actors;

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
 * Created by pratikvelani on 04/07/17.
 */

public class BaseActor extends ModelInstance {
    public final Vector3 center = new Vector3();
    public final Vector3 dimensions = new Vector3();
    public final float radius;

    private final static BoundingBox bounds = new BoundingBox();

    public BaseActor(Model model, Matrix4 transform) {
        super(model, transform);
        calculateBoundingBox(bounds);
        bounds.getCenter(center);
        bounds.getDimensions(dimensions);

        radius = dimensions.len() / 2f;
    }

    public void updateBounds () {
        calculateBoundingBox(bounds);
        bounds.getCenter(center);
        bounds.getDimensions(dimensions);
    }

    static public class Factory {

        static public TileActor createStack (Vector3 point, float length, float breadth, float height) {
            //float height = Constants.DEFAULT_WALL_HEIGHT / 2;

            Texture texture = Assets.getInstance().getTexture(Assets.TEXTURE_TILE);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Material material = new Material(TextureAttribute.createDiffuse(texture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(16f));
            long attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;

            ModelBuilder modelBuilder = new ModelBuilder();
            Model model = modelBuilder.createBox(length, height, breadth,
                    material, attributes);
            TileActor actor = new TileActor(model, new Matrix4().setToTranslation(point.x, point.y, point.z));
            actor.calculateTransforms();
            actor.updateBounds();
            return actor;
        }
    }
}
