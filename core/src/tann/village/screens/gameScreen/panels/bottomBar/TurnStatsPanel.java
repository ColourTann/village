package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.util.Colours;
import tann.village.util.Layoo;

public class TurnStatsPanel extends BottomBarPanel{

    public TurnStatsPanel() {
        setColor(Colours.blue_dark);
        layout();
    }

    Array<Eff> turnEffects = new Array<>();
    public void addTurnEffects(Eff te){
        turnEffects.add(te);

    }

    @Override
    public void layout() {
        setSize(BottomBar.width(), BottomBar.height());
        Layoo l = new Layoo(this);
        for(Eff te:turnEffects){

        }
        l.layoo(true);
    }

    @Override
    public String getName() {
        return "stats";
    }
}
