package tann.village.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageGap extends Actor{
	
	TextureRegion tr;
	public ImageGap(TextureRegion tr, float width, float height) {
		this.tr=tr;
		setSize(width, height);
	}
	
	Color background;
	public void setBackground(Color col) {
		this.background=col;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(background!=null){
			batch.setColor(background);
			Draw.fillActor(batch, this);
		}
		batch.setColor(Colours.z_white);
		Draw.drawSize(batch, tr, getX(), getY(), getWidth(), getHeight());
	}


}
