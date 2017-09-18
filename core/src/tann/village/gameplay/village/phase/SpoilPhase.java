package tann.village.gameplay.village.phase;

import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.Inventory;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.review.SpoilPanel;

public class SpoilPhase extends Phase {
    int spoilt;
    public SpoilPhase(int spoilt) {
        this.spoilt=spoilt;
    }

    @Override
    public void activate() {
        Village.getInventory().imposeLimits();
        SpoilPanel panel = new SpoilPanel(spoilt);
        GameScreen.get().addWithProceedButton(panel, true);
    }

    @Override
    public void deactivate() {

    }
}
