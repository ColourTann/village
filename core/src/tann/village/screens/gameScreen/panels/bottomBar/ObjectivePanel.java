package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectIntMap;
import tann.village.Images;
import tann.village.Main;
import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.island.objective.Objective;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.village.Inventory;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.*;

public class ObjectivePanel extends Lay{
	


	Array<Objective> objectives = new Array<>();
	public ObjectivePanel() {
		layout();
	}

    @Override
    public void layout() {
        setSize(BottomTextBar.width(), BottomTextBar.height());
        refresh();
    }

    public void addObject(Objective obj){
	    objectives.add(obj);
	    refresh();
    }

	public void refresh(){
	    clearChildren();
	    Layoo parentLay = new Layoo(this);
	    parentLay.gap(1);
	    for(int i=0;i<objectives.size;i++){
            Objective o = objectives.get(i);
            BasicLay bl = new BasicLay(){
                @Override
                public void draw(Batch batch, float parentAlpha) {
                    Draw.fillActor(batch, this, Colours.dark, Colours.brown_light, Main.h(.6f));
                    super.draw(batch, parentAlpha);
                }
            };
            bl.setSize(BottomTextBar.width()/3, BottomTextBar.height());
            Layoo l = new Layoo(bl);
            TextBox objText = new TextBox(o.getTitleString(), Fonts.fontSmall, getWidth(), Align.center);
            TextBox progress = new TextBox(o.getProgressString(), Fonts.fontSmall, getWidth(), Align.center);
            l.row(1);
            l.actor(objText);
            l.row(1);
            l.actor(progress);
            l.row(1);
            l.layoo();
            parentLay.actor(bl);
            parentLay.gap(1);

        }
		parentLay.layoo();
	}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.brown_dark);
        Draw.fillActor(batch, this);
        super.draw(batch, parentAlpha);
    }

}
