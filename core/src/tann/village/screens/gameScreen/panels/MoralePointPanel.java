package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import tann.village.Main;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.inventory.MoralePoint;
import tann.village.screens.gameScreen.panels.eventStuff.EffectPanel;
import tann.village.util.*;

/**
 * Created by Oliver.Garland on 02/10/2017.
 */
public class MoralePointPanel extends Lay {

    MoralePoint point;
    public MoralePointPanel(MoralePoint point) {
        this.point = point;
        layout();
    }

    @Override
    public void layout() {
        setSize(Main.w(30), Main.w(20));
        Layoo l = new Layoo(this);
        TextWriter tw = new TextWriter("Morale event", Fonts.font);
        l.row(1);
        l.actor(tw);
        l.row(1);
        EffectPanel ep = new EffectPanel(point.eff, true);
        l.actor(ep);
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
