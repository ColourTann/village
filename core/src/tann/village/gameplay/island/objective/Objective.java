package tann.village.gameplay.island.objective;

import tann.village.screens.gameScreen.panels.ObjectivePanel;

public abstract class Objective {




    public enum ObjectiveEffect{Building};
    int current, required;

    private ObjectivePanel panel;

    public ObjectivePanel getPanel(){
        if(panel==null){
            panel = new ObjectivePanel(this);
        }
        return panel;
    }

    protected String getDefaultProgressString() {
        return current+"/"+required;
    }

    public abstract void init();
    public abstract void objectiveProgress(ObjectiveEffect type, int amount);
    public abstract String getTitleString();
    public abstract String getProgressString();
}
