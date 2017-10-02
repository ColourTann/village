package tann.village.gameplay.village.inventory;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.phase.MoralePointPhase;

/**
 * Created by Oliver.Garland on 02/10/2017.
 */
public class MoralePoint {
    public int morale;
    public Eff eff;
    public MoralePoint(int morale, Eff eff) {
        this.morale = morale;
        this.eff = eff;
    }

    public void trigger() {
        Village.get().pushPhase(new MoralePointPhase(this));
    }
}
