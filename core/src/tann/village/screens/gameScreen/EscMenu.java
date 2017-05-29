package tann.village.screens.gameScreen;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import tann.village.Main;
import tann.village.gameplay.island.islands.Island;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.screens.mapScreen.MapScreen;
import tann.village.util.*;

public class EscMenu extends InfoPanel {
    private static int WIDTH = 300, HEIGHT = 200;

    private static EscMenu self;
    public static EscMenu get(){
        if(self==null) self = new EscMenu();
        return self;
    }

    private EscMenu() {
        setSize(WIDTH,HEIGHT);
        Slider.music.setSize(getWidth()*.8f, 30);
        Slider.SFX.setSize(getWidth()*.8f, 30);
        setPosition(Main.width/2-getWidth()/2, Main.height/2-getHeight()/2);
        final Layoo l = new Layoo(this);
        l.row(1);
        l.actor(Slider.music);
        l.row(1);
        l.actor(Slider.SFX);
        l.row(1);
        TextButton quit = new TextButton(110, 40, "Quit");
        TextButton restart = new TextButton(110, 40, "Restart");
        l.add(1, quit, 1, restart, 1);
        l.row(1);
        l.layoo();
        quit.setRunnable(new Runnable() {
            @Override
            public void run() {
                Main.self.setScreen(MapScreen.get(), Main.TransitionType.LEFT, Interpolation.bounce.pow2Out , .5f);
            }
        });
        restart.setRunnable(new Runnable() {
            @Override
            public void run() {
                Island island = GameScreen.get().island;
                GameScreen.nullScreen();
                Main.self.travelTo(island);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Fonts.draw(batch, "Version "+Main.version, Fonts.fontSmall, Colours.brown_light, getX(), getY(), 150, 20, Align.left);
    }
}