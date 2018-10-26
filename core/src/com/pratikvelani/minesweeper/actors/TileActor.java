package com.pratikvelani.minesweeper.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.pratikvelani.minesweeper.toolbox.Assets;

/**
 * Created by pratikvelani
 */

public class TileActor extends BaseActor {

    public Vector3 point1;
    public Vector3 point2;

    public Vector3 point;

    public TileActor(Model model) {
        //super(model, transform);
        super(model);
    }

    static public TileActor create (Vector3 point, float length, float breadth, float height) {
        //float height = Constants.DEFAULT_WALL_HEIGHT / 2;

        Texture texture = Assets.getInstance().getTexture(Assets.TEXTURE_TILE);

        /*TextureAtlas textureAtlas = Assets.getInstance().getTextureAtlas(Assets.TEXTURE_NUMBERS_ATLAS);
        TextureRegion textureRegion = textureAtlas.findRegion("1");

        int index = (int) (Math.random() * 10);
        Gdx.app.debug("TileActor", "" + index);*/
        //Texture texture = Assets.getInstance().getTexture(Assets.TEXTURE_NUMBERS[index]);
        //TextureRegion textureRegion = new TextureRegion(texture);
        /*texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);*/

        /*Material material = new Material(TextureAttribute.createDiffuse(texture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(16f));

        long attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        ModelBuilder modelBuilder = new ModelBuilder();

        Model model = modelBuilder.createBox(length, height, breadth, material, attributes);

        TileActor actor = new TileActor(model);
        actor.transform.translate(point.x, point.y, point.z);
        actor.calculateTransforms();
        actor.updateBounds();
        return actor;*/

        /*int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        modelBuilder.node();
        *//*modelBuilder.part("box", GL20.GL_TRIANGLES,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates,
                new Material(TextureAttribute.createDiffuse(textureRegion)))
                .box(2f, 2f, 2f);*//*
        MeshPartBuilder mpb = modelBuilder.part("box", GL20.GL_TRIANGLES, attr, new Material(TextureAttribute.createDiffuse(textureRegion)));
        BoxShapeBuilder.build(mpb, length, breadth, height);
        Model model = modelBuilder.end();

        TileActor actor = new TileActor(model, new Matrix4().setToTranslation(point.x, point.y, point.z));
        //actor.calculateTransforms();
        //actor.updateBounds();
        return actor;*/

        Matrix4 mat = new Matrix4();
        //mat.setFromEulerAngles(0, 0, 90).trn(point.x, point.y, point.z).scl(4, 4, 4);

        Model model = Assets.getInstance ().getModel(Assets.MODEL_CUBE);

        TileActor actor = new TileActor(model);
        actor.point = point;

        actor.transform.setToTranslation(point.x, point.y, point.z);
        actor.transform.scale(4, 4, 4);
        actor.transform.rotate(Vector3.X, 0);
        actor.transform.rotate(Vector3.Y, 0);
        actor.transform.rotate(Vector3.Z, 0);

        Quaternion quaternion = new Quaternion();
        mat.getRotation(quaternion);

        /*Gdx.app.log("ROT", "START");
        Gdx.app.log("ROT", "X:" + quaternion.getPitch() + " Y:" + quaternion.getRoll() + " Z:" + quaternion.getYaw());
        Gdx.app.log("ROT", "END");*/

        actor.calculateTransforms();
        actor.updateBounds();
        return actor;
    }

    /*@Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }*/
}
