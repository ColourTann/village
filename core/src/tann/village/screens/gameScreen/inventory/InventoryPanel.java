package tann.village.screens.gameScreen.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import tann.village.Images;
import tann.village.Main;
import tann.village.screens.gameScreen.Effect;

public class InventoryPanel extends Group{
	static final int GAP = 20;
	
	
	InventoryItem food;
	InventoryItem wood;
	InventoryItem morale;
	InventoryItem fate;
	
	Array<InventoryItem> items = new Array<>();
	
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
		
		items.add(food);
		items.add(wood);
		items.add(morale);
		items.add(fate);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
//		batch.setColor(Colours.brown_dark);
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}

	public void activate(Effect effect) {
		switch(effect.type){
		case Food:
			food.changeValue(effect.value);
			break;
		case Morale:
			morale.changeValue(effect.value);
			break;
		case Skull:
			break;
		case Wood:
			wood.changeValue(effect.value);
			break;
		default:
			break;
		
		}
	}

	public void clearWisps() {
		for(InventoryItem i:items) i.clearWisp();
	}

	public void showWisps() {
		for(InventoryItem i:items) i.wisp();
	}

}
