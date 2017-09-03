package tann.village.gameplay.village;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.village.project.Project;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.eventStuff.JoelDebugPanel;
import tann.village.screens.gameScreen.panels.bottomBar.ObjectivePanel;
import tann.village.screens.gameScreen.panels.rollStuff.RerollPanel;
import tann.village.screens.gameScreen.panels.villagerStuff.VillagerIcon;
import tann.village.util.Sounds;

public class Village {
	
	private List<Project> buildings  = new ArrayList<>();
	private static Village self;
	private RerollPanel panel;
    private Inventory inventory;
    public Upkeep getUpkeep() {
        return upkeep;
    }
    private float joel;
    private Upkeep upkeep;
    private Array<Buff> buffs = new Array<Buff>();

    public static final int STARTING_VILLAGERS = 5;
    public Array<Villager> villagers = new Array<>();

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
	    getObjectivePanel().objectiveProgress(Objective.ObjectiveEffect.Turn, 1);
	    tickBuffs();
    }

    private void tickBuffs(){
        for(int i=buffs.size-1;i>=0;i--){
            Buff b = buffs.get(i);
            b.turn();
            if(b.dead){
                buffs.removeValue(b, true);
            }
        }
    }

	public static Inventory getInventory(){
	    return get().inventory;
    }


    Array<Eff> potentialEffects = new Array<>();

    public void activate(Eff[] effs, boolean activateNow, boolean invert){for(Eff e:effs){activate(e, activateNow, invert);}}
    public void activate(Array<Eff> effs, boolean activateNow, boolean invert){for(Eff e:effs){activate(e, activateNow, invert);}}
    public void activate(Array<Eff> effs, boolean activateNow){for(Eff e:effs){activate(e, activateNow, false);}}
    public void activate(Eff e, boolean activateNow){
        activate(e, activateNow, false);
    }
    public void activate(Eff e, boolean activateNow, boolean invert){
        doEffectStuff(e,activateNow,invert);
        refreshDelta();
    }

    private void doEffectStuff(Eff e, boolean activateNow, boolean invert){
        if(e.effAct!=null) {
            switch (e.effAct.type) {
                case FOR_TURNS:
                    activate(e.copy().now(), false);
                case IN_TURNS: // fallthrough
                    addTurnEff(e.copy());
                    return;
            }
        }
        if(e.type==EffectType.Buff){
            e.getBuff().resetTurns();
            if(invert) buffs.removeValue(e.getBuff(), true);
            else buffs.add(e.getBuff());
            return;
        }

        if(activateNow){
            switch(e.type){
                case Brain:
                    e.sourceDie.villager.gainXP(e.value);
                    break;
            }
            int value = e.value*(invert?-1:1);
            InventoryItem item = getInventory().get(e.type);
            if(item!=null) item.changeValue(value);
        }
        else{
            // potential zone
            if(invert){
                boolean removed = potentialEffects.removeValue(e, true);
                if(!removed){
                    System.err.println("Failed to remove "+e);
                }
            }
            else {
                potentialEffects.add(e);
            }
        }
    }


    private void actuallyActivate(Eff e){

    }


    private void refreshDelta(){
        calculateDelta();
        getInventory().setDeltas(deltaMap);
        for(Villager v:villagers){
            v.setDelta(deltaMap);
        }
    }

    private AddSub getFromMap(Object key){
        AddSub as = deltaMap.get(key);
        if(as==null){
            as = new AddSub();
            deltaMap.put(key, as);
        }
        return as;
    }

    private Map<Object, AddSub> deltaMap = new HashMap<>();
    private void calculateDelta(){
        for(AddSub ad:deltaMap.values()){
            ad.reset();
        }
        for(int i=0;i<potentialEffects.size;i++){
            Eff e = potentialEffects.get(i);
            processBuffs(e);
            Object key = null;
            switch(e.type){

                case Food:
                case Wood:
                case Morale:
                case FoodStorage:
                case Fate:
                case Gem:
                    key = e.type;
                    break;
                case Brain:
                    key = e.sourceDie.villager;
                    break;
                case Reroll:
                case Buff:
                    // hmm not sure
                    break;
            }
            AddSub as = getFromMap(key);
            as.add(e.getAdjustedValue());
        }
    }

    private void processBuffs(Eff e){
        e.resetBonus();
        for(Buff b:buffs){
            b.process(e);
        }
    }

    public void actionPotential() {
        calculateDelta();
        potentialEffects.clear();
        for(Villager v:villagers){
            AddSub as = deltaMap.get(v);
            if(as!=null){
                v.gainXP(as.getTotal());
            }
        }
        getInventory().actionPotential(deltaMap);
    }


	public void setup(){
	    dayNum=0;
        buildings.clear();
        inventory = new Inventory();
        upkeep= new Upkeep();
        villagers.clear();
        for(int i=0;i<STARTING_VILLAGERS;i++){
            villagers.add(new Villager(i));
        }
    }

    public void startOfRoll(){
	    GameScreen.get().tsp.startOfRolling();
    }

	public void addBuilding(Project b) {
        Sounds.playSound(Sounds.build,1,1);
        buildings.add(b);
        getObjectivePanel().objectiveProgress(Objective.ObjectiveEffect.Building, 1);
	}

    public int getRerolls() {
        return 2 + getBonusRerolls();
    }

    private int getBonusRerolls(){
	    int total = inventory.getResourceAmount(Eff.EffectType.Morale)/3;
	    for(Buff b : buffs){
	        if(b.type == Buff.BuffType.Rerolls){
	            total += b.value;
            }
        }
        return total;
    }

    public int getNumBuildings(){
        return buildings.size();
    }

    private void addTurnEff(Eff eff){
        GameScreen.get().tsp.addTurnEffects(eff);
    }

    private void addToUpkeepp(Eff eff){
        upkeep.addEffect(eff);
    }

    public ObjectivePanel getObjectivePanel() {
        return GameScreen.get().objectivePanel;
    }

    public void objectiveProgress(Objective.ObjectiveEffect obj, int value) {
        getObjectivePanel().objectiveProgress(obj, value);
    }

    public void addJoel(float joel){
        this.joel+=joel;
        getJoelDebugPanel().setJoel(this.joel);
    }

    public float getJoel(){
        return joel;
    }

    private JoelDebugPanel jdp;
    public JoelDebugPanel getJoelDebugPanel() {
        if(jdp == null){
            jdp = new JoelDebugPanel();
            jdp.setJoel(0);
        }
        return jdp;
    }
}
