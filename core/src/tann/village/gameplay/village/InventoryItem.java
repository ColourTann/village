package tann.village.gameplay.village;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.screens.gameScreen.InventoryItemPanel;

public class InventoryItem {

    private int min, max, value;
    private TextureRegion image;

    public InventoryItem(TextureRegion image) {
        this(image, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public InventoryItem(TextureRegion image, int max) {
        this(image, Integer.MIN_VALUE, max);
    }

    public InventoryItem(TextureRegion image, int min, int max) {
        this.max=max;
        this.min=min;
        this.image=image;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value=value;
        valueChanged();
    }

    public void changeValue(int delta){
        setValue(this.value += delta);
        valueChanged();
    }

    public boolean canChangeBy(int delta){
        return value+delta > min && value+delta < max;
    }

    public void addMax(int delta){
        this.max+=delta;
    }

    public void imposeMaximum(){
        this.value = Math.min(max, value);
        valueChanged();
    }

    public void imposeMinimum(){
        this.value = Math.max(0, value);
        valueChanged();
    }

    public void valueChanged(){
        getPanel().setValue(getValue());
    }

    private InventoryItemPanel panel;
    public InventoryItemPanel getPanel(){
        if(panel==null){
            panel = new InventoryItemPanel(image, value, max);
        }
        return panel;
    }


}
