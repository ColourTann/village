package tann.village.gameplay.island;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.gameplay.village.building.Building;
import tann.village.gameplay.village.building.BuildingEffect;
import tann.village.gameplay.village.building.BuildingEffect.BuildingEffectType;

public class BuildingGenerator {

	public static Map<Integer, Array<Building>> buildings = new HashMap<>();
	private static String title, description;
	private static BuildingEffect b1;
	private static BuildingEffect b2;
	private static int w,f,l;
	public static void init(int level){
		EffectSource b = EffectSource.Building;
		buildings.put(level, new Array<Building>());
		switch(level){
		case 0:
			l=0; // *********************level 0********************* //
			title="Dock";
			description="A short pier leading into the ocean";
			w=9;
			b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Food, 1, b)});
			make();
			
			title="Bonfire";
			description="A big bonfire can really bring the community together";
			w=4;
			b1 = new BuildingEffect(BuildingEffectType.Now, new Effect[]{new Effect(EffectType.Morale, 1, b)});
			make();
			
			title="Offering";
			description="If the gods exist, it's a good idea to get on their favour";
			w=4;
			f=2;
			b1 = new BuildingEffect(BuildingEffectType.Now, new Effect[]{new Effect(EffectType.Morale, 1, b)});
			b2 = new BuildingEffect(BuildingEffectType.Now, new Effect[]{new Effect(EffectType.Fate, 1, b)});
			make();
			
			title="Crate";
			description="A little extra storage for food can help out when times are hard";
			w=5;
			b1 = new BuildingEffect(BuildingEffectType.Permanent, new Effect[]{new Effect(EffectType.FoodStorage, 3, b)});
			make();
			
			title="Salvage Hut";
			description="A place to sort through useful materials";
			w=8;
			b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Wood, 1, b)});
			make();
			break;
			
		case 1:
			l=1; // *********************level 1********************* //
			title="Palm Grove";
			description="A small grove for harvesting fast-growing trees";
			w=10; f=4;
			b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Food, 1, b)});
			b2 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Wood, 1, b)});
			make();
			
			title="Meeting Circle";
			description="Your people can congregate here and have a chat!";
			w=9;
			b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Morale, 1, b)});
			b2= new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Food, -1, b)});
			make();
			
			title="Shrimp Traps";
			description="An effective food source! Requires maintenance though";
			w=8;
			f=6;
			b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Food, 3, b)});
			b2 = new BuildingEffect(BuildingEffectType.EveryTurn, new Effect[]{new Effect(EffectType.Wood, -1, b)});
			make();
			
			title="Larder";
			description="Large storage area for food";
			w=9;
			b1 = new BuildingEffect(BuildingEffectType.Permanent, new Effect[]{new Effect(EffectType.FoodStorage, 6, b)});
			make();
			
			title="Shrine";
			description="An offering to the gods";
			w=10;
			b1 = new BuildingEffect(BuildingEffectType.Now, new Effect[]{new Effect(EffectType.Fate, 2, b)});
			b2 = new BuildingEffect(BuildingEffectType.Now, new Effect[]{new Effect(EffectType.Morale, 1, b)});
			make();
			break;
		}
		
		
		
		
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
		if(title==null||description==null||b1==null){
			System.out.println("Something went wrong making "+title+":"+description);
		}
		Array<BuildingEffect> effects = new Array<>();
		if(b1!=null)effects.add(b1);
		if(b2!=null)effects.add(b2);
		buildings.get(l).add(new Building(title, description, l, new Cost(w, f), effects));
		w=0; f=0;
		title=null; description=null;
		b1=null;
		b2=null;
	}

	public static Building random(int level) {
		if(buildings.get(level)==null || buildings.get(level).size==0) init(level);
		return buildings.get(level).removeIndex((int)(Math.random()*buildings.get(level).size));
	}
}
