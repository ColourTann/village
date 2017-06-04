package tann.village.gameplay.effect;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.Images;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.village.villager.die.Die;
import tann.village.gameplay.village.Inventory;

public class Effect {

	public enum EffectType{
        Food(Images.food),
        Wood(Images.wood),
		Skull(Images.side_skull),
        LevelUp(Images.level_up),
        Morale(Images.morale),
        FoodStorage(Images.food_storage),
        Fate(Images.fate),
        Brain(Images.brain),
        Reroll(Images.roll),
        Gem(Images.gem),
        Survive(Images.village, true),
        BuildTown(Images.village, true),
        FoodBonus(),
        CollectGems(Images.village, true),
        TimeLimit(Images.village, true);

        public boolean objective;
        public TextureRegion region;
		boolean special;
        EffectType(){
            this.region=Images.dotdotdot;
        }
        EffectType(TextureRegion region){
            this.region=region;
        }
        EffectType(TextureRegion region, boolean objective){
            this.region=region; this.objective=objective;
        }
		EffectType(TextureRegion region, String extraText){
			this.region=region;
			this.special=true;
		}
	}
	
	public enum EffectSource{
		Dice, Upkeep, Event, Building
	}
	
	
	public final EffectType type;
	public final EffectSource source;
	public int value;
	public int duration;
	public Die sourceDie;

    public Effect(EffectType type, int value, EffectSource source, Die sourceDie, int duration){
        this.type=type; this.value=value; this.source=source; this.sourceDie=sourceDie; this.duration = duration;
    }

    public Effect(EffectType type, int value, EffectSource source, int duration){
       this(type, value, source, null, duration);
    }

	public Effect(EffectType type, int value, EffectSource source, Die sourceDie){
	        this(type,value,source,sourceDie, -1);
    }

    public Effect(EffectType type, int value,  Die sourceDie){
        this(type,value,EffectSource.Dice, sourceDie);
    }

	public Effect(EffectType type, int value, EffectSource source){
		this(type,value,source,null);
	}

	public Effect(EffectType type, EffectSource source){
		this(type, 0, source);
	}

	public void activate(){
        Effect e = this.copy();
        e.internalActivate();
	}

	private void internalActivate(){
        switch(type){
            case FoodStorage:
                Village.getInventory().get(EffectType.Food).addMax(value);
                return;
            case Brain:
                sourceDie.villager.gainXP(value);
                break;
            case Reroll:
            case FoodBonus:
                Village.get().addBuff(this);
        }
        GameScreen.get().addEffect(this);
    }

	public String toString(){
		return type +": "+value+" from "+source; 
	}
	
	public Effect copy(){
		Effect result = new Effect(type, value, source, sourceDie);
		return result;
	}

	public String getValueString() {
		return (value>0?"+":"")+value;
	}
	
}
