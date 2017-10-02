package tann.village.gameplay.village.inventory;

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
