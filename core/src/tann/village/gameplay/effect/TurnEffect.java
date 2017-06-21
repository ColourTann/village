package tann.village.gameplay.effect;

import tann.village.gameplay.village.villager.die.Die;

public class TurnEffect extends Effect{
    public enum TurnEffectType{}
    public TurnEffect(TurnEffectType tet, EffectType type, int value) {
        super(type, value);
    }
}
