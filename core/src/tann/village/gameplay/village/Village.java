package tann.village.gameplay.village;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.village.building.Building;
import tann.village.gameplay.village.building.BuildingEffect;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.rollStuff.RerollPanel;
import tann.village.util.Sounds;

public class Village {
	
	private List<Building> buildings  = new ArrayList<>();
	private static Village self;
	private RerollPanel panel;
    private Inventory inventory;
    public Upkeep getUpkeep() {
        return upkeep;
    }

    private Upkeep upkeep;

    private int dayNum=0;
	public static Village get(){
		if(self==null){
			self = new Village();
			self.setup();
		}
		return self;
	}

	public int getDayNum(){
	    return dayNum;
    }

    public void nextDay(){
	    dayNum++;
            GameScreen.get().island.objectiveProgress(Objective.ObjectiveEffect.Turn, 1);
    }

	public static Inventory getInventory(){
	    return get().inventory;
    }

	public void setup(){
	    dayNum=0;
        buildings.clear();
        inventory = new Inventory();
        upkeep= new Upkeep();
    }

	public void upkeep(){
		for(Building b:buildings){
			b.upkeep();
		}
	}

	public void addBuilding(Building b) {
        Sounds.playSound(Sounds.build,1,1);
        buildings.add(b);
        GameScreen.get().island.objectiveProgress(Objective.ObjectiveEffect.Building, 1);
        for(BuildingEffect be:b.buildingEffects){
		    if(be.effectType== BuildingEffect.BuildingEffectType.EveryTurn){
		        for(Eff e:be.effects){
                    activateEffect(e);
                }
            }
        }
        //todo this??
//        GameScreen.get().tsp.addTurnEffects();
	}

    public int getRerolls() {
        return 2 + getBonusRerolls();
    }

    private int getBonusRerolls(){
	    int total = inventory.getResourceAmount(Eff.EffectType.Morale)/3;
//	    for(Buff b : buffs){
//	        if(b.buffType == Eff.EffectType.Reroll){
//	            total += b.value;
//            }
//        }
        //todo rerolls
        return total;
    }

    public int getNumBuildings(){
        return buildings.size();
    }

    public void process(Eff effect) {
//        for(Buff b:buffs){
//            b.process(effect);
//        }
        //todo process
    }

    public void activateEffect(Eff eff) {
        System.out.println(eff);
        switch(eff.effAct.type){
            case NOW:
                internalActuallyActivate(eff);
                break;
            case UPKEEP:
                addToUpkeepp(eff);
                break;
            case IN_TURNS:
            case FOR_TURNS:
            case PASSIVE:
                addTurnEff(eff);
                break;
        }
    }

    private void addTurnEff(Eff eff){
        System.out.println("adding"+eff);
        GameScreen.get().tsp.addTurnEffects(eff);
    }

    private void addToUpkeepp(Eff eff){
        upkeep.addEffect(eff);
    }

    private void internalActuallyActivate(Eff eff){
        getInventory().activate(eff);
        int value = eff.value;
        switch(eff.type){
            case Brain:
                eff.sourceDie.villager.gainXP(value);
                break;
        }
    }
}
