package tann.village.screens.gameScreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.Images;

public class Effect {

	public enum EffectType{
		Brain(Images.side_brain, "dice"), 
		Dice_Food(Images.food, "dice"), Upkeep_Food(Images.food, "upkeep"), Event_Food(Images.food, "event"), 
		Dice_Wood(Images.wood, "dice"), Upkeep_Wood(Images.wood, "upkeep"), Event_Wood(Images.wood, "event"),
		Skull(Images.side_skull, "dice"), 
		Morale_Event(Images.morale, "event");

		public String text;
		public TextureRegion region;
		
		EffectType(TextureRegion region, String text){
			this.region=region;
			this.text=text;
		}
	
	}
	
	
	public EffectType type;
	public int value;
	public Effect(EffectType type, int value){
		this.type=type;
		this.value=value;
	}
	
	public Effect(EffectType type){
		this.type=type;
		this.value=1;
	}
	
	public void activate(){
		switch(type){
		case Dice_Food:
		case Upkeep_Food:
		case Event_Food:
			GameScreen.get().addFood(value, type);
			break;
		case Dice_Wood:
		case Upkeep_Wood:
		case Event_Wood:
			GameScreen.get().addWood(value, type);
			break;
		}
	}
	
}
