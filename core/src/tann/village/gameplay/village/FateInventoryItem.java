package tann.village.gameplay.village;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.gameplay.effect.Eff;
import tann.village.screens.gameScreen.panels.inventoryStuff.InventoryItemPanel;

public class FateInventoryItem extends InventoryItem {
    public FateInventoryItem() {
        super(Eff.EffectType.Fate);
    }

    @Override
    public InventoryItemPanel getPanel() {
        return null;
    }

    @Override
    public void valueChanged() {
    }
}
