package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Inventory;
import tann.village.gameplay.village.Village;
import tann.village.util.Colours;
import tann.village.util.Layoo;

public class TurnStatsPanel extends BottomBarPanel{

    public TurnStatsPanel() {
        setColor(Colours.dark);
        layout();
    }

    public void startOfRolling(){
        for(int i=turnEffects.size-1;i>=0;i--) {
            Eff e = turnEffects.get(i);
            switch (e.effAct.type) {
                case FOR_TURNS:
                case IN_TURNS:
                    e.turn();
                    break;
                case UPKEEP:
                    break;
                case PASSIVE:
                    break;
            }
            if(e.dead){
                turnEffects.removeValue(e, true);
            }
        }
        layout();
    }

    private Array<Eff> turnEffects = new Array<>();
    public void addTurnEffects(Eff te){
        turnEffects.add(te);
        layout();
        somethingAdded();
    }

    @Override
    public void layout() {
        clearChildren();
        setSize(BottomBar.width(), BottomBar.height());
        Layoo l = new Layoo(this);
        l.row(2);
        l.gap(1);
        int x = 0;
        for(Eff te:turnEffects){
            TurnEffectPanel tep = new TurnEffectPanel(te);
            x+= tep.getWidth();
            if(x>getWidth()){
                x=0;
                l.row(1.5f);
                l.gap(1);
            }
            l.actor(tep);
            l.gap(1);
        }
        l.row(2);
        l.layoo();
    }

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public void refresh() {
        layout();
    }

    @Override
    public void reset() {
        turnEffects.clear();
    }
}
