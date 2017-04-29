package tann.village.gameplay.village;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import tann.village.Images;
import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.screens.gameScreen.panels.InventoryPanel;


public class Inventory{
	static final int GAP = 20;


	InventoryItem food;
	InventoryItem wood;
	InventoryItem morale;
	InventoryItem fate;

	public Array<InventoryItem> items = new Array<>();

	private static final EffectType[] inventoryEffect = new EffectType[]{EffectType.Food, EffectType.Wood, EffectType.Morale, EffectType.Fate};
	
	private static Inventory instance;
	public static Inventory get(){
		if(instance==null){
			instance = new Inventory();
		}
		return instance;
	}
	
	public static final int ITEM_GAP=30;

	public Inventory() {
		food = new InventoryItem(Images.food, 5);
		wood = new InventoryItem(Images.wood);
		morale = new InventoryItem(Images.morale);
		fate = new InventoryItem(Images.fate);

		morale.setValue(4);
		food.setValue(0);
		wood.setValue(0);
		fate.setValue(10);

		items.add(food);
		items.add(wood);
		items.add(morale);
		items.add(fate);
	}

	private InventoryPanel panel;
	public InventoryPanel getGroup(){
		if(panel==null) panel = new InventoryPanel(this);
		return panel;
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

	//TODO rename this after refactoring
	public void imposeMaximums(){
		for(InventoryItem item:items){
			item.imposeLimit();
		}
	}
	
	public boolean isEffectValid(Effect e){
		if(get(e) == null) return true;
		return get(e).canChangeBy(e.value);
	}

}
