package com.pratikvelani.minesweeper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pratikvelani.minesweeper.screens.GameScreen;
import com.pratikvelani.minesweeper.toolbox.Assets;

public class GdxGame extends Game {
	static private GdxGame mInstance;

	public Screen screen;

	public static synchronized GdxGame init () {
		mInstance = new GdxGame ();
		return mInstance;
	}

	@Override
	public void create () {
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();

		Assets.createInstance ();
		Assets.getInstance().loadSync();

		showGameScreen();
	}

	@Override
	public void dispose () {
		Assets.getInstance().dispose();
		super.dispose();
	}

	private void showGameScreen () {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				screen = new GameScreen(GdxGame.this);
				setScreen(screen);
			}
		});
	}
}
