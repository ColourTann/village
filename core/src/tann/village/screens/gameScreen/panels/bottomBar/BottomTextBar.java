package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.graphics.g2d.Batch;
import tann.village.Main;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Lay;

public class BottomTextBar extends Lay{

    public BottomTextBar() {
        layout();
    }

    public static float height(){
        return Main.h(14);
    }

    public static float width(){
        return Main.width- GameScreen.getConstructionCircleSize()*2;
    }

    TurnStatsPanel tsp;
    public void setStatsPanel(TurnStatsPanel tsp){
        this.tsp=tsp;
        addPanel(tsp);
    }

    ObjectivePanel op;
    public void setObjectivePanel(ObjectivePanel op){
        this.op=op;
        addPanel(op);
    }

    public void addPanel(Lay panel) {
        addActor(panel);
    }

    @Override
    public void layout() {
        setSize(width(), height());
        setPosition(GameScreen.getConstructionCircleSize(), 0);
        final BottomTextBarTab objTab = new BottomTextBarTab("objectives", Colours.brown_dark, new Runnable() {
            @Override
            public void run() {
                if(op!=null){
                    op.toFront();
                }
            }
        }
        );

        BottomTextBarTab statsTab = new BottomTextBarTab("stats", Colours.blue_dark, new Runnable() {
            @Override
            public void run() {
                if(tsp!=null){
                    tsp.toFront();
                }
            }
        }
        );

        addActor(objTab);
        addActor(statsTab);
        objTab.setPosition(getWidth()/3-objTab.getWidth()/2, getHeight());
        statsTab.setPosition(getWidth()/3*2-statsTab.getWidth()/2, getHeight());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.dark);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }

}
