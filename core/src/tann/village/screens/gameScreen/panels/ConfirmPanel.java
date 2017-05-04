package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import tann.village.Images;
import tann.village.util.Colours;
import tann.village.util.Draw;

public class ConfirmPanel extends Actor{
    static final int WIDTH = 100, HEIGHT = 100;
    public ConfirmPanel() {
        setSize(WIDTH, HEIGHT);

    }

    boolean clickable;
    public void setClickable(boolean clickable){
        this.clickable=clickable;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(clickable?Colours.green_light:Colours.grey);
        Draw.drawSize(batch, Images.tick, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
