package tann.village.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class TextBox extends Actor{
	String text;
	GlyphLayout layout = new GlyphLayout();
	BitmapFont font;
	int border;
	int align;
	Color textCol = Colours.light;
	public TextBox(String text, BitmapFont font, int border, int maxWidth, int align){
		this.align=align;
		this.text=text;
		this.font=font;
		this.border=border;
		layout.setText(font, text, Colours.light, maxWidth, align, true);
		setSize(Math.min(maxWidth, layout.width)+border*2, layout.height+border*2);
	}
	
	public void setTextColour(Color col){
		this.textCol = col;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
//		batch.setColor(1,0,1,.5f);
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		font.setColor(textCol);
		font.draw(batch, text, getX()+border, getY()+getHeight()-border, layout.width,  align, true);
		super.draw(batch, parentAlpha);
	}
	
}
