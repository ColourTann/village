package tann.village.gameplay.village.phase;

import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.villagerStuff.LevelupPanel;
import tann.village.util.Sounds;

public class LevelupPhase extends Phase {

    Villager v;
    public LevelupPhase(Villager v) {
        this.v=v;
    }

    @Override
    public void activate() {
        LevelupPanel lup = new LevelupPanel(v, Village.island.getRandomVillagerTypes(Math.min(Villager.MAX_LEVEL, v.type.level+1), 3));
        GameScreen.get().addWithProceedButton(lup, false);
        Sounds.playSound(Sounds.marimba_happy, 1, 1);
    }

    @Override
    public void deactivate() {

    }
}
