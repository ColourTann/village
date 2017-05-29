package tann.village.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Aller_Lt.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 20;
		fontSmall = generator.generateFont(parameter); // font size 12 pixels
        parameter.size=39;
        font= generator.generateFont(parameter); // font size 12 pixels
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Cinzel-Regular.otf"));
        parameter.size=55;
		fontBig = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
	}

	public static void draw(Batch batch, String string, BitmapFont font, Color col, float x, float y, float width, float height){
	    draw(batch,string, font,col,x,y,width,height,Align.center);
    }

    public static void draw(Batch batch, String string, BitmapFont font, Color col, float x, float y, float width, float height, int align){
        font.setColor(col);
        font.draw(batch, string, x, y+height/2+font.getCapHeight()/2, width, align, true);
    }
}
