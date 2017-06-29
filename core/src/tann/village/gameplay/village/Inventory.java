package tann.village.gameplay.village;

import com.badlogic.gdx.utils.Array;
import tann.village.Images;
import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.island.objective.Objective;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.inventoryStuff.InventoryPanel;


public class Inventory{
	static final int GAP = 20;


	InventoryItem food;
	InventoryItem wood;
	InventoryItem morale;
	InventoryItem fate;
	InventoryItem gems;


	public Array<InventoryItem> items = new Array<>();

	private static final EffectType[] inventoryEffect = new EffectType[]{EffectType.Food, EffectType.Wood, EffectType.Morale, EffectType.Fate};
	

	public static final int ITEM_GAP=30;

	public Inventory() {
		food = new InventoryItem(Images.food, 0, 5);
		wood = new InventoryItem(Images.wood, 0, Integer.MAX_VALUE);
		morale = new InventoryItem(Images.morale);
		fate = new InventoryItem(Images.fate);

		morale.setValue(4);
		food.setValue(0);
		wood.setValue(0);
		fate.setValue(0);

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
	

	public void activate(Eff effect) {
		internalActivate(effect, false);
	}

	public void activateNegative(Eff effect) {
		internalActivate(effect, true);
	}
	
	private void internalActivate(Eff effect, boolean inverse){
	    if(effect.type == EffectType.Gem){
	        if(gems == null){
	            gems = new InventoryItem(Images.gem);
	            items.insert(0,gems);
	            getGroup().layout(true);
            }
            GameScreen.get().island.objectiveProgress(Objective.ObjectiveEffect.Gem, effect.value);
        }
		int value = effect.value*(inverse?-1:1);
		InventoryItem item = get(effect.type);
        if(item!=null) item.changeValue(value);
        switch(effect.type){
            case FoodStorage:
                get(Eff.EffectType.Food).addMax(value);
                break;
        }

	}

    public void addDelta(Eff e, boolean invert){
        InventoryItem item = get(e);
        if(item!=null){
            item.addDelta(e.value, invert);
        }
        if(e.type==EffectType.Brain){
            e.sourceDie.villager.addPotentialXP(e.value*(invert?-1:1));
        }
    }

    public void addDelta(Array<Eff> effects, boolean invert) {
        for (Eff e : effects) {
            addDelta(e, invert);
        }
    }

    public void addDelta(Eff[] effects, boolean invert) {
        for (Eff e : effects) {
            addDelta(e, invert);
        }
    }

    public void clearDeltas(){
	    for(InventoryItem item:items){
	        item.clearDelta();
        }
    }

	public InventoryItem get(Eff e){
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
        case Gem:
            return gems;

		}
		return null;
	}
	
	private boolean checkCostEffect(Eff e){
		 return get(e).getValue()>=e.value;

	}

	public boolean checkCost(Cost c){
		for(Eff e:c.effects){
			if(!checkCostEffect(e)) return false;
		}
		return true;
	}

	public void spendCost(Cost c){
		for(Eff e:c.effects){
			activateNegative(e);
		}
	}

	public int getResourceAmount(EffectType resourceType){
	    if(resourceType==EffectType.Gem && gems == null){
	        return 0;
        }
		return get(resourceType).getValue();
	}

	public void imposeLimits(){
		for(InventoryItem item:items){
			item.imposeMaximum();
		}
	}

	public void imposeFoodAndWoodMinimum(){
	    food.imposeMinimum();
	    wood.imposeMinimum();
    }

    public boolean isEffectValidAllowOvershoot(Eff e){
	    if(e.value>0) return true;
	    return isEffectValid(e);
    }
	
	public boolean isEffectValid(Eff e){
		if(get(e) == null) return true;
		return get(e).canChangeBy(e.value);
	}

    public void showWisps() {
	    for(InventoryItem ii: items){
	        ii.getPanel().wisp();
        }
    }

    public void resetWisps() {
        for(InventoryItem ii: items){
            ii.getPanel().clearWisp();
        }
    }

}
