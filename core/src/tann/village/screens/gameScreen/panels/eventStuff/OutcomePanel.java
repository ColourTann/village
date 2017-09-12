package tann.village.screens.gameScreen.panels.eventStuff;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import tann.village.Images;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.event.Outcome;
import tann.village.util.*;

public class OutcomePanel extends Group {
    public static int WIDTHSMALL = 220, WIDTHBIG=260, HEIGHTBASE = 40, HEIGHT = 115;
    Color border = Colours.dark;
    Outcome o;
    boolean locked;
    public boolean chosen;
    public OutcomePanel(final Outcome o, boolean triple) {
        this.o=o;

        setSize(WIDTHBIG, HEIGHT);
        Fonts.fontSmall.setColor(Colours.light);
        TextBox tb = new TextBox(o.description, Fonts.fontSmall, WIDTHBIG-10, Align.center);
        float textPosition = .73f;
        tb.setPosition(getWidth()/2-tb.getWidth()/2, getHeight()*textPosition-tb.getHeight()/2);
        addActor(tb);

        if(o.effects.size>0) {
            Eff e = o.effects.get(0);
            EffectPanel ep = new EffectPanel(e, true);
            float effPanelPosition = .28f;
            ep.setPosition(getWidth() / 2 - ep.getWidth() / 2, getHeight() * effPanelPosition - ep.getHeight() / 2);
            addActor(ep);
        }


        if(o.cost!=null){
            CostTab ct = new CostTab(o.cost);
            addActor(ct);
            ct.setPosition(getWidth()/2-ct.getWidth()/2, getHeight());
        }
        setColor(0,0,0,0);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int gap = 2;
        batch.setColor(chosen?Colours.light:Colours.grey);
        Draw.fillActor(batch,this);
        batch.setColor(border);
        Draw.fillRectangle(batch, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
        super.draw(batch, parentAlpha);
        if(!o.pickedBeforeEver && o.fateful){
            batch.setColor(border);
            Draw.fillRectangle(batch, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
            batch.setColor(Colours.light);
            float scale = Math.min(getWidth()/Images.eagle.getRegionWidth(), getHeight()/Images.eagle.getRegionHeight())*.7f;
            Draw.drawCenteredScaled(batch, Images.eagle, getX()+getWidth()/2, getY()+getHeight()/2, scale, scale);
        }
        batch.setColor(getColor());
        Draw.fillActor(batch,this);
    }

    private void lock(){
        locked=true;
        o.pick();
        setColor(Colours.light);
        addAction(Actions.fadeOut(.5f, Interpolation.pow2In));
    }

    public void deselect() {
        chosen=false;
        border = Colours.dark;
    }

    public void select() {
        chosen=true;
        if(o.fateful && !o.pickedBeforeEver){
            lock();
        }
        border = Colours.fate_darkest;
    }
}
