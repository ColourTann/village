package tann.village.gameplay.effect;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.Images;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.village.villager.die.Die;

public class Eff {

    public Eff getInverse() {
        return new Eff(type, -value, sourceDie, effectActivation);
    }

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
	

	public final EffectType type;
	public int value;
	public Die sourceDie;
    public final EffAct effectActivation;

	public Eff(EffectType type, int value, Die sourceDie, EffAct effectActivation){
        this.type=type; this.value=value;  this.sourceDie=sourceDie; this.effectActivation = effectActivation;
    }
    public Eff(EffectType type, int value, Die sourceDie){this(type, value, sourceDie, EffAct.now);}
    public Eff(EffectType type, int value, EffAct effectActivation){
        this(type,value,null, effectActivation);
    }
	public Eff(EffectType type, int value){
	    this(type,value, null, null);
    }
    public Eff(EffectType type){this(type, 0);}

	public void activate(){
        Eff e = this.copy();
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
                break;
            case Gem:
                GameScreen.get().island.objectiveProgress(Objective.ObjectiveEffect.Gem, this.value);
                break;
        }

        GameScreen.get().addEffect(this);
    }

	public String toString(){
		return type +": "+value;
	}
	
	public Eff copy(){
		Eff result = new Eff(type, value, sourceDie);
		return result;
	}

	public String getValueString() {
		return (value>0?"+":"")+value;
	}
	
}
