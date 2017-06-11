package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import tann.village.util.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import tann.village.gameplay.effect.Effect;

import java.util.ArrayList;
import java.util.List;

public class EachTurnPanel extends Group{
    public EachTurnPanel() {

    }

    List<EffectPanel> panels = new ArrayList<>();
    public void addEffect(Effect e){
        for(EffectPanel ep:panels){
            if(ep.effect.type==e.type){
                ep.value+=e.value;
                return;
            }
        }
        EffectPanel ep = new EffectPanel(e);
        panels.add(ep);
        layout();
        if(panels.size()==1){
            addAction(Actions.moveTo(175,0, .5f, Interpolation.pow2Out));
        }
    }

    private void layout(){
        clearChildren();
        int gap = 10;
        TextBox tb = new TextBox("Each turn:", Fonts.fontSmall, 999, Align.center);
        setSize(gap+(EffectPanel.WIDTH+gap)*panels.size(), tb.getHeight() + EffectPanel.HEIGHT + gap*3);
        Layoo l = new Layoo(this);
        l.row(1);
        l.actor(tb);
        l.row(1);
        l.gap(1);
        for(EffectPanel ep:panels){
            l.actor(ep);
            l.gap(1);
        }
        l.row(1);
        l.layoo();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.brown_dark);
        Draw.fillRectangle(batch, 0, getY(), getX()+getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
