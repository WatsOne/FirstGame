package ru.alexkulikov.firstfame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.alexkulikov.firstfame.FirstGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new FirstGame(), config);
		config.vSyncEnabled = true;
		config.width = 800;
		config.height = 600;
		config.samples = 3;
	}
}
