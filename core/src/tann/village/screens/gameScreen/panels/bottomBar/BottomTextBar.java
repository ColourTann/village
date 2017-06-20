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

    @Override
    public void layout() {
        setSize(width(), height());
        setPosition(GameScreen.getConstructionCircleSize(), 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.dark);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }
}
