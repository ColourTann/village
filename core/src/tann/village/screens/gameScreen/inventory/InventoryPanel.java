package tann.village.screens.gameScreen.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

import tann.village.Images;
import tann.village.Main;
import tann.village.util.Colours;
import tann.village.util.Draw;

public class InventoryPanel extends Group{
	static final int GAP = 20;
	
	
	public InventoryItem food;
	public InventoryItem wood;
	InventoryItem morale;
	InventoryItem fate;
	
	static final int ITEM_GAP=30;
	
	public InventoryPanel() {
		setSize(150, InventoryItem.height*4+ITEM_GAP*3);
		setPosition(GAP, Main.height/2-getHeight()/2);
		addActor(food = new InventoryItem(Images.food));
		addActor(wood = new InventoryItem(Images.wood));
		addActor(morale = new InventoryItem(Images.morale));
		addActor(fate = new InventoryItem(Images.fate));
		
		morale.setY(InventoryItem.height+GAP);
		wood.setY(InventoryItem.height*2+GAP*2);
		food.setY(InventoryItem.height*3+GAP*3);
		
		morale.setValue(10);
		food.setValue(6);
		wood.setValue(2);
		fate.setValue(1);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
//		batch.setColor(Colours.brown_dark);
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}

}
