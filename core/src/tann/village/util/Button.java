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
	private float imageScale;
	public Button(float width, float height, int gap, float imageScale, final TextureRegion region, Color backgroundColour, final Runnable runnable) {
		this.region=region;
		this.backgroundColour=backgroundColour;
		this.imageScale=imageScale;
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
	
	public Button(float width, float height, int gap, final TextureRegion region, Color backgroundColour, final Runnable runnable) {
		this(width, height, gap, 1, region, backgroundColour, runnable);
	}
	
	public void setBackgroundColour(Color col){
		backgroundColour=col;
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(backgroundColour);
		Draw.fillRectangle(batch, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
		batch.setColor(getColor());
		Draw.drawSize(batch, region, 
				getX()+gap+(getWidth()-gap*2)*(1-imageScale)/2, 
				getY()+gap+(getHeight()-gap*2)*(1-imageScale)/2, 
				(getWidth()-gap*2)*imageScale, (getHeight()-gap*2)*imageScale);
		super.draw(batch, parentAlpha);
	}

}
