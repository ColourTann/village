package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.panels.eventStuff.OutcomePanel;

public class Outcome {
    public Array<Eff> effects = new Array<>();
    public String description;
    public int fateAmount;

    public Outcome(String description, Array<Eff> events, int fateAmount) {
        this.effects = events;
        this.description = description;
        this.fateAmount=fateAmount;
    }

    public void activate(){
        Village.get().activate(effects, true);
    }

    public OutcomePanel getPanel(){
        return new OutcomePanel(this);
    }

    public boolean isValid() {
        for(Eff e:effects){
            if(!Village.getInventory().isEffectValidAllowOvershoot(e)){
                return false;
            }
        }
        return true;
    }
}
