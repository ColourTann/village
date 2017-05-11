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

    boolean clickable, rolling;
    public void setRolling(boolean rolling){
        this.rolling=rolling;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!rolling){
            batch.setColor(Colours.green_light);
            Draw.drawSize(batch, Images.tick, getX(), getY(), getWidth(), getHeight());
        }
        else{
            batch.setColor(Colours.grey);
            Draw.drawLoadingAnimation(batch, getX()+getWidth()/2, getY()+getHeight()/2, 30, 10, 2, 3);
        }
        super.draw(batch, parentAlpha);
    }
}
