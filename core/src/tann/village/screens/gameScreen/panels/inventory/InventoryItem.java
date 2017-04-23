package tann.village.screens.gameScreen.panels.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

import tann.village.screens.gameScreen.NumberPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;

public class InventoryItem extends Group{
	
	TextureRegion icon;
	static final int width = 80;
	static final int height = 80;
	static final int border = 4;
	int min;
	public NumberPanel numberPanel;
	public InventoryItem(TextureRegion icon, int max) {
		this(icon, max, 0);
	}
	
	public InventoryItem(TextureRegion icon, int max, int min) {
		this.min=min;
		this.icon=icon;
		setSize(width, height);
		numberPanel = new NumberPanel(max);
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

	public void imposeMaximum() {
		if(numberPanel.max>0) setValue(Math.min(getValue(), numberPanel.max));
	}
	
	public boolean canChangeBy(int amount){
		int potentialTotal = amount + getValue();
		return (potentialTotal <= numberPanel.max) && (potentialTotal  >= min); 
	}
}
