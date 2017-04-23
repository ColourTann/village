package tann.village.gameplay.effect;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;

public class Cost {
	public Array<Effect> effects = new Array<>();
	
	public Cost(int wood, int food){
		EffectSource b = EffectSource.Building;
		if(wood>0) effects.add(new Effect(EffectType.Wood, wood, b));
		if(food>0) effects.add(new Effect(EffectType.Food, food, b));
	}
	
}
