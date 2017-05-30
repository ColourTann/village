package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Effect;
import tann.village.screens.gameScreen.panels.OutcomePanel;

import java.util.ArrayList;
import java.util.List;

public class Outcome {
    public Array<Effect> effects = new Array<>();
    public String description;

    public Outcome(String description, Array<Effect> events) {
        this.effects = events;
        this.description = description;
    }

    public void activate(){
        for(Effect e: effects){
            e.activate();
        }
    }

    public OutcomePanel getPanel(){
        return new OutcomePanel(this);
    }
}
