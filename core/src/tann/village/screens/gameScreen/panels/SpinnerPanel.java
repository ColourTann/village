package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import tann.village.bullet.BulletStuff;
import tann.village.gameplay.village.villager.die.Die;
import tann.village.util.Draw;

public class SpinnerPanel extends Actor {
    Die d;
    public SpinnerPanel(Die d, float size) {
        this.d=d;
        setSize(size,size);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        Vector2 result = localToStageCoordinates(new Vector2());
        BulletStuff.drawSpinnyDie3(d, result.x+getWidth()/2, result.y+getHeight()/2, getWidth()*1.2f);
        batch.begin();
        super.draw(batch, parentAlpha);
    }
}
