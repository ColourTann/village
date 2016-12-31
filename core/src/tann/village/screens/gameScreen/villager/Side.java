package tann.village.screens.gameScreen.villager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.Images;
import tann.village.screens.gameScreen.Effect;
import tann.village.screens.gameScreen.Effect.EffectType;

public class Side {

	public TextureRegion tr;
	public Effect effect;
	public Side(TextureRegion tr, Effect effect){
		this.tr=tr;
		this.effect=effect;
	}
	
	public static Side food1 = new Side(Images.side_food_1, new Effect(EffectType.Dice_Food, 1));
	public static Side food2 = new Side(Images.side_food_2, new Effect(EffectType.Dice_Food, 2));
	public static Side food3 = new Side(Images.side_food_3, new Effect(EffectType.Dice_Food, 3));
	public static Side wood1 = new Side(Images.side_wood_1, new Effect(EffectType.Dice_Wood, 1));
	public static Side wood2 = new Side(Images.side_wood_2, new Effect(EffectType.Dice_Wood, 2));
	public static Side skull = new Side(Images.side_skull, new Effect(EffectType.Skull));
	public static Side brain = new Side(Images.side_brain, new Effect(EffectType.Brain));
}
