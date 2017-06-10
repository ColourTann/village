package tann.village.screens.gameScreen.panels.review;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Align;

import tann.village.Main;
import tann.village.gameplay.island.islands.Island;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.mapScreen.MapScreen;
import tann.village.util.*;

public class LossPanel extends InfoPanel{
	private static float WIDTH=500, HEIGHT=300;
	public enum LossReason{Morale, Other}
	public LossPanel(LossReason reason, int turnsSurvived) {
		setSize(WIDTH, HEIGHT);
		String lossText="";
		switch(reason){
		case Morale:
			lossText="The village has lost hope and must return to the mainland";
			break;
		default:
			break;
		}
		TextBox loss = new TextBox(lossText, Fonts.font, WIDTH-30, Align.center);
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(loss);
		l.row(1);
        TextButton quit = new TextButton(110, 40, "Quit");
        TextButton restart = new TextButton(110, 40, "Restart");
        l.add(1, quit, 1, restart, 1);
		l.row(1);
		l.layoo();
        quit.setRunnable(new Runnable() {
            @Override
            public void run() {
                Sounds.stopMusic();
                GameScreen.get().pop();
                Main.self.setScreen(MapScreen.get(), Main.TransitionType.LEFT, Interpolation.bounce.pow2Out , .5f);
            }
        });
        restart.setRunnable(new Runnable() {
            @Override
            public void run() {
                GameScreen.get().pop();
                Island island = GameScreen.get().island;
                GameScreen.nullScreen();
                Main.self.travelTo(island);
            }
        });
	}
}
