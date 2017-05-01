package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.scenes.scene2d.Group;
import tann.village.Images;
import tann.village.Main;
import tann.village.gameplay.village.Inventory;
import tann.village.gameplay.village.InventoryItem;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.InventoryItemPanel;
import tann.village.util.Layoo;

public class InventoryPanel extends Group {
    Inventory inventory;
    public static final int WIDTH = InventoryItemPanel.WIDTH, HEIGHT=350;
    public InventoryPanel(Inventory inventory){
        this.inventory = inventory;
        setSize(WIDTH, HEIGHT);
        Layoo l = new Layoo(this);
        for(int i=0;i<inventory.items.size;i++){
            InventoryItem item = inventory.items.get(i);
            InventoryItemPanel panel = item.getPanel();
            if(panel!=null) l.actor(panel);
            if(i<inventory.items.size-1){
                l.row(1);
            }
        }
        l.layoo();
    }
}
