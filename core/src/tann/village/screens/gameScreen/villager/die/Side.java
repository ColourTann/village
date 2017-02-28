package tann.village.screens.gameScreen.villager.die;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.Images;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;

public class Side {

	private static EffectSource d = EffectSource.Dice;
	public TextureRegion tr;
	public Effect[] effects;
	public Side(TextureRegion tr, Effect effect){
		this.tr=tr;
		this.effects=new Effect[]{effect};
	}
	
	public Side(TextureRegion tr, Effect effect, Effect effect2, Effect effect3) {
		this.tr=tr;
		this.effects=new Effect[]{effect, effect2, effect3};
	}
	
	public Side(TextureRegion tr, Effect effect, Effect effect2){
		this.tr=tr;
		this.effects=new Effect[]{effect, effect2};
	}
	
	public Side(TextureRegion tr, Effect[] effects){
		this.tr=tr;
		this.effects=effects;
	}
	
	

	public static Side food1 = new Side(Images.side_food_1, new Effect(EffectType.Food, 1, d));
	public static Side food2 = new Side(Images.side_food_2, new Effect(EffectType.Food, 2, d));
	public static Side food3 = new Side(Images.side_food_3, new Effect(EffectType.Food, 3, d));
	public static Side wood1 = new Side(Images.side_wood_1, new Effect(EffectType.Wood, 1, d));
	public static Side wood2 = new Side(Images.side_wood_2, new Effect(EffectType.Wood, 2, d));
	public static Side wood3 = new Side(Images.side_wood_3, new Effect(EffectType.Wood, 3, d));
	public static Side food1wood1 = make(Images.side_food_1_wood_1, EffectType.Wood, 1, EffectType.Food, 1);
	public static Side skull = new Side(Images.side_skull, new Effect(EffectType.Skull, d));
	public static Side brain = make(Images.side_brain, EffectType.Brain, 1);
	public static Side TwoMoraleForTwoFood = make(Images.side_morale_2_minus_2_food, EffectType.Food, -2, EffectType.Morale, 2);

	public static Side morale1 = new Side(Images.side_morale_1, new Effect(EffectType.Morale, 1, d));
	public static Side morale2 = make(Images.side_morale_2, EffectType.Morale, 2);
	public static Side fate1= make(Images.side_fate_1, EffectType.Fate, 1);
	public static Side fate2ForWoodAndFood= make(Images.side_2fateForWoodAndFood, EffectType.Fate, 2, EffectType.Wood, -1, EffectType.Food, -1);
	public static Side fateForWood= make(Images.side_fateForWood, EffectType.Fate, 1, EffectType.Wood, -1);
	public static Side fateForFood= make(Images.side_fateForFood, EffectType.Fate, 1, EffectType.Food, -1);
	public static Side fate3= make(Images.side_fate_3, EffectType.Fate, 3);
	
	
	private static Side make(TextureRegion image, EffectType type, int value){
		return new Side(image, new Effect(type, value, d));
	}
	
	private static Side make(TextureRegion image, EffectType type, int value, EffectType type2, int value2){
		return new Side(image, new Effect(type, value, d), new Effect(type2, value2, d));
	}
	
	private static Side make(TextureRegion image, EffectType type, int value, EffectType type2, int value2, EffectType type3, int value3){
		return new Side(image, new Effect(type, value, d), new Effect(type2, value2, d), new Effect(type3, value3, d));
	}
	
	public Side copy(){
		return new Side(tr, effects.clone());
	}
}
