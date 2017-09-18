package tann.village.gameplay.village.phase;

import tann.village.Main;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.villagerStuff.VillagerBuffPanel;
import tann.village.screens.gameScreen.panels.villagerStuff.VillagerPanel;

public class BuffVillagerPhase extends Phase {

    Eff villagerBuff;
    VillagerBuffPanel vbp;
    public BuffVillagerPhase(Eff villagerBuff) {
        this.villagerBuff=villagerBuff;
    }

    @Override
    public void activate() {
        vbp = new VillagerBuffPanel(villagerBuff);
        vbp.setPosition(Main.width/2-vbp.getWidth()/2, Main.height/2-vbp.getHeight()/2);
        GameScreen.get().push(vbp);
        GameScreen.get().vbp.toFront();
    }

    @Override
    public void selectVillager(Villager v) {
        v.giveBuff(villagerBuff);
        GameScreen.get().pop();
        Village.get().popPhase();
    }

    @Override
    public void deactivate() {

    }
}
