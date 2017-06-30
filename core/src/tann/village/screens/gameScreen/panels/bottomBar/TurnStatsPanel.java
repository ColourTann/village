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

    private Array<Eff> turnEffects = new Array<>();
    public void addTurnEffects(Eff te){
        turnEffects.add(te);
        layout();
        somethingAdded();
        System.out.println("added");
    }

    @Override
    public void layout() {
        setSize(BottomBar.width(), BottomBar.height());
        Layoo l = new Layoo(this);
        l.gap(1);
        for(Eff te:turnEffects){
            l.actor(new TurnEffectPanel(te));
            l.gap(1);
        }
        l.layoo();
        System.out.println(getChildren().size);
    }

    @Override
    public String getName() {
        return "stats";
    }
}
