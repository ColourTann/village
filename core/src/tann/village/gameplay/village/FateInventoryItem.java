package tann.village.gameplay.village;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.screens.gameScreen.panels.inventoryStuff.InventoryItemPanel;

public class FateInventoryItem extends InventoryItem {
    public FateInventoryItem(TextureRegion image) {
        super(image);
    }

    @Override
    public InventoryItemPanel getPanel() {
        return null;
    }

    @Override
    public void valueChanged() {
    }
}
