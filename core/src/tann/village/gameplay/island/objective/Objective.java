package tann.village.gameplay.island.objective;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.ObjectivePanel;
import tann.village.util.Sounds;

public abstract class Objective {


    public String getVictoryText(){
        return "You win!";
    };

    public abstract boolean isDeath();

    public enum ObjectiveEffect{Building, Turn, Gem};
    int current, required;

    private ObjectivePanel panel;

    protected String getDefaultProgressString() {
        return current+"/"+required;
    }

    public abstract void init();
    public void objectiveProgress(ObjectiveEffect type, int amount){
        internalObjectiveProgress(type, amount);
        GameScreen.get().objectivePanel.refresh();
    }

    protected abstract void internalObjectiveProgress(ObjectiveEffect type, int amount);
    public abstract String getTitleString();
    public abstract String getProgressString();
    public boolean isComplete(){
        return current >= required;
    }
}
