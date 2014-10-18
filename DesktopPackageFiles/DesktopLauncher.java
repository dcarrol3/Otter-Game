package com.mygdx.game.desktop;

// Main game class for desktop, run from here!!

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.OtterGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "OtterGame";
		cfg.width = 800;
	    cfg.height = 480;
		new LwjglApplication(new OtterGame(), cfg);
	}
}
