package tann.village.screens.gameScreen.panels.villagerStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import tann.village.gameplay.effect.Eff;
import tann.village.screens.gameScreen.panels.eventStuff.EffectPanel;
import tann.village.util.*;

public class VillagerBuffPanel extends Lay {
    Eff e;
    public VillagerBuffPanel(Eff e) {
        this.e=e;
        layout();
    }

    @Override
    public void layout() {
        setSize(500,120);
        TextWriter tw = new TextWriter("Choose a villager to give", Fonts.font);
        EffectPanel ep = new EffectPanel(e, false);
        Layoo l = new Layoo(this);
        l.row(1);
        l.actor(tw);
        l.row(1);
        l.actor(ep);
        l.row(1);
        l.layoo();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Draw.fillActor(batch, this, Colours.brown_dark, Colours.dark, 2);
        super.draw(batch, parentAlpha);
    }
}
