package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import tann.village.Main;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Colours;
import tann.village.util.Lay;

public class BottomBar extends Lay{

    public BottomBar() {
        layout();
        setY(-getHeight()*1.3f);
    }

    public static float height(){
        return Main.h(14);
    }

    public static float gap(){
        return Main.w(2);
    }


    public static float width(){
        return Main.width- GameScreen.getConstructionCircleSize()*2-gap()*2;
    }

    Array<BottomBarPanel> panels = new Array<>();
    public void addPanel(final BottomBarPanel panel) {
        addActor(panel);
        panels.add(panel);
        final BottomTab tab = panel.getTab();
        addActor(tab);
        tab.setPosition(getWidth()/3*panels.size-tab.getWidth()/2, getHeight());
        tab.setRunnable(new Runnable() {
            @Override
            public void run() {
                BottomBar.this.toFront();
                panel.toFront();
                tab.toFront();
            }
        });
        if(panels.size==1){
            addAction(Actions.moveTo(getX(), 0, .3f, Interpolation.pow2Out));
        }
    }



    @Override
    public void layout() {
        setSize(width(), height());
        setPosition(GameScreen.getConstructionCircleSize()+gap(), getY());
        for(BottomBarPanel bbp:panels){
            bbp.refresh();
            BottomTab bt = bbp.getTab();
            bt.setPosition(getWidth()/3*(panels.indexOf(bbp, true)+1)-bt.getWidth()/2, getHeight());
        }
    }

    public static float border(){
        return Main.h(.5f);
    }

}
