package tann.village.gameplay.village.phase;

import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.inventory.MoralePoint;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.MoralePointPanel;
import tann.village.screens.gameScreen.panels.review.StarvationPanel;

/**
 * Created by Oliver.Garland on 02/10/2017.
 */
public class MoralePointPhase extends Phase {
    MoralePoint point;
    public MoralePointPhase(MoralePoint point) {
        this.point = point;
    }

    @Override
    public void activate() {
        Village.get().activate(point.eff, true);
        MoralePointPanel panel = new MoralePointPanel(point);
        GameScreen.get().addWithProceedButton(panel, true);
    }

    @Override
    public void deactivate() {

    }
}
