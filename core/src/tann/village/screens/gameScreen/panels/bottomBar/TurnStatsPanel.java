package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.village.TurnEffect;
import tann.village.util.Colours;
import tann.village.util.Layoo;

public class TurnStatsPanel extends BottomBarPanel{

    public TurnStatsPanel() {
        setColor(Colours.blue_dark);
        layout();
    }

    Array<TurnEffect> turnEffects = new Array<>();
    public void addTurnEffects(TurnEffect te){
        turnEffects.add(te);
        layout();
        somethingAdded();
    }

    @Override
    public void layout() {
        setSize(BottomBar.width(), BottomBar.height());
        Layoo l = new Layoo(this);
        for(TurnEffect te:turnEffects){

        }
        l.layoo();
    }

    @Override
    public String getName() {
        return "stats";
    }
}
