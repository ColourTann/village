package tann.village.screens.gameScreen.panels.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import tann.village.Images;
import tann.village.Main;
import tann.village.screens.gameScreen.Cost;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.effect.Effect;

public class Inventory{
	static final int GAP = 20;


	InventoryItem food;
	InventoryItem wood;
	InventoryItem morale;
	InventoryItem fate;

	Array<InventoryItem> items = new Array<>();

	private static Inventory instance;
	public static Inventory get(){
		if(instance==null){
			instance = new Inventory();
		}
		return instance;
	}
	
	public static final int ITEM_GAP=30;
	private Group g;
	public Inventory() {
		
		g = new Group();
		
		
		g.setSize(InventoryItem.width, InventoryItem.height*4+ITEM_GAP*3);
		g.setPosition(GameScreen.BUTTON_BORDER, Main.height/2-g.getHeight()/2);
		food = new InventoryItem(Images.food);
		g.addActor(food);
		g.addActor(wood = new InventoryItem(Images.wood));
		g.addActor(morale = new InventoryItem(Images.morale));
		g.addActor(fate = new InventoryItem(Images.fate));

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

	public Group getGroup(){
		return g;
	}
	

	public void activate(Effect effect) {
		internalActivate(effect, false);
	}

	public void activateNegative(Effect effect) {
		internalActivate(effect, true);
	}
	
	private void internalActivate(Effect effect, boolean inverse){
		int value = effect.value*(inverse?-1:1);
		switch(effect.type){
		case Food:
			food.changeValue(value);
			break;
		case Morale:
			morale.changeValue(value);
			break;
		case Skull:
			break;
		case Wood:
			wood.changeValue(value);
			break;
		default:
			break;
		}
	}
	
	
	public InventoryItem get(Effect e){
		switch(e.type){
		case Food:
			return food;
		case Morale:
			return morale;
		case Wood:
			return wood;
		case Fate:
			return fate;

		}

		return null;
	}
	
	private boolean checkCostEffect(Effect e){
		 return get(e).getValue()>=e.value;

	}

	public boolean checkCost(Cost c){
		for(Effect e:c.effects){
			if(!checkCostEffect(e)) return false;
		}
		return true;
	}

	public void spendCost(Cost c){
		for(Effect e:c.effects){
			activateNegative(e);
		}
	}

	

	public void clearWisps() {
		for(InventoryItem i:items) i.clearWisp();
	}

	public void showWisps() {
		for(InventoryItem i:items) i.wisp();
	}

}
