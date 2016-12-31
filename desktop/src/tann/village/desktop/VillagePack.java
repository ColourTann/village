package tann.village.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class VillagePack {
	public static void main(String[] args){
	Settings settings = new Settings();
	settings.combineSubdirectories = false;
	TexturePacker.process(settings, "../images", "../android/assets", "atlas_image");
	}
}
