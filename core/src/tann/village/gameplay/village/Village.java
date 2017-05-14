package tann.village.gameplay.village;

import java.util.ArrayList;
import java.util.List;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.village.building.Building;
import tann.village.gameplay.village.building.BuildingEffect;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.RerollPanel;

public class Village {
	
	private List<Building> buildings  = new ArrayList<>();
	private static Village self;
	private RerollPanel panel;
    private List<Buff> buffs = new ArrayList<>();

	public static Village get(){
		if(self==null){
			self = new Village();
			self.setup();
		}
		return self;
	}

	public void setup(){

    }

	public void upkeep(){
		for(Building b:buildings){
			b.upkeep();
		}
        for(int i=buffs.size()-1;i>=0;i--){
            Buff b = buffs.get(i);
            b.upkeep();
            if(b.expired()){
                buffs.remove(b);
            }

        }
	}

	public void addBuilding(Building b) {
		buildings.add(b);
		if(GameScreen.get().island.objective!=null){
            GameScreen.get().island.objective.objectiveProgress(Objective.ObjectiveEffect.Building, 1);
        }
        for(BuildingEffect be:b.buildingEffects){
		    if(be.effectType== BuildingEffect.BuildingEffectType.EveryTurn){
		        for(Effect e:be.effects){
                    GameScreen.get().eachTurnPanel.addEffect(e);
                }
            }
        }
	}

    public void addBuff(Effect effect) {
        buffs.add(new Buff(effect));
    }

    public int getRerolls() {
        return 2 + getBonusRerolls();
    }

    private int getBonusRerolls(){
	    int total = Inventory.get().getResourceAmount(Effect.EffectType.Morale)/3;
	    for(Buff b : buffs){
	        if(b.buffType == Effect.EffectType.Reroll){
	            total += b.value;
            }
        }
        return total;
    }

    public int getNumBuildings(){
        return buildings.size();
    }

}
