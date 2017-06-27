package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.panels.eventStuff.OutcomePanel;

public class Outcome {
    public Array<Effect> effects = new Array<>();
    public String description;

    public Outcome(String description, Array<Effect> events) {
        this.effects = events;
        this.description = description;
    }

    public void activate(){
        for(Effect e: effects){
            e.activate(false);
        }
    }

    public OutcomePanel getPanel(){
        return new OutcomePanel(this);
    }

    public boolean isValid() {
        for(Effect e:effects){
            if(!Village.getInventory().isEffectValidAllowOvershoot(e)){
                return false;
            }
        }
        return true;
    }
}
