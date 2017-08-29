package tann.village.gameplay.village;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.village.building.Building;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.eventStuff.JoelDebugPanel;
import tann.village.screens.gameScreen.panels.bottomBar.ObjectivePanel;
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
    private float joel;
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
	    getObjectivePanel().objectiveProgress(Objective.ObjectiveEffect.Turn, 1);
    }

	public static Inventory getInventory(){
	    return get().inventory;
    }

    public void activate(Eff[] effs, boolean activateNow, boolean invert){for(Eff e:effs){activate(e, activateNow, invert);}}
    public void activate(Array<Eff> effs, boolean activateNow, boolean invert){for(Eff e:effs){activate(e, activateNow, invert);}}
    public void activate(Array<Eff> effs, boolean activateNow){for(Eff e:effs){activate(e, activateNow, false);}}
    public void activate(Eff e, boolean activateNow){
        activate(e, activateNow, false);
    }
    public void activate(Eff e, boolean activateNow, boolean invert){
        if(e.effAct!=null) {
            switch (e.effAct.type) {
                case FOR_TURNS:
                    activate(e.copy().now(), false);
                case IN_TURNS: // fallthrough
                    addTurnEff(e.copy());
                    return;
            }
        }
        getInventory().activate(e.copy(), activateNow, invert);
    }

	public void setup(){
	    dayNum=0;
        buildings.clear();
        inventory = new Inventory();
        upkeep= new Upkeep();
    }

    public void startOfRoll(){
	    GameScreen.get().tsp.startOfRolling();
    }

	public void addBuilding(Building b) {
        Sounds.playSound(Sounds.build,1,1);
        buildings.add(b);
        getObjectivePanel().objectiveProgress(Objective.ObjectiveEffect.Building, 1);
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
