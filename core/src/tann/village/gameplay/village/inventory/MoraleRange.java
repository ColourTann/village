package tann.village.gameplay.village.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;

/**
 * Created by Oliver.Garland on 02/10/2017.
 */
public class MoraleRange {
    public int min, max;
    public Color col;
    public Array<Eff> effs;
    public MoraleRange(int min, int max, Color col, Eff eff) {
        this.min = min;
        this.max = max;
        this.col = col;
        effs = new Array<>();
        effs.add(eff);
    }

    public boolean isActive(int value) {
        return(value>=min && value<=max);
    }
}
