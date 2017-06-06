package tann.village.gameplay.village.building;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.building.BuildingEffect.BuildingEffectType;

public class Building {
	
	
	public String name;
	String description;
	public int level;
	public Cost cost;
	
	public Array<BuildingEffect> buildingEffects;
	public TextureRegion image = Main.atlas.findRegion("building/hut");
	
	
	
	public Building(String name, String description, int level, Cost cost, Array<BuildingEffect> buildingEffects) {
		this.name=name;
		this.description=description;
		this.level=level;
		this.cost=cost;
		this.buildingEffects = buildingEffects;
	}
	
	
	
	

	public void onBuild() {
		for(BuildingEffect bEff:buildingEffects){
			if(bEff.effectType==BuildingEffectType.Now){
				for(Effect e:bEff.effects){
					e.activate(false);
				}
			}
		}
	}
	

	public void upkeep() {
		for(BuildingEffect bEff:buildingEffects){
			if(bEff.effectType==BuildingEffectType.EveryTurn){
				for(Effect e:bEff.effects){
					e.activate(true);
				}
			}
		}
	}
}
