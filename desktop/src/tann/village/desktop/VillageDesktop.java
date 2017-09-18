package tann.village.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import tann.village.Main;

public class VillageDesktop {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.samples=13;
		config.resizable=true;
		config.vSyncEnabled=true;
		config.foregroundFPS=60;
		config.width=Main.width;
		config.height=Main.height;
		config.title="Village";
		config.addIcon("icon.png", FileType.Internal);
//		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		new LwjglApplication(new Main(), config);
	}
}
