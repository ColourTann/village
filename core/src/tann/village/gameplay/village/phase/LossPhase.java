package tann.village.gameplay.village.phase;

import tann.village.gameplay.effect.Eff;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.review.LossPanel;

public class LossPhase extends Phase{

    Eff lossCause;

    public LossPhase(Eff e) {
        lossCause = e;
    }

    @Override
    public void activate() {
        LossPanel lp = new LossPanel(LossPanel.LossReason.Morale, 2);
        GameScreen.get().addWithProceedButton(lp, false);
    }

    @Override
    public void deactivate() {

    }
}
