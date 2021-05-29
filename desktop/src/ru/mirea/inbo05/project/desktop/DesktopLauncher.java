package ru.mirea.inbo05.project.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.mirea.inbo05.project.StarRealms;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 960;
		config.width = 1280;
		new LwjglApplication(new StarRealms(), config);
	}
}
