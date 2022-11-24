package ru.fefelov.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.fefelov.SpaceGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 500;
		config.height = 700;
		config.title = "Space Game";
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new SpaceGame(), config);
	}
}
