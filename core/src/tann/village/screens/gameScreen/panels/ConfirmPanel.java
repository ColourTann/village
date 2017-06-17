package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import tann.village.Images;
import tann.village.Main;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Lay;

public class ConfirmPanel extends Lay{
    public ConfirmPanel() {
        layout();
    }

    @Override
    public void layout() {
        setSize(Main.h(16), Main.h(16));
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
