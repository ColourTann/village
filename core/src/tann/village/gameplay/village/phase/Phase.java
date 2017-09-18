package tann.village.gameplay.village.phase;

import tann.village.gameplay.village.villager.Villager;

public abstract class Phase {
    public abstract void activate();
    public abstract void deactivate();

    public boolean allowDieClicking() {return false;}

    public boolean allowBuying() {return false;}

    public boolean canContinue() {return true;}

    public void selectVillager(Villager v){};
}
