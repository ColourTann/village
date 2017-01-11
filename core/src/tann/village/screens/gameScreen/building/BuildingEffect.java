package tann.village.screens.gameScreen.building;

import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.effect.Effect;

public class BuildingEffect {

	public enum BuildingEffectType{Now, EveryTurn, PermanentBonus, Passive}

	public enum PermanentBonusType{}
	
	public enum BuildingPassiveType{}
	
	BuildingEffectType effectType;
	Effect[] effects;
	PermanentBonusType perm;
	BuildingPassiveType pass;
	
	public BuildingEffect(BuildingEffectType type, Effect[] effects) {
		this.effectType=type;
		this.effects=effects;
	}
	
	public BuildingEffect(PermanentBonusType permanentBonusType){
		this.effectType=BuildingEffectType.PermanentBonus;
		this.perm = permanentBonusType;
	}
	
	public BuildingEffect(BuildingPassiveType passiveType){
		this.effectType=BuildingEffectType.Passive;
		this.pass = passiveType;
	}
	
	
	
	
	
}
