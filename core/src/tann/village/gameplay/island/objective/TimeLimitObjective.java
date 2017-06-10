package tann.village.gameplay.island.objective;

import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Sounds;

public class TimeLimitObjective extends Objective {

    public TimeLimitObjective(int turns) {
            this.required=turns;
    }

    @Override
    public boolean isDeath() {
        return true;
    }

    @Override
    public void init() {
            this.current = Village.get().getDayNum();
    }

    @Override
    protected void internalObjectiveProgress(ObjectiveEffect type, int amount) {
        if(type==ObjectiveEffect.Turn){
            current+=amount;
        }
    }

    @Override
    public String getTitleString() {
        return "Complete all objectives in "+required+" turns.";
    }

    @Override
    public String getProgressString() {
        return getDefaultProgressString();
    }

    public void complete(){
        GameScreen.get().showLoss();
    }
}
