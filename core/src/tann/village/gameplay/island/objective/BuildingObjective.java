package tann.village.gameplay.island.objective;

import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;

public class BuildingObjective extends Objective{


    public BuildingObjective(int numBuildings) {
        this.required = numBuildings;
    }

    @Override
    public boolean isDeath() {
        return false;
    }

    @Override
    public void init() {
        this.current = Village.get().getNumBuildings();
    }

    @Override
    public boolean internalObjectiveProgress(ObjectiveEffect type, int amount) {
        if(type==ObjectiveEffect.Building){
            this.current += amount;
            return true;
        }
        return false;
    }

    @Override
    public String getTitleString() {
        return "Build "+required+" buildings";
    }

    @Override
    public String getProgressString() {
        return getDefaultProgressString();
    }

}
