package tann.village.screens.gameScreen.villager.die;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.Images;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;

public class Side {

	public TextureRegion tr;
	public Effect effect;
	public Side(TextureRegion tr, Effect effect){
		this.tr=tr;
		this.effect=effect;
	}
	
	
	public static Side food1 = new Side(Images.side_food_1, new Effect(EffectType.Food, 1, EffectSource.Dice));
	public static Side food2 = new Side(Images.side_food_2, new Effect(EffectType.Food, 2, EffectSource.Dice));
	public static Side food3 = new Side(Images.side_food_3, new Effect(EffectType.Food, 3, EffectSource.Dice));
	public static Side wood1 = new Side(Images.side_wood_1, new Effect(EffectType.Wood, 1, EffectSource.Dice));
	public static Side wood2 = new Side(Images.side_wood_2, new Effect(EffectType.Wood, 2, EffectSource.Dice));
	public static Side skull = new Side(Images.side_skull, new Effect(EffectType.Skull, EffectSource.Dice));
	public static Side brain = new Side(Images.side_brain, new Effect(EffectType.Brain, 2, EffectSource.Dice));
	

	public static Side morale1 = new Side(Images.side_morale_1, new Effect(EffectType.Morale, 1, EffectSource.Dice));
	public static Side morale2 = make(Images.side_morale_2, EffectType.Morale, 2);
	public static Side fate2= make(Images.side_fate_2, EffectType.Fate, 2);;
	public static Side fate3= make(Images.side_fate_3, EffectType.Fate, 3);;
	
	private static Side make(TextureRegion image, EffectType type){
		return make(image,type,1);
	}
	
	private static Side make(TextureRegion image, EffectType type, int value){
		return new Side(image, new Effect(type, value, EffectSource.Dice));
	}
	
	public Side copy(){
		return new Side(tr, effect.copy());
	}
}
