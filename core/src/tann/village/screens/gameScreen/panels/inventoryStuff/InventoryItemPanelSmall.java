package tann.village.screens.gameScreen.panels.inventoryStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.util.Colours;

public class InventoryItemPanelSmall extends InventoryItemPanel{
    public InventoryItemPanelSmall(TextureRegion tr, int value, int max) {
        super(tr, value, max);
        this.border = Colours.brown_dark;
    }

    @Override
    public void layout() {
        setSize(invPanelWidth()*.6f, invPanelHeight()*.5f);
        setup();
    }

}
