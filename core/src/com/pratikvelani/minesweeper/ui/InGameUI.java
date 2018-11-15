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
    private Label label1;
    private TextButton button1, button2;

    private Vector3 position = new Vector3();
    public Matrix4 projectionMatrix = new Matrix4();
    public Matrix4 transform = new Matrix4();

    public InGameUI() {
        super();

        init ();
    }

    private void init () {
        spriteBatch = new SpriteBatch();

        label1 = new Label("Menu", Assets.getInstance().getUISkin());

        button1 = new TextButton("Restart", Assets.getInstance().getUISkin());
        button2 = new TextButton("Exit", Assets.getInstance().getUISkin());

        rootTable = new Table();
        /*rootTable.add(label1);
        rootTable.row();*/
        rootTable.add(button1);
        /*rootTable.row();
        rootTable.pad(30f).add(button2);*/
    }

    public void setCamera (PerspectiveCamera camera) {
        this.camera = camera;
    }

    public void render (float delta) {
        if (rootTable.isVisible()) {
            spriteBatch.setProjectionMatrix(projectionMatrix.set(camera.combined).mul(transform));
            spriteBatch.begin();
            rootTable.draw(spriteBatch, 1.0f);
            spriteBatch.end();
        }
    }

    /*public int getClickResult (int screenX, int screenY) {
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
    }*/

    public int getClickResult (int screenX, int screenY) {
        Ray ray = camera.getPickRay(screenX, screenY);
        int result = -1;
        float distance = -1;

        transform.getTranslation(position);

        if (Intersector.intersectRayBoundsFast(ray, position, new Vector3(40, 40, 20) )) {
            Gdx.app.log("RAY", "Table Clicked");
        }

        return result;
    }

    public void showStart () {
        label1.setText("Minesweeper");
        button1.setText("Restart");
        rootTable.setVisible(true);
    }

    public void showLevelComplete () {
        label1.setText("You Won!");
        button1.setText("Restart");
        rootTable.setVisible(true);
    }

    public void showLevelLost () {
        label1.setText("You Lost! Try Again.");
        button1.setText("Restart");
        rootTable.setVisible(true);
    }

    public void hide () {
        rootTable.setVisible(false);
    }
}
