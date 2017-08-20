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


	Array<Eff> potentialEffects = new Array<>();
	void activate(Eff e, boolean activateNow, boolean invert){
	    if(activateNow){
            switch(e.type){
                case Brain:
                    e.sourceDie.villager.gainXP(e.value);
                    break;
                case Gem:
                    if(gems == null){
                        gems = new InventoryItem(Images.gem);
                        items.insert(0,gems);
                        getGroup().layout(true);
                    }
                    Village.get().objectiveProgress(Objective.ObjectiveEffect.Gem, e.value);
                break;
            }
            int value = e.value*(invert?-1:1);
            InventoryItem item = get(e.type);
            if(item!=null) item.changeValue(value);
            switch(e.type){
                case FoodStorage:
                    get(Eff.EffectType.Food).addMax(value);
                    break;
            }
        }
        else{
	        // potential zone

             if(invert){
                 if (e.type == EffectType.Brain) {
                     e.sourceDie.villager.addPotentialXP(e.value * (invert ? -1 : 1));
                 }
	            boolean removed = potentialEffects.removeValue(e, true);
	            if(!removed){
	                System.err.println("Failed to remove "+e);
                }
            }
            else {
                 if (e.type == EffectType.Brain) {
                     e.sourceDie.villager.addPotentialXP(e.value * (invert ? -1 : 1));
                 }
                potentialEffects.add(e);
                InventoryItem item = get(e);
                if (item != null) item.addDelta(e.value, invert);
            }
        }
    }



    public void activateAndclearDeltas(){
	    for(Eff e:potentialEffects){
	        Village.get().activate(e, true);
        }
        potentialEffects.clear();
	    // potential xp stuff?
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
		    activate(e, true,true);
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

    /*
      public void activateEffect(Eff eff, boolean asNow) {
        if(asNow){
            internalActuallyActivate(eff);
            return;
        }
        switch(eff.effAct.type){
            case NOW:
                internalActuallyActivate(eff);
                break;
            case UPKEEP:
                addToUpkeepp(eff);
                break;
            case FOR_TURNS:
                getInventory().addDelta(eff, false);
            case IN_TURNS:
            case PASSIVE:
                addTurnEff(eff);
                break;
        }
    }
     */
}
