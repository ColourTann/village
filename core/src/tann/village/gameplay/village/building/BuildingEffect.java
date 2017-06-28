package tann.village.gameplay.village.building;

import tann.village.gameplay.effect.Effect;

public class BuildingEffect {

	public enum BuildingEffectType{Now, EveryTurn}

	public BuildingEffectType effectType;
	public Effect[] effects;

	public BuildingEffect(BuildingEffectType type, Effect[] effects) {
		this.effectType=type;
		this.effects=effects;
	}
	
}
