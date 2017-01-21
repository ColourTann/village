package tann.village.screens.gameScreen.panels.review;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import tann.village.util.Layoo;

public class InfoPanel extends Group{

	
	Layoo layoo;
	
	public InfoPanel() {
		layoo = new Layoo(this);
	}
	
	public Layoo getLayoo(){
		return layoo;
	}
	
	
	public void layout(){
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
	}
	
}
