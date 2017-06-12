package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import tann.village.Main;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.screens.mapScreen.MapScreen;
import tann.village.util.*;

public class VictoryPanel extends InfoPanel{

    public VictoryPanel(String text) {
        setSize(250,150);
        TextBox tb = new TextBox(text, Fonts.font, getWidth()-20, Align.center);
        TextButton ok = new TextButton(70, 30, "Noice!");
        Layoo l = new Layoo(this);
        l.row(1);
        l.actor(tb);
        l.row(1);
        l.actor(ok);
        l.row(1);
        l.layoo();
        ok.setRunnable(new Runnable() {
            @Override
            public void run() {
                Main.self.setScreen(MapScreen.get(), Main.TransitionType.LEFT, Interpolation.pow2Out, 1); Sounds.stopMusic();
            }
        }
        );
    }
}
