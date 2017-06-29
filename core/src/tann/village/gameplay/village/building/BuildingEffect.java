package tann.village.gameplay.village.building;

import tann.village.gameplay.effect.Eff;

public class BuildingEffect {

	public enum BuildingEffectType{Now, EveryTurn}

	public BuildingEffectType effectType;
	public Eff[] effects;

	public BuildingEffect(BuildingEffectType type, Eff[] effects) {
		this.effectType=type;
		this.effects=effects;
	}
	
}
