package tann.village.gameplay.effect;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff.EffectType;

public class Cost {
	public Array<Eff> effects = new Array<>();
	
	public Cost(int wood, int food){
		if(wood>0) effects.add(new Eff(EffectType.Wood, wood));
		if(food>0) effects.add(new Eff(EffectType.Food, food));
	}
	
}
