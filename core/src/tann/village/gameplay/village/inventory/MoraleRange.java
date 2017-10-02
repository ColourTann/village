package tann.village.gameplay.village.inventory;

import com.badlogic.gdx.graphics.Color;
import tann.village.gameplay.effect.Eff;

/**
 * Created by Oliver.Garland on 02/10/2017.
 */
public class MoraleRange {
    public int min, max;
    public Color col;
    public Eff eff;
    public MoraleRange(int min, int max, Color col, Eff eff) {
        this.min = min;
        this.max = max;
        this.col = col;
        this.eff = eff;
    }
}
