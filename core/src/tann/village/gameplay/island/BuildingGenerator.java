package tann.village.gameplay.island;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.village.building.Building;

public class BuildingGenerator {

	private static Array<Building> buildings = new Array<>();
	private static Building b = new Building();
	public static void makeBasicBuildings(){
	    b=new Building();
        b.name="Dock";
        b.description="A short pier leading into the ocean";
        b.cost= new Cost(8,0);
        b.effects.add(new Eff().eachTurn(10).food(1));
        make(b);

        b=new Building();
        b.name="Bonfire";
        b.description="A big bonfire can really bring the community together";
        b.cost = new Cost(4,0);
        b.effects.add(new Eff().morale(2));
        make(b);

        b=new Building();
        b.name="Offering";
        b.description="If the gods exist, it's a good idea to get on their good side";
        b.cost=new Cost(4,2);
        b.effects.add(new Eff().morale(1));
        b.effects.add(new Eff().fate(2));
        make(b);

        b = new Building();
        b.name="Crate";
        b.description="A little extra storage for food can help out when times are hard";
        b.cost=new Cost(3,0);
        b.effects.add(new Eff().storage(2));
        make(b);

        b = new Building();
        b.name="Salvage Hut";
        b.description="A place to sort through useful materials";
        b.cost=new Cost(7,0);
        b.effects.add(new Eff().eachTurn(10).food(1));
        make(b);

        // *********************level 1********************* //

//        l=1;
        b = new Building();
        b.name="Palm Grove";
        b.description="A small grove for harvesting fast-growing trees";
        b.cost = new Cost(11,3);
        b.effects.add(new Eff().eachTurn(10).food(1));
        b.effects.add(new Eff().eachTurn(10).wood(1));
        make(b);

        b = new Building();
        b.name="Larder";
        b.description="Large storage area for food";
        b.cost=new Cost(5,0);
        b.effects.add(new Eff(EffectType.FoodStorage, 6));
        make(b);

        b = new Building();
        b.name="Shrine";
        b.description="An offering to the gods";
        b.cost=new Cost(10,0);
        b.effects.add(new Eff(EffectType.Fate, 4));
        b.effects.add(new Eff(EffectType.Morale, 1));
        make(b);
    }

    public static void makeGemBuildings(){
        b = new Building();
        b.name= "Mining";
        b.cost = new Cost(5,3);
        b.effects.add(new Eff(EffectType.Gem, 3));
        b.effects.add(new Eff(EffectType.Morale, -1));
        make(b);

        b = new Building();
        b.name= "Fountain";
        b.cost=new Cost(15,5);
        b.effects.add(new Eff(EffectType.Gem, 5));
        make(b);

        b = new Building();
        b.name= "Expedition";
        b.cost=new Cost(10,2);
        b.effects.add(new Eff(EffectType.Gem, 2));
        b.effects.add(new Eff(EffectType.Morale, 1));
        make(b);

        b = new Building();
        b.name= "Ocean sifting";
        b.cost=new Cost(4,1);
        b.effects.add(new Eff(EffectType.Gem, 1));
        make(b);
    }

    private static void make(Building b){
		if(b.name==null||b.effects.size==0||buildings.contains(b, true)){
			System.err.println("Something went wrong making "+b.name+":"+b.description);
			return;
		}
		buildings.add(b);
	}

	public static Array<Building> getBuildings(){
        Array<Building> result = buildings;
        buildings = new Array<>();
        return result;
    }
}
