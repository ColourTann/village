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

    Array<MoralePoint> points = new Array<>();
    Array<MoraleRange> ranges = new Array<>();
    public MoraleInventoryItem(int min, int max) {
        super(Eff.EffectType.Morale);
        setBounds(min, max);
        points.add(new MoralePoint(min, null,null));
        points.add(new MoralePoint(max, null,null));

        points.add(new MoralePoint(5, Images.food, new Eff[]{new Eff().food(5), new Eff().storage(2)}));
        points.add(new MoralePoint(9, Images.wood, new Eff[]{new Eff().wood(6)}));
        points.add(new MoralePoint(11, Images.fate, new Eff[]{new Eff().fate(4)}));
        points.add(new MoralePoint(-5, Images.skull, new Eff[]{new Eff().lose()}));

        ranges.add(new MoraleRange(-6,-2, Colours.red, new Eff(new Buff().rerolls(-1))));
        ranges.add(new MoraleRange(2,5,Colours.green_light,
                new Eff[]{new Eff(new Buff().rerolls(1))}));
        ranges.add(new MoraleRange(5,10,Colours.blue_light,
                new Eff[]{
                        new Eff(new Buff().rerolls(1)),
                        new Eff(new Buff().bonusFood(1)),
                        new Eff().morale(-1)
        }));
        ranges.add(new MoraleRange(10,15,Colours.light,
                new Eff[]{
                    new Eff(new Buff().rerolls(1)),
                    new Eff(new Buff().bonusFood(3)),
                    new Eff().morale(-2)
        }));
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
