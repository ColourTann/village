package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.village.TurnEffect;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Lay;
import tann.village.util.Layoo;

public class TurnStatsPanel extends Lay{

    public TurnStatsPanel() {
        layout();
    }

    Array<TurnEffect> turnEffects = new Array<>();

    public void setTurnEffects(Array<TurnEffect> turnEffects){
        this.turnEffects= turnEffects;
        layout();
    }

    @Override
    public void layout() {
        setSize(BottomTextBar.width(), BottomTextBar.height());
        Layoo l = new Layoo(this);
        for(TurnEffect te:turnEffects){

        }
        l.layoo();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.blue_dark);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }
}
