package tann.village.screens.gameScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import tann.village.Main;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.panels.VillagerIcon;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Lay;
import tann.village.util.Layoo;

public class VillagerBarPanel extends Lay{

    @Override
    public void layout() {
        setSize(Main.width-InventoryItemPanel.totalWidth(), VillagerIcon.height());
        clearChildren();
        Layoo l = new Layoo(this);
        l.gap(1);
        for(Villager v: GameScreen.get().villagers){
            l.actor(v.getIcon());
            l.gap(1);
        }
        l.layoo();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
