package tann.village.screens.gameScreen.panels.inventory;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import tann.village.Images;
import tann.village.Main;
import tann.village.screens.gameScreen.Cost;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectType;

public class Inventory{
	static final int GAP = 20;


	InventoryItem food;
	InventoryItem wood;
	InventoryItem morale;
	InventoryItem fate;

	Array<InventoryItem> items = new Array<>();

	private static final EffectType[] inventoryEffect = new EffectType[]{EffectType.Food, EffectType.Wood, EffectType.Morale, EffectType.Fate};
	
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
		food = new InventoryItem(Images.food, 5);
		g.addActor(food);
		g.addActor(wood = new InventoryItem(Images.wood, 20));
		g.addActor(morale = new InventoryItem(Images.morale, 10));
		g.addActor(fate = new InventoryItem(Images.fate, 6, -6));

		morale.setY(InventoryItem.height+GAP);
		wood.setY(InventoryItem.height*2+GAP*2);
		food.setY(InventoryItem.height*3+GAP*3);

		morale.setValue(4);
		food.setValue(0);
		wood.setValue(0);
		fate.setValue(0);

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
		InventoryItem item = get(effect.type);
		if(item!=null) item.changeValue(value);
	}
	
	public InventoryItem get(Effect e){
		return get(e.type);
	}
	
	public InventoryItem get(EffectType type){
		switch(type){
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

	public int getResourceAmount(EffectType resourceType){
		return get(resourceType).getValue();
	}

	public void clearWisps() {
		for(InventoryItem i:items) i.clearWisp();
	}

	public void showWisps() {
		for(InventoryItem i:items) i.wisp();
	}

	public void resetFood() {
		get(EffectType.Food).setValue(0);
	}
	
	public void imposeMaximums(){
		for(InventoryItem item:items){
			item.imposeMaximum();
		}
	}
	
	public boolean isEffectValid(Effect e){
		if(get(e) == null) return true;
		return get(e).canChangeBy(e.value);
	}

}
