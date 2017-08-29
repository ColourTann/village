package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.panels.eventStuff.OutcomePanel;

public class Outcome {
    public Array<Eff> effects = new Array<>();
    public String description;
    public Cost cost;
    public boolean pickedBeforeEver;
    public boolean chosen;
    public boolean fateful;
    public Outcome(String description, Array<Eff> events, Cost cost) {
        this.cost=cost;
        this.effects = events;
        this.description = description;
        if(cost!=null && cost.has(Eff.EffectType.Fate)){
            fateful=true;
        }
    }

    public void activate(){
        if(cost!=null) Village.get().activate(cost.effects, true);
        Village.get().activate(effects, true);
    }

    OutcomePanel ocp;
    public OutcomePanel getPanel(){
        if(ocp==null)ocp= new OutcomePanel(this);
        return ocp;
    }

    public boolean isValid() {
        if(cost!=null && !Village.getInventory().checkCost(cost)) return false;
        for(Eff e:effects){
            if(!Village.getInventory().isEffectValidAllowOvershoot(e)){
                return false;
            }
        }
        return true;
    }
}
