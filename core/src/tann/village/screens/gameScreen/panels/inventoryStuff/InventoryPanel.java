package tann.village.screens.gameScreen.panels.inventoryStuff;
import com.badlogic.gdx.graphics.g2d.Batch;
import tann.village.Images;
import tann.village.Main;
import tann.village.gameplay.village.inventory.Inventory;
import tann.village.gameplay.village.inventory.InventoryItem;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Lay;
import tann.village.util.Layoo;

public class InventoryPanel extends Lay {
    Inventory inventory;
    public InventoryPanel(Inventory inventory){
        this.inventory = inventory;
        layout();
    }

    @Override
    public void layout() {
        setSize(InventoryItemPanel.invPanelWidth(), Main.height - GameScreen.getConstructionCircleSize());
        layout(false);
    }

    public void layout(boolean slide){
        clearChildren();
        Layoo l = new Layoo(this);
        for(int i=inventory.items.size-1;i>=0;i--){
            InventoryItem item = inventory.items.get(i);
            InventoryItemPanel panel = item.getPanel();
            if(panel.getY()==0){
                panel.setPosition(-panel.getWidth(), getHeight()-panel.getHeight());
            }
            l.actor(panel);
            if(panel.tr==Images.food_storage) l.gap(1);
            if(panel.tr==Images.food) l.row(0);
            else l.row(1);
        }
        l.layoo(slide);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }


}
