package tann.village.gameplay.village;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.screens.gameScreen.panels.UpkeepPanel;

public class Upkeep {

    UpkeepPanel panel;
    public UpkeepPanel getPanel(){
        if(panel==null) panel = new UpkeepPanel();
        return panel;
    }

    Array<Eff> effects = new Array<>();

    public void addEffect(Eff effect){
        //todo fix upkeep
        effect.clearActivation();
        boolean added = false;
        for(Eff existing:effects){
            if(existing.type == effect.type){
                existing.value+=effect.value;
                added=true;
                break;
            }
        }
        if(!added) effects.add(effect);
        updatePanel();
        Village.getInventory().getGroup().layout(false);
    }

    public void reset(){
        effects.clear();
        updatePanel();
    }

    private void updatePanel() {
        getPanel().setEffects(effects);
        getPanel().layout();
    }

    public void activateDelta(){
        for(Eff e:effects){
            Village.getInventory().addDelta(e, false);
        }
    }

    public void activate() {
        for(Eff e:effects){
            Village.get().activateEffect(e);
        }
    }

}
