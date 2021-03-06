package com.pratikvelani.minesweeper.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.pratikvelani.minesweeper.toolbox.Assets;

/**
 * Created by pratikvelani
 */

public class TileActor extends BaseActor {

    public Vector3 point = new Vector3();
    public Matrix4 mat;

    public Vector3[] faceAngleList = new Vector3[] {
            new Vector3(0, 0, 0),
            new Vector3(90, 0, -90),
            new Vector3(90, 180, 0),
            new Vector3(90, 90, 0),
            new Vector3(90, 0, 90),
            new Vector3(90, -90, 0)
    };

    public Vector3 viewAngle = new Vector3();

    public int face = 0;
    public int type = 0;
    public GridPoint2 index = new GridPoint2();
    public int neighborCount = 0;
    public Boolean opened = false;

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

        //actor.materials.get(0).set(ColorAttribute.createAmbient(1.0f, 0f, 0f, 1.0f));

        Quaternion quaternion = new Quaternion();
        mat.getRotation(quaternion);

        /*Gdx.app.log("ROT", "START");
        Gdx.app.log("ROT", "X:" + quaternion.getPitch() + " Y:" + quaternion.getRoll() + " Z:" + quaternion.getYaw());
        Gdx.app.log("ROT", "END");*/

        actor.calculateTransforms();
        actor.updateBounds();
        return actor;
    }

    static public TileActor create (Matrix4 mat, int type) {
        Model model = Assets.getInstance ().getModel(Assets.MODEL_CUBES[type]);

        TileActor actor = new TileActor(model);
        actor.type = type;
        actor.mat = mat.cpy();
        actor.transform.set(mat);

        //actor.model.nodes.get(0).parts.get(0).material.set(ColorAttribute.createDiffuse(Color.RED));
        //Gdx.app.log("TA", actor.model.nodes.get(0).parts.get(0).material.get(TextureAttribute.Diffuse).toString());

        mat.getTranslation(actor.point);

        actor.calculateTransforms();
        actor.updateBounds();
        return actor;
    }

    /*@Override
    public void act(float deltaTime) {
        super.act(deltaTime);
    }*/

    public void showFace (int face) {
        if (face < faceAngleList.length && opened == false) {
            this.face = face;
            transform.set(mat);
            transform.rotate(Vector3.X, faceAngleList[face].x);
            transform.rotate(Vector3.Y, faceAngleList[face].y);
            transform.rotate(Vector3.Z, faceAngleList[face].z);
            calculateTransforms();
        }
    }

    public void open () {
        showFace(2);
        opened = true;
    }

    public Boolean isBomb() {
        return type == 5;
    }
}
