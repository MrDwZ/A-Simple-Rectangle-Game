package com.mrdwz.drop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mrdwz.drop.Drop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		int WINDOW_X = 800, WINDOW_Y = 800;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "A simple rectangle game";
		config.height = WINDOW_Y;
		config.width  = WINDOW_X;
		config.resizable = false;
		new LwjglApplication(new Drop(), config);
	}
}
