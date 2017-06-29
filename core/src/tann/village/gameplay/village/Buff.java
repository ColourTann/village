package tann.village.gameplay.village;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;

public class Buff {

    EffectType buffType;
    int value;
    int duration;
    private int turnsLeft;

    public Buff(Eff effect){
        this(effect.type, effect.value, 1); //todo duration
    }


    public Buff(EffectType buffType, int value, int duration) {
        this.buffType = buffType;
        this.value = value;
        this.duration = duration;
        this.turnsLeft = duration;
    }

    public boolean expired() {
        return turnsLeft ==0;
    }

    public void upkeep() {
        if (turnsLeft > 0) turnsLeft--;
    }

    public void process(Eff effect) {
//        switch(buffType){
//            case FoodBonus:
//                if(effect.source == Eff.EffectSource.Dice){
//                    effect.value = Math.max(0, effect.value + value);
//                }
//                break;
//        }
        //todo what's this
    }
}
