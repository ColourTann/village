package tann.village.gameplay.village.phase;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.villagerStuff.LevelupPanel;
import tann.village.screens.gameScreen.panels.villagerStuff.VillagerBarPanel;
import tann.village.util.Sounds;

public class LevelupPhase extends Phase {

    Villager v;
    public LevelupPhase(Villager v) {
        this.v=v;
    }

    @Override
    public void activate() {
        Array<Villager.VillagerType> choices = Island.get().getRandomVillagerTypes(v.type, 3);
        LevelupPanel lup = new LevelupPanel(v, choices);
        GameScreen.get().addWithProceedButton(lup, false);
        Sounds.playSound(Sounds.marimba_happy, 1, 1);
    }

    @Override
    public void deactivate() {
        GameScreen.get().vbp.layout();
    }
}
