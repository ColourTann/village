package tann.village.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.Main;

public class ImageActor extends BasicLay{
	
	public TextureRegion tr;
    public ImageActor(TextureRegion tr, float width, float height) {
        this.tr=tr;
        setSize(width, height);
    }

    public ImageActor(TextureRegion tr) {
        this(tr, tr.getRegionWidth(), tr.getRegionHeight());
    }

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
        batch.setColor(getColor());
        if(flash){
            float freq = 3.5f;
            batch.setColor(Colours.withAlpha(getColor(), (float)(Math.sin(Main.ticks*freq)+1)/2));
        }
        Draw.drawSize(batch, tr, getX(), getY(), getWidth(), getHeight());
	}


    @Override
    public void layout() {

    }

    boolean flash;
    public void flash(boolean flash) {
        System.out.println("flash");
        this.flash=flash;
    }

}
