package tann.village.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class VillagePack {
	public static void main(String[] args){
	Settings settings = new Settings();
	settings.combineSubdirectories = false;
	settings.maxWidth=2048;
	settings.maxHeight=2048;
	TexturePacker.process(settings, "../images", "../android/assets", "atlas_image");


	settings.minWidth=2048;
	settings.minHeight=2048;
	settings.paddingX=2;
	settings.paddingY=2;
	settings.combineSubdirectories=true;
	TexturePacker.process(settings, "../images_3d", "../android/assets/3d", "atlas_image");
	}
}