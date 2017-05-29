package tann.village.gameplay.village;

import tann.village.gameplay.effect.Effect;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.UpkeepPanel;

import java.util.ArrayList;
import java.util.List;

public class Upkeep {

    UpkeepPanel panel;
    public UpkeepPanel getPanel(){
        if(panel==null) panel = new UpkeepPanel();
        return panel;
    }

    List<Effect> effects = new ArrayList<>();

    public void addEffect(Effect effect){
        boolean added = false;
        for(Effect existing:effects){
            if(existing.type == effect.type){
                existing.value+=effect.value;
                added=true;
                break;
            }
        }
        if(!added) effects.add(effect);
        updatePanel();
        Village.getInventory().getGroup().layout();
    }

    public void reset(){
        effects.clear();
        updatePanel();
    }

    private void updatePanel() {
        getPanel().build(effects);
    }

    public void activate() {
        for(Effect e:effects){
            GameScreen.get().addEffect(e);
        }
    }

}