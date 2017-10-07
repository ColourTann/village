package tann.village.gameplay.village.phase;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.review.StarvationPanel;

public class StarvationPhase extends Phase {
    @Override
    public void activate() {
        Village.get().starvation++;
        int food = Village.getInventory().getResourceAmount(Eff.EffectType.Food);
        int wood = Village.getInventory().getResourceAmount(Eff.EffectType.Wood);
        int foodMissing = Math.max(0, -food);
        int woodMissing = Math.max(0, -wood);
        StarvationPanel panel = new StarvationPanel(foodMissing, woodMissing);
        GameScreen.get().addWithProceedButton(panel, true);
        Village.getInventory().imposeFoodAndWoodMinimum();
    }

    @Override
    public void deactivate() {
        if(Village.get().starvation>=Village.MAX_STARVATION){
            Village.get().pushPhase(new LossPhase(null));
        }
    }
}
