package tann.village.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class TextWisp extends Actor{
	public String text;
	Color c = Colours.green_dark;
	final float initialDuration = 1.3f;
	float duration = initialDuration;
	public TextWisp(String text, float x, float y) {
		setPosition(x, y);
		this.text=text;
	}
	boolean alphaMode=true;
	public void disableAlpha(){
		alphaMode=false;
	}
	
	public void setColor(Color c){
		this.c=c;
	}
	
	static float speed=16;
	@Override
	public void act(float delta) {
		setY(getY()+delta*speed);
		duration-=delta;
		if(duration<=0){
			getParent().removeActor(this);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Fonts.font.setColor(c.r, c.g, c.b, duration/initialDuration);
		Fonts.font.draw(batch, text, getX(), getY(), 0, Align.center, false);
	}
	
}
