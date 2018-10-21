package com.pratikvelani.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pratikvelani.minesweeper.GdxGame;
import com.pratikvelani.minesweeper.g3d.ClickAdapter;
import com.pratikvelani.minesweeper.toolbox.Assets;

import java.awt.Color;

public class GameScreen extends ClickAdapter implements Screen {
    private static final float FOV = 67F;
    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    public static final String TAG = GameScreen.class.getName();

    private GdxGame game;

    SpriteBatch batch;


    public GameScreen(GdxGame game) {
        super ();
        this.game = game;

        batch = new SpriteBatch();

        init ();
    }

    private void init () {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Assets.getInstance().getTexture(Assets.TEXTURE_BADLOGIC), 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
