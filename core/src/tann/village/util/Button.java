package tann.village.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;


public class Button extends Actor{
	public static final int gap=2;
	String text;
	
	public Button(String text) {
		setup(text, -1);
	}
	
	public Button(String text, int width){
		setup(text, width);
	}
	
	public void setup(String text, int overrideWidth){
		this.text=text;
		setWidth(overrideWidth!=-1?overrideWidth:TannFont.font.getWidth(text)+gap*2);
		setHeight(TannFont.font.getHeight()+gap*2);
	}
	
	public void setClickAction(final Runnable r){
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				r.run();
				return true;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.setColor(Colours.light);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.dark);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		TannFont.font.draw(batch, text, (int)(getX()+getWidth()/2), (int)getY()+gap, Align.bottom);
	}

}
