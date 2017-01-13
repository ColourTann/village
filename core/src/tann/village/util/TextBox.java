package tann.village.util;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class TextBox extends Actor{
	
	public static HashMap<BitmapFont, Float> fontHeights;
	static{
		fontHeights = new HashMap<>();
		for(BitmapFont font:new BitmapFont[]{Fonts.font, Fonts.fontBig, Fonts.fontSmall}){
			TextBox tb = new TextBox("hi", font, 500, Align.center);
			fontHeights.put(font, tb.getHeight());
		}
	}
	
	
	String text;
	GlyphLayout layout = new GlyphLayout();
	BitmapFont font;
	int align;
	Color textCol = Colours.light;
	public TextBox(String text, BitmapFont font, float maxWidth, int align){
		if(maxWidth==-1) maxWidth = 99999;
		this.align=align;
		this.text=text;
		this.font=font;
		layout.setText(font, text, Colours.light, maxWidth, align, true);
		setSize(Math.min(maxWidth, layout.width), layout.height);
	}
	
	public void setTextColour(Color col){
		this.textCol = col;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
//		batch.setColor(1,0,1,.5f);
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		font.setColor(textCol);
		font.draw(batch, text, getX(), getY()+getHeight(), layout.width,  align, true);
		super.draw(batch, parentAlpha);
	}
	
}
