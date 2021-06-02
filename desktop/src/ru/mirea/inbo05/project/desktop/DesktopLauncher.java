package ru.mirea.inbo05.project.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.mirea.inbo05.project.StarRealms;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1600;
		config.width = 2560;
		config.fullscreen = true;
		new LwjglApplication(new StarRealms(), config);
	}
}
