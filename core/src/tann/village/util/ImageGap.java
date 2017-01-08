package tann.village.util;

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
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.setColor(Colours.z_white);
		Draw.drawSize(batch, tr, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
	}

}
