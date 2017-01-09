package tann.village.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageGap extends Actor{
	
	TextureRegion tr;
	float gap;
	public ImageGap(TextureRegion tr, float width, float height, float gap) {
		this.tr=tr;
		setSize(width, height);
		this.gap=gap;
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
		Draw.drawSize(batch, tr, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
	}


}
