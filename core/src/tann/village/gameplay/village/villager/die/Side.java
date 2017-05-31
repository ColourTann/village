package tann.village.gameplay.village.villager.die;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.Images;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;

public class Side {

	private static EffectSource d = EffectSource.Dice;
	public TextureRegion[] tr;
	public Effect[] effects;
	public Side(TextureRegion tr[], Effect effect){
		this.tr=tr;
		this.effects=new Effect[]{effect};
	}
	
	public Side(TextureRegion tr[], Effect effect, Effect effect2, Effect effect3) {
		this.tr=tr;
		this.effects=new Effect[]{effect, effect2, effect3};
	}
	
	public Side(TextureRegion tr[], Effect effect, Effect effect2){
		this.tr=tr;
		this.effects=new Effect[]{effect, effect2};
	}
	
	public Side(TextureRegion tr[], Effect[] effects){
		this.tr=tr;
		this.effects=effects;
	}
	
	

	public static Side food1 = new Side(Images.get("food1"), new Effect(EffectType.Food, 1, d));
	public static Side food2 = new Side(Images.get("food2"), new Effect(EffectType.Food, 2, d));
	public static Side food3 = new Side(Images.get("food3"), new Effect(EffectType.Food, 3, d));
	public static Side wood1 = new Side(Images.get("wood1"), new Effect(EffectType.Wood, 1, d));
	public static Side wood2 = new Side(Images.get("wood2"), new Effect(EffectType.Wood, 2, d));
	public static Side wood3 = new Side(Images.get("wood2"), new Effect(EffectType.Wood, 3, d));
	public static Side food1wood1 = make(Images.get("food1wood1"), EffectType.Wood, 1, EffectType.Food, 1);
	public static Side skull = make(Images.get("nothing"), EffectType.Skull, 1);
	public static Side brain = make(Images.get("brain"), EffectType.Brain, 1);
	public static Side TwoMoraleForTwoFood = make(Images.get("morale_2_minus_2_food"), EffectType.Food, -2, EffectType.Morale, 2);

	public static Side morale1 = make(Images.get("morale1"), EffectType.Morale, 1);
	public static Side morale2 = make(Images.get("morale2"), EffectType.Morale, 2);
    public static Side fate1= make(Images.get("fate1"), EffectType.Fate, 1);
    public static Side fate2= make(Images.get("fate2"), EffectType.Fate, 2);
    public static Side fate2ForWoodAndFood= make(Images.get("fate2woodminus1foodminus1"), EffectType.Fate, 2, EffectType.Wood, -1, EffectType.Food, -1);
    public static Side gem1 = make(Images.get("emerald"), EffectType.Gem, 2, EffectType.Wood, -1, EffectType.Food, -1);
	public static Side fateForWood= make(Images.get("fate1woodminus1"), EffectType.Fate, 1, EffectType.Wood, -1);
	public static Side fateForFood= make(Images.get("fate1foodminus1"), EffectType.Fate, 1, EffectType.Food, -1);
	
	
	private static Side make(TextureRegion[] image, EffectType type, int value){
		return new Side(image, new Effect(type, value, d));
	}
	
	private static Side make(TextureRegion[] image, EffectType type, int value, EffectType type2, int value2){
		return new Side(image, new Effect(type, value, d), new Effect(type2, value2, d));
	}
	
	private static Side make(TextureRegion[] image, EffectType type, int value, EffectType type2, int value2, EffectType type3, int value3){
		return new Side(image, new Effect(type, value, d), new Effect(type2, value2, d), new Effect(type3, value3, d));
	}
	
	public Side copy(){
		Effect[] newEffects = new Effect[effects.length];
		for(int i=0;i<effects.length;i++){
			newEffects[i] = new Effect(effects[i].type, effects[i].value, effects[i].source);
		}
		return new Side(tr, newEffects);
	}
}
