package tann.village.screens.gameScreen.panels.review;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import tann.village.util.Colours;
import tann.village.util.Draw;

public class InfoPanel extends Group{

	public static final int BORDER= 3;
	
	protected Color background = Colours.dark;
	
	public void setBackground(Color col){
		this.background=col;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.brown_dark);
		Draw.fillRectangle(batch, getX()-BORDER, getY()-BORDER, getWidth()+BORDER*2, getHeight()+BORDER*2);
		batch.setColor(background);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}
	
}
