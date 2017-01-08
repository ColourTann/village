package tann.village.screens.gameScreen.building;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.screens.gameScreen.Cost;
import tann.village.screens.gameScreen.building.BuildingEffect.BuildingEffectType;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;

public class Building {
	
	
	String name;
	String description;
	Cost cost;
	
	Array<BuildingEffect> buildingEffects;
	public TextureRegion image = Main.atlas.findRegion("resource/wood");
	
	
	
	public Building(String name, String description, Cost cost, Array<BuildingEffect> buildingEffects) {
		this.name=name;
		this.description=description;
		this.cost=cost;
		this.buildingEffects = buildingEffects;
	}
	
	
	
	public static Array<Building> buildings = new Array<>();
	
	private static String n, d;
	private static BuildingEffect b1;
	private static BuildingEffect b2;
	private static int w,f;
	public static void init(){
		EffectSource b = EffectSource.Building;
		n="Dock";
		d="A short pier leading into the ocean";
		w=5;
		b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Food, 1, b)});
		make();
		
		n="Palm Grove";
		d="A small grove for harvesting fast-growing trees";
		w=9; f=2;
		b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Food, 1, b), new Effect(EffectType.Wood, 1, b)});
		make();
		
		n="Shrimp Traps";
		d="An effective food source! Requires maintenance though";
		w=4;
		b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Food, 3, b), new Effect(EffectType.Wood, -1, b)});
		make();
		
		//altar
		
		//shrine
		
		//treehouse
		
		//+3 fate when built 
		//pay 2 food to skip event
		
		//+4 fate when built
		//+1 fate per turn
		
		//now
		//each turn
		//permanent bonus
		//ability or passive
	}
	
	
	
	
	private static void make(){
		if(n==null||d==null||b1==null){
			System.out.println("Something went wrong making "+n+":"+d);
		}
		Array<BuildingEffect> effects = new Array<>();
		if(b1!=null)effects.add(b1);
		if(b2!=null)effects.add(b2);
		buildings.add(new Building(n, d, new Cost(w, f), effects));
		w=0; f=0;
		n=null; d=null;
		b1=null;
		b2=null;
	}
	
}
