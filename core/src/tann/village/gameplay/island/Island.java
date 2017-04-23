package tann.village.gameplay.island;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.Images;
import tann.village.gameplay.island.event.Event;
import tann.village.util.Colours;
import tann.village.util.Draw;

public class Island {

	public boolean available;
	public boolean explored;
	TextureRegion tr;
	int x,y;
	
	public Island(TextureRegion tr, int x, int y){
		this.tr=tr;
		this.x=x; this.y=y;
	}
	
	public void drawMask(Batch batch){
		int size = 200;
		Draw.drawSize(batch, Images.mask, x-size/2, Gdx.graphics.getHeight()-(y+size/2), size, size);
	}
	
	public void draw(Batch batch){
		batch.setColor(Colours.z_white);
		Draw.drawCentered(batch, tr, x, y);
	}

	public Event getEvent(){
		return null;
	}
}
