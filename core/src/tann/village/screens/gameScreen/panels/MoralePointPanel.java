package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import tann.village.Main;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.inventory.MoralePoint;
import tann.village.screens.gameScreen.panels.eventStuff.EffectPanel;
import tann.village.util.*;

public class MoralePointPanel extends Lay {

    MoralePoint point;
    public MoralePointPanel(MoralePoint point) {
        this.point = point;
        layout();
    }

    @Override
    public void layout() {
        setSize(Main.w(40), Main.w(20));
        Layoo l = new Layoo(this);
        TextWriter tw = new TextWriter("Morale event", Fonts.font);
        l.row(1);
        l.actor(tw);
        l.row(1);
        l.gap(1);
        for(Eff e:point.effs){
            EffectPanel ep = new EffectPanel(e, true);
            l.actor(ep);
            l.gap(1);
        }
        l.row(1);
        l.layoo();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.dark);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }
}
