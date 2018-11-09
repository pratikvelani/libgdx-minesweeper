package com.pratikvelani.minesweeper.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.pratikvelani.minesweeper.toolbox.Assets;

public class InGameUI {

    private PerspectiveCamera camera;
    private SpriteBatch spriteBatch;

    private Table rootTable;
    private TextButton restartButton, exitButton;

    private Vector3 position = new Vector3();
    public Matrix4 projectionMatrix = new Matrix4();
    public Matrix4 transform = new Matrix4();

    public InGameUI() {
        super();

        init ();
    }

    private void init () {
        spriteBatch = new SpriteBatch();

        Label nameLabel = new Label("Menu", Assets.getInstance().getUISkin());

        restartButton = new TextButton("Restart", Assets.getInstance().getUISkin());
        exitButton = new TextButton("Exit", Assets.getInstance().getUISkin());

        rootTable = new Table();
        rootTable.add(nameLabel);
        rootTable.row();
        rootTable.pad(30f).add(restartButton);
        rootTable.row();
        rootTable.pad(30f).add(exitButton);


    }

    public void setCamera (PerspectiveCamera camera) {
        this.camera = camera;
    }

    public void render (float delta) {
        spriteBatch.setProjectionMatrix(projectionMatrix.set(camera.combined).mul(transform));
        spriteBatch.begin();
        rootTable.draw(spriteBatch, 1.0f);
        spriteBatch.end();
    }

    public int getClickResult (int screenX, int screenY) {
        Ray ray = camera.getPickRay(screenX, screenY);
        int result = -1;
        float distance = -1;

        transform.getTranslation(position);
        //transform.scale(exitButton.getWidth(), exitButton.getHeight(), 1);

        //Gdx.app.log("SCALE", "" + transform.getScaleX()+"::"+transform.getScaleY()+"::"+ transform.getScaleZ());
        //Gdx.app.log("SCALE", exitButton.getWidth() +":" + exitButton.getHeight());
        //Gdx.app.log ("UP", "" + rootTable.stageToLocalCoordinates(new Vector2(screenX, screenY)));

        if (Intersector.intersectRayBoundsFast(ray, position, new Vector3(40, 40, 20) )) {
            Gdx.app.log("CHECK", "" + true);
        }

        return result;
    }
}
