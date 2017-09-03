package tann.village.gameplay.village;

import tann.village.gameplay.effect.Eff;

public class Buff {
    public enum BuffType{
        BonusFoodFromDice,
        BonusWoodFromDice,
        Rerolls
    }
    public BuffType type;
    int value=0;
    int turns=1;

    public Buff(){}

    public Buff bonusFood(int amount){
        return type(BuffType.BonusFoodFromDice, amount);
    }

    public Buff bonusWood(int amount){
        return type(BuffType.BonusWoodFromDice, amount);
    }

    public Buff rerolls(int amount){
        return type(BuffType.Rerolls, amount);
    }

    public Buff forTurns(int amount){
        this.turns=amount;
        return this;
    }

    private Buff type(BuffType type, int value){
        this.type=type;
        this.value=value;
        return this;
    }

    public void process(Eff e){

    }

    public void onAdd(){}
    public void onRemove(){}

}
