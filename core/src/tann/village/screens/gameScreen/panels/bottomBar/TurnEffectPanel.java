package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import tann.village.Main;
import tann.village.gameplay.effect.Eff;
import tann.village.util.*;

public class TurnEffectPanel extends Lay {
    private Eff eff;
    public TurnEffectPanel(Eff eff) {
        this.eff =eff;
        layout();
    }

    @Override
    public void layout() {
        setSize(30,30);
        TextBox tb = new TextBox(eff.toString(), Fonts.fontSmall, -1, Align.center);
        addActor(tb);
        setSize(tb.getWidth(), tb.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Draw.fillActor(batch, this, Colours.dark, Colours.brown_light, Main.h(.3f));
        super.draw(batch, parentAlpha);
    }
}
