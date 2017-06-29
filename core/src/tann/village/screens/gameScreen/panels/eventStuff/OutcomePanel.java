package tann.village.screens.gameScreen.panels.eventStuff;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.event.Outcome;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.*;

public class OutcomePanel extends Group {
    public static int WIDTH = 270, HEIGHTBASE = 10, HEIGHTADD = 70;
    Color border = Colours.dark;
    public OutcomePanel(final Outcome o) {
        setSize(WIDTH, HEIGHTBASE+HEIGHTADD*((o.effects.size+1)/2));
        Layoo l = new Layoo(this);
        l.row(1);
        Fonts.fontSmall.setColor(Colours.light);
        TextBox tb = new TextBox(o.description, Fonts.fontSmall, WIDTH, Align.center);
        l.actor(tb);
        l.row(1);
        l.gap(1);
        for(int i=0;i<o.effects.size;i++){
            Eff e=o.effects.get(i);
            l.actor(new EffectPanel(e));
            l.gap(1);
            if(i%2 == 1 && i < o.effects.size-1) {
                l.row(1);
                l.gap(1);
            }
        }
        l.row(1);
        l.layoo();
        addListener(new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                border = Colours.fate_darkest;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                border = Colours.dark;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.get().chooseOutcome(o);
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int gap = 2;
        batch.setColor(Colours.light);
        Draw.fillActor(batch,this);
        batch.setColor(border);
        Draw.fillRectangle(batch, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
        super.draw(batch, parentAlpha);
    }
}
