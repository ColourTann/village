package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import tann.village.Main;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.GameScreen;
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
