package tann.village.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Button extends Group{
	private TextureRegion region;
	private int gap;
	private Color backgroundColour = Colours.dark;
	public Button(float width, float height, int gap, final TextureRegion region, Color backgroundColour, final Runnable runnable) {
		this.region=region;
		this.backgroundColour=backgroundColour;
		this.gap=gap;
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				runnable.run();
				return true;
			}
		});
		setSize(width, height);
	}
	
	public void setBackgroundColour(Color col){
		backgroundColour=col;
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(backgroundColour);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(getColor());
		Draw.drawSize(batch, region, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
		super.draw(batch, parentAlpha);
	}

}
