package tann.village.gameplay.village;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.screens.gameScreen.panels.BuffPanel;

public class Buff {

    EffectType buffType;
    int value;
    int duration;
    private int turnsLeft;

    public Buff(Effect effect){
        this(effect.type, effect.value, effect.duration);
    }


    public Buff(EffectType buffType, int value, int duration) {
        this.buffType = buffType;
        this.value = value;
        this.duration = duration;
        this.turnsLeft = duration;
    }

    private BuffPanel panel;
    public BuffPanel getPanel(){
        if(panel==null) panel = new BuffPanel(this);
        return panel;
    }


    public boolean expired() {
        return turnsLeft <=0;
    }

    public void upkeep() {
        turnsLeft--;
    }
}
