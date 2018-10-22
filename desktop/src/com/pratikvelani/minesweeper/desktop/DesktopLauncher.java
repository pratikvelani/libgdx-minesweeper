package com.pratikvelani.minesweeper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pratikvelani.minesweeper.Constants;
import com.pratikvelani.minesweeper.GdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Constants.WORLD_WIDTH;
		config.height = (int) Constants.WORLD_HEIGHT;
		new LwjglApplication(new GdxGame(), config);
	}
}
