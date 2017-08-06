package tann.village.gameplay.island;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.village.building.Building;

public class BuildingGenerator {

	private static Array<Building> buildings = new Array<>();
	private static String title, description;
	private static int w,f,l;
	private static Eff e1;
    private static Eff e2;
	public static void makeBasicBuildings(){

        l=0; // *********************level 0********************* //
        title="Dock";
        description="A short pier leading into the ocean";
        w=8;
        e1 =new Eff().eachTurn(10).food(1);
        make();

        title="Bonfire";
        description="A big bonfire can really bring the community together";
        w=4;
        e1 = new Eff(EffectType.Morale, 2);
        make();

        title="Offering";
        description="If the gods exist, it's a good idea to get on their good side";
        w=4;
        f=2;
        e1 =new Eff(EffectType.Morale, 1);
        e2 =new Eff(EffectType.Fate, 2);
        make();

        title="Crate";
        description="A little extra storage for food can help out when times are hard";
        w=3;
        e1 = new Eff(EffectType.FoodStorage, 2);
        make();

        title="Salvage Hut";
        description="A place to sort through useful materials";
        w=7;
        e1 = new Eff().eachTurn(10).wood(1);
        make();

        // *********************level 1********************* //

        l=1;
        title="Palm Grove";
        description="A small grove for harvesting fast-growing trees";
        w=11; f=3;
        e1 = new Eff().eachTurn(10).wood(1);
        e2 = new Eff().eachTurn(10).food(1);
        make();

        title="Larder";
        description="Large storage area for food";
        w=5;
        e1 = new Eff(EffectType.FoodStorage, 6);
        make();

        title="Shrine";
        description="An offering to the gods";
        w=10;
        e1 = new Eff().fate(4);
        e2 = new Eff().morale(1);
        make();
    }

    public static void makeGemBuildings(){
        title = "Mining";
        w = 5;
        f = 3;
        e1 = new Eff().gem(3);
        e2 = new Eff().morale(-1);
        make();

        title = "Fountain";
        w = 15;
        f = 5;
        e1 = new Eff().gem(5);
        make();

        title = "Expedition";
        w = 10;
        f = 2;
        e1 = new Eff().gem(2);
        e2 = new Eff().morale(1);
        make();

        title = "Ocean sifting";
        w = 4;
        f = 1;
        e1 = new Eff().gem(1);
        make();
    }

    private static void make(){
		if(title==null||e1==null){
			System.err.println("Something went wrong making "+title+":"+description);
			return;
		}
		Array<Eff> effects = new Array<>();
		if(e1!=null)effects.add(e1);
		if(e2!=null)effects.add(e2);
		buildings.add(new Building(title, description, l, new Cost(w, f), effects));
		w=0; f=0;
		title=null; description=null;
		e1=null;
		e2=null;
	}

	public static Array<Building> getBuildings(){
        Array<Building> result = buildings;
        buildings = new Array<Building>();
        return result;
    }
}
