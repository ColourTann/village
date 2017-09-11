package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.graphics.g2d.Batch;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Lay;

public abstract class BottomBarPanel extends Lay {
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Draw.fillActor(batch, this, getColor(), getColor(), BottomBar.border());
        super.draw(batch, parentAlpha);
    }

    BottomTab tab;
    public BottomTab getTab(){
        if(tab==null) tab = new BottomTab(getName(), getColor());
        return tab;
    }

    public abstract String getName();

    boolean added;
    protected void somethingAdded() {
        if(!added){
            added=true;
            GameScreen.get().btb.addPanel(this);
        }
    }
    public abstract void refresh();

}
