package tann.village.screens.gameScreen.building;

import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.Cost;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;

public class Building {
	
	public enum BuildingActivationType{
		StartRolling, Review
	}
	
	String name;
	String description;
	Cost cost;
	BuildingActivationType activationType;
	Array<Effect> effects;
	
	public Building(String name, String description, Cost cost, BuildingActivationType activationType, Array<Effect> effects) {
		this.name=name;
		this.description=description;
		this.cost=cost;
		this.activationType=activationType;
		this.effects=effects;
	}
	
	public void activate(BuildingActivationType type){
		if(this.activationType != type) return;
		for(Effect e:effects){
			e.activate();
		}
	}
	
	
	public static Array<Building> buildings = new Array<>();
	
	private static String n, d;
	private static BuildingActivationType t;
	private static int w,f;
	private static Effect e1, e2, e3;
	public static void init(){
		EffectSource b = EffectSource.Building;
		n="Dock";
		d="A short pier leading into the ocean";
		w=5;
		t=BuildingActivationType.Review;
		e1=new Effect(EffectType.Food, 1, b);
		make();
		
		n="Palm Grove";
		d="A small grove for harvesting fast-growing trees";
		w=9; f=2;
		t=BuildingActivationType.Review;
		e1=new Effect(EffectType.Food, 1, b);
		e2=new Effect(EffectType.Wood, 1, b);
		make();
		
		n="Shrimp Traps";
		d="An effective food source! Requires maintenance though";
		w=4;
		t=BuildingActivationType.Review;
		e1=new Effect(EffectType.Food, 3, b);
		e2=new Effect(EffectType.Wood, -1, b);
		make();
	}
	
	private static void make(){
		if(n==null||d==null||t==null||e1==null){
			System.out.println("Something went wrong making "+n+":"+d);
		}
		Array<Effect> effects = new Array<>();
		if(e1!=null)effects.add(e1);
		if(e2!=null)effects.add(e2);
		if(e3!=null)effects.add(e3);
		buildings.add(new Building(n, d, new Cost(w, f), t, effects));
		w=0; f=0;
		e1=null; e2=null; e3=null;
		n=null; d=null; t=null; 
	}
	
}
