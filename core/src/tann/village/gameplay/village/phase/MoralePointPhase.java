package tann.village.gameplay.village.phase;

import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.inventory.MoralePoint;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.MoralePointPanel;
import tann.village.screens.gameScreen.panels.review.StarvationPanel;

public class MoralePointPhase extends Phase {
    MoralePoint point;
    public MoralePointPhase(MoralePoint point) {
        this.point = point;
    }

    @Override
    public void activate() {
        Village.get().activate(point.effs, true);
        MoralePointPanel panel = new MoralePointPanel(point);
        GameScreen.get().addWithProceedButton(panel, true);
        GameScreen.get().checkEnd();
    }

    @Override
    public void deactivate() {

    }
}
