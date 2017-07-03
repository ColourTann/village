package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Inventory;
import tann.village.gameplay.village.Village;
import tann.village.util.Colours;
import tann.village.util.Layoo;

public class TurnStatsPanel extends BottomBarPanel{

    public TurnStatsPanel() {
        setColor(Colours.blue_dark);
        layout();
    }

    public void startOfRolling(){
        for(Eff e: turnEffects)
            switch (e.effAct.type) {
                case NOW:
                    break;
                case IN_TURNS:
                    break;
                case FOR_TURNS:
                    Village.getInventory().addDelta(e, false);
                    break;
                case UPKEEP:
                    break;
                case PASSIVE:
                    break;
            }
    }

    public void endOfRolling(){
        for(Eff e: turnEffects)
            switch (e.effAct.type) {
                case NOW:
                    break;
                case IN_TURNS:
                    break;
                case FOR_TURNS:
                    Village.getInventory().activate(e);
                    break;
                case UPKEEP:
                    break;
                case PASSIVE:
                    break;
            }
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
