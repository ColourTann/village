package tann.village.gameplay.island.objective;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.village.Village;

public class GemsObjective extends  Objective{

    public GemsObjective(int numGems) {
        this.required=numGems;
    }

    @Override
    public boolean isDeath() {
        return false;
    }

    @Override
    public void init() {
        current = Village.get().getInventory().getResourceAmount(Effect.EffectType.Gem);
    }

    @Override
    protected void internalObjectiveProgress(ObjectiveEffect type, int amount) {
        if(type==ObjectiveEffect.Gem){
            current+=amount;
        }
    }

    @Override
    public String getTitleString() {
        return "Collect "+required+" gems.";
    }

    @Override
    public String getProgressString() {
        return getDefaultProgressString();
    }
}
