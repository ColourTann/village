package tann.village.gameplay.effect;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff.EffectType;

public class Cost {
	public Array<Eff> effects = new Array<>();

	public Cost(){}

    public Cost wood(int amount){
        return add(EffectType.Wood, amount);
    }

    public Cost food(int amount){
        return add(EffectType.Food, amount);
    }

    public Cost fate(int amount){
        return add(EffectType.Fate, amount);
    }

    private Cost add(EffectType type, int amount){
	    effects.add(new Eff(type, amount));
	    return this;
    }

    public String toWriterString() {
        String result ="";
        for(Eff e:effects){
            if(result.length()!=0) result += " ";
            result += e.value+"[h]["+e.typeString()+"]";
        }
        return result;
    }
}
