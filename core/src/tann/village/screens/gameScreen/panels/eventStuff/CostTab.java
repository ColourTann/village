package tann.village.screens.gameScreen.panels.eventStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.utils.Align;
import tann.village.Main;
import tann.village.gameplay.effect.Cost;
import tann.village.util.*;

public class CostTab extends Lay{
    Cost cost;
    public CostTab(Cost cost) {
        this.cost=cost;
        layout();
    }

    NinePatch np;
    @Override
    public void layout() {
        np = new NinePatch(Main.atlas.findRegion("patchTest"),30,30,27,1);
        float baseWidth = Main.h(11);
        for(int i=0;i<cost.effects.size;i++){
            baseWidth += Main.h(4);
        }
        setSize(baseWidth, Main.h(4.5f));
        Layoo l = new Layoo(this);
        TextWriter tw = new TextWriter(cost.toWriterString(), Fonts.fontSmallish);
        addActor(tw);
        tw.setPosition(getWidth()/2, getHeight()/2, Align.center);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.brown_dark);
        np.draw(batch, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
