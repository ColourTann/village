package tann.village.gameplay.village.building;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Effect;

public class BuildingEffect {

	public enum BuildingEffectType{Now, EveryTurn, Permanent, Passive}

	public enum PermanentBonusType{}
	
	public enum BuildingPassiveType{}
	
	public BuildingEffectType effectType;
	public Effect[] effects;
	PermanentBonusType perm;
	BuildingPassiveType pass;
	
	public BuildingEffect(BuildingEffectType type, Effect[] effects) {
		this.effectType=type;
		this.effects=effects;
	}
	
	public BuildingEffect(PermanentBonusType permanentBonusType){
		this.effectType=BuildingEffectType.Permanent;
		this.perm = permanentBonusType;
	}
	
	public BuildingEffect(BuildingPassiveType passiveType){
		this.effectType=BuildingEffectType.Passive;
		this.pass = passiveType;
	}
	
	
	
	
	
}
