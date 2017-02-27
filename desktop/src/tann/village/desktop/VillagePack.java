package tann.village.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class VillagePack {
	public static void main(String[] args){
	Settings settings = new Settings();
	settings.combineSubdirectories = false;
	settings.maxWidth=2048;
	settings.maxHeight=2048;
	settings.edgePadding=false;
	settings.paddingX=0;
	settings.paddingY=0;
	TexturePacker.process(settings, "../images", "../android/assets", "atlas_image");
	}
}