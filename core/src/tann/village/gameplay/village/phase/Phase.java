package tann.village.gameplay.village.phase;

public abstract class Phase {
    public abstract void activate();
    public abstract void deactivate();

    public boolean allowDieClicking() {
        return false;
    }

    public boolean allowBuying() {
        return false;
    }
}
