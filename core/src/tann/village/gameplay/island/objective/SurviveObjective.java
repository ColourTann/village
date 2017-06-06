package tann.village.gameplay.island.objective;


import tann.village.gameplay.village.Village;

public class SurviveObjective extends Objective {
    public SurviveObjective(int numTurns) {
        this.required=numTurns;
    }

    @Override
    public boolean isDeath() {
        return false;
    }

    @Override
    public void init() {
        this.current = Village.get().getDayNum();
    }

    @Override
    public void internalObjectiveProgress(ObjectiveEffect type, int amount) {
        if(type == ObjectiveEffect.Turn){
            this.current += amount;
        }
    }

    @Override
    public String getTitleString() {
        return "Survive "+required+" turns";
    }

    @Override
    public String getProgressString() {
        return getDefaultProgressString();
    }
}
