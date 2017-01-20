package tann.village.screens.gameScreen.panels.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.NumberPanel;

public class InventoryItem extends Group{
	
	TextureRegion icon;
	static final int width = 80;
	static final int height = 80;
	static final int border = 4;
	
	public NumberPanel numberPanel;
	public InventoryItem(TextureRegion icon) {
		this.icon=icon;
		setSize(width, height);
		numberPanel = new NumberPanel();
		addActor(numberPanel);
		numberPanel.setPosition(getX()+width, getY()+getHeight()/2-numberPanel.getHeight()/2);
	}
	
	public void wisp(){
		numberPanel.wisp();
	}
	
	public void clearWisp(){
		numberPanel.clearWisp();
	}
	
	public void setValue(int value){
		numberPanel.setValue(value);
	}
	
	public void changeValue(int delta){
		numberPanel.changeValue(delta);
	}
	
	public int getValue(){
		return numberPanel.getValue();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.z_white);
		Draw.drawSize(batch, icon, getX()+border, getY()+border, getWidth()-border*2, getHeight()-border*2);
	}

}
