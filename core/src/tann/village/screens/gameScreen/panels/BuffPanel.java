package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import tann.village.gameplay.village.Buff;
import tann.village.util.Colours;
import tann.village.util.Draw;

public class BuffPanel extends Group {
    private Buff buff;
    private static final int WIDTH = 40, HEIFHT=40;

    public BuffPanel(Buff buff) {
        this.buff = buff;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.blue_dark);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }
}
