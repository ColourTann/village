package tann.village.gameplay.village.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import tann.village.Images;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Buff;
import tann.village.screens.gameScreen.panels.inventoryStuff.InventoryItemPanel;
import tann.village.screens.gameScreen.panels.inventoryStuff.MoraleCompass;
import tann.village.util.Colours;

public class MoraleInventoryItem extends InventoryItem {

    Array<MoralePoint> points;
    Array<MoraleRange> ranges;
    public MoraleInventoryItem(int min, int max, Array<MoralePoint> points, Array<MoraleRange> ranges) {
        super(Eff.EffectType.Morale);
        setBounds(min, max);
        this.points = points;
        this.ranges=ranges;
        points.add(new MoralePoint(min, null,null));
        points.add(new MoralePoint(max, null,null));
    }

    private int min, max;
    private void setBounds(int min, int max) {
        this.min = min; this.max = max;
    }

    @Override
    public InventoryItemPanel getPanel() {
        if(panel==null){
           panel = new MoraleCompass(min, max, points, ranges);
        }
        return panel;
    }

    @Override
    public void valueChanged() {
        super.valueChanged();
        for(int i=points.size-1; i>=0; i--){
            MoralePoint mp = points.get(i);
            if((mp.morale>0 && mp.morale<=getValue()) || (mp.morale<0 && mp.morale>=getValue())){
                mp.trigger();
                points.removeValue(mp, true);
            }
        }
    }

    public void activateRanges(){
        Array<Eff> result = new Array<>();
        for(MoraleRange mr:ranges){
            if(mr.isActive()){
                mr.activate();
            }
        }
    }

    @Override
    public boolean canChangeBy(int delta) {
        return true;
    }
}
