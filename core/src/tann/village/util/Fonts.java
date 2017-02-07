package tann.village.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;

import tann.village.Main;

public class Fonts {
	
	public static BitmapFont fontSmall;
	public static BitmapFont font;
	public static BitmapFont fontBig;
	
	public static void setup(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/bloodcrow.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 22;
		fontSmall = generator.generateFont(parameter); // font size 12 pixels
		fontSmall.setColor(Colours.light);
		parameter.size=40;
		font= generator.generateFont(parameter); // font size 12 pixels
		font.setColor(Colours.light);
		parameter.size=55;
		fontBig = generator.generateFont(parameter); // font size 12 pixels
		fontBig.setColor(Colours.light);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		
	}
}
