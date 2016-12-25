package tann.village.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;

public class TextWisp extends Particle{
	public String text;
	Color c = Colours.light;
	public int width;
	public TextWisp(String text, int x, int y) {
		this.x=x;
		this.y=y;
		this.text=text;
		setupLife(1);
		width = TannFont.font.getWidth(text);		
	}
	boolean alphaMode=true;
	public void disableAlpha(){
		alphaMode=false;
	}
	
	public void setColor(Color c){
		this.c=c;
	}
	
	static float speed=10;
	@Override
	public void tick(float delta) {
		y+=delta*speed;
	}
	
	public void refresh(){
		setupLife(.5f);
	}
	
	public void setText(String text){
		this.text=text;
		refresh();
	}

	@Override
	public void draw(Batch batch) {
		batch.setColor(c.r, c.g, c.b, alphaMode?ratio:1);
		TannFont.font.draw(batch, text, (int)x, (int)y, Align.center);
	}

	
}
