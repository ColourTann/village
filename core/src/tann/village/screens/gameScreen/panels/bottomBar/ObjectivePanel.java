package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.Array;
import tann.village.Main;
import tann.village.gameplay.island.objective.Objective;
import tann.village.util.*;

public class ObjectivePanel extends BottomBarPanel{

	Array<Objective> objectives = new Array<>();
	public ObjectivePanel() {
        setColor(Colours.brown_dark);
        layout();
	}

    @Override
    public void layout() {
        setSize(BottomBar.width(), BottomBar.height());
        refresh();
    }


    public void addObject(Objective obj){
	    objectives.add(obj);
	    refresh();
	    somethingAdded();
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
            bl.setSize(BottomBar.width()/3, BottomBar.height()*.8f);
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
        toFront();
	}

    @Override
    public String getName() {
        return "objective";
    }
}
