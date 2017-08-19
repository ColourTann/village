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

    private float getGap(){
        return Main.h(.7f);
    }

    @Override
    public void layout() {
        setSize(30,30);
        TextWriter tw = new TextWriter(eff.toWriterString(), Fonts.fontSmallish);
        addActor(tw);
        tw.setPosition(getGap(), getGap());
        setSize(tw.getWidth()+getGap()*2, tw.getHeight()+getGap()*2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Draw.fillActor(batch, this, Colours.dark, Colours.brown_light, Main.h(.5f));
        super.draw(batch, parentAlpha);
    }
}
