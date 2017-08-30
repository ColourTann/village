package tann.village.screens.gameScreen.panels.buildingStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;

public class UnlockedByPanel extends Group {
    String text;
    public static float height(){
        return 14;
    }
    public UnlockedByPanel(String unlockedBy) {
        setSize(ProjectPanel.WIDTH, height());
        text= "Unlocked by "+unlockedBy;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.fate_darkest);
        Draw.fillActor(batch,this);
        Fonts.draw(batch, text, Fonts.fontTiny, Colours.light, getX(), getY(), getWidth(), getHeight(), Align.center);
        super.draw(batch, parentAlpha);
    }
}
