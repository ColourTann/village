package tann.village.gameplay.village.inventory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.phase.MoralePointPhase;

/**
 * Created by Oliver.Garland on 02/10/2017.
 */
public class MoralePoint {
    public int morale;
    public Eff[]effs;
    public TextureRegion tr;
    public MoralePoint(int morale, TextureRegion tr, Eff[] effs) {
        this.morale = morale;
        this.effs = effs;
        this.tr=tr;
    }

    public void trigger() {
        if(effs != null) Village.get().pushPhase(new MoralePointPhase(this));
    }
}
