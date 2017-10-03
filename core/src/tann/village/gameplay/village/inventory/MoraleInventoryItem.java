package tann.village.gameplay.village.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Buff;
import tann.village.screens.gameScreen.panels.inventoryStuff.InventoryItemPanel;
import tann.village.screens.gameScreen.panels.inventoryStuff.MoraleCompass;
import tann.village.util.Colours;

public class MoraleInventoryItem extends InventoryItem {

    Array<MoralePoint> points = new Array<>();
    Array<MoraleRange> ranges = new Array<>();
    public MoraleInventoryItem(int min, int max) {
        super(Eff.EffectType.Morale);
        setBounds(min, max);
        points.add(new MoralePoint(min, null));
        points.add(new MoralePoint(max, null));

        points.add(new MoralePoint(5, new Eff().food(1)));
        points.add(new MoralePoint(9, new Eff().wood(1)));
        points.add(new MoralePoint(11, new Eff().fate(1)));
        points.add(new MoralePoint(-5, new Eff().death(1)));

        ranges.add(new MoraleRange(-5,-2, Colours.red, new Eff(new Buff().rerolls(-1))));
        ranges.add(new MoraleRange(2,5,Colours.green_dark, new Eff(new Buff().rerolls(1))));
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
        for(MoralePoint mp: points){
            if((mp.morale>0 && mp.morale<=getValue()) || (mp.morale<0 && mp.morale>=getValue())){
                mp.trigger();
                points.removeValue(mp, true);
            }
        }
    }

    public Array<Eff> getActiveEffects(){
        Array<Eff> result = new Array<>();
        for(MoraleRange mr:ranges){
            if(mr.isActive(getValue())){
                result.addAll(Eff.copyArray(mr.effs));
            }
        }
        return result;
    }

    @Override
    public boolean canChangeBy(int delta) {
        return true;
    }
}
