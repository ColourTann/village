package tann.village.screens.gameScreen.panels.eventStuff;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import tann.village.Images;
import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.event.Outcome;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.buildingStuff.CostPanel;
import tann.village.util.*;

public class OutcomePanel extends Group {
    public static int WIDTH = 270, HEIGHTBASE = 40;
    Color border = Colours.dark;
    Outcome o;
    boolean locked;
    public OutcomePanel(final Outcome o) {
        this.o=o;
        float height = HEIGHTBASE;
        Layoo l = new Layoo(this);
        l.row(1);
        Fonts.fontSmall.setColor(Colours.light);
        TextBox tb = new TextBox(o.description, Fonts.fontSmall, WIDTH, Align.center);
        height += tb.getHeight();
        l.actor(tb);
        l.row(2);
        for(int i=0;i<o.effects.size;i++){
            Eff e=o.effects.get(i);
            EffectPanel ep =new EffectPanel(e, true);
            l.actor(ep);
            height += ep.getHeight()+5;
            l.row(1);
        }
        l.row(1);
        setSize(WIDTH, height);
        l.layoo();
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
        batch.setColor(o.chosen?Colours.light:Colours.grey);
        Draw.fillActor(batch,this);
        batch.setColor(border);
        Draw.fillRectangle(batch, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
        super.draw(batch, parentAlpha);
        if(!o.pickedBeforeEver && o.cost!=null){
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
        o.pickedBeforeEver=true;
        setColor(Colours.light);
        addAction(Actions.fadeOut(.5f, Interpolation.pow2In));
    }

    public void deselect() {
        o.chosen=false;
        border = Colours.dark;
    }

    public void select() {
        o.chosen=true;
        if(o.fateful && !o.pickedBeforeEver){
            lock();
        }
        border = Colours.fate_darkest;
    }
}
