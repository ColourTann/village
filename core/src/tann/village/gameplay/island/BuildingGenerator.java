package tann.village.gameplay.island;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.effect.EffAct;
import tann.village.gameplay.village.building.Building;
import tann.village.gameplay.village.building.BuildingEffect;
import tann.village.gameplay.village.building.BuildingEffect.BuildingEffectType;

public class BuildingGenerator {

	private static Array<Building> buildings = new Array<>();
	private static String title, description;
	private static BuildingEffect b1;
	private static BuildingEffect b2;
	private static int w,f,l;
	public static void makeBasicBuildings(){

        l=0; // *********************level 0********************* //
        title="Dock";
        description="A short pier leading into the ocean";
        w=8;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Food, 1, new EffAct(EffAct.ActivationType.FOR_TURNS, 10))});
        make();


        l=0; // *********************level 0********************* //
        title="Dock";
        description="A short pier leading into the ocean";
        w=0;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Food, 1, new EffAct(EffAct.ActivationType.FOR_TURNS, 10))});
        make();
        l=0; // *********************level 0********************* //
        title="Dock";
        description="A short pier leading into the ocean";
        w=0;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Food, 1, new EffAct(EffAct.ActivationType.FOR_TURNS, 10))});
        make();l=0; // *********************level 0********************* //
        title="Dock";
        description="A short pier leading into the ocean";
        w=0;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Food, 1, new EffAct(EffAct.ActivationType.FOR_TURNS, 10))});
        make();l=0; // *********************level 0********************* //
        title="Dock";
        description="A short pier leading into the ocean";
        w=0;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Food, 1, new EffAct(EffAct.ActivationType.FOR_TURNS, 10))});
        make();l=0; // *********************level 0********************* //
        title="Dock";
        description="A short pier leading into the ocean";
        w=0;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Food, 1, new EffAct(EffAct.ActivationType.FOR_TURNS, 10))});
        make();l=0; // *********************level 0********************* //
        title="Dock";
        description="A short pier leading into the ocean";
        w=0;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Food, 1, new EffAct(EffAct.ActivationType.FOR_TURNS, 10))});
        make();l=0; // *********************level 0********************* //
        title="Dock";
        description="A short pier leading into the ocean";
        w=0;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Food, 1, new EffAct(EffAct.ActivationType.FOR_TURNS, 10))});
        make();









        title="Bonfire";
        description="A big bonfire can really bring the community together";
        w=4;
        b1 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Morale, 2)});
        make();

        title="Offering";
        description="If the gods exist, it's a good idea to get on their good side";
        w=4;
        f=2;
        b1 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Morale, 1)});
        b2 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Fate, 2)});
        make();

        title="Crate";
        description="A little extra storage for food can help out when times are hard";
        w=3;
        b1 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.FoodStorage, 2)});
        make();

        title="Salvage Hut";
        description="A place to sort through useful materials";
        w=7;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Wood, 1)});
        make();

        // *********************level 1********************* //

        l=1;
        title="Palm Grove";
        description="A small grove for harvesting fast-growing trees";
        w=11; f=3;
        b1 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Food, 1)});
        b2 = new BuildingEffect(BuildingEffectType.EveryTurn, new Eff[]{new Eff(EffectType.Wood, 1)});
        make();

        title="Larder";
        description="Large storage area for food";
        w=5;
        b1 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.FoodStorage, 6)});
        make();

        title="Shrine";
        description="An offering to the gods";
        w=10;
        b1 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Fate, 4)});
        b2 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Morale, 1)});
        make();
    }

    public static void makeGemBuildings(){
        title = "Mining";
        w = 5;
        f = 3;
        b1 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Gem, 3)});
        b2 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Morale, -1)});
        make();

        title = "Fountain";
        w = 15;
        f = 5;
        b1 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Gem, 5)});
        make();

        title = "Expedition";
        w = 10;
        f = 2;
        b1 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Gem, 2)});
        b2 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Morale, 1)});
        make();

        title = "Ocean sifting";
        w = 4;
        f = 1;
        b1 = new BuildingEffect(BuildingEffectType.Now, new Eff[]{new Eff(EffectType.Gem, 1)});
        make();
    }

    private static void make(){
		if(title==null||b1==null){
			System.err.println("Something went wrong making "+title+":"+description);
			return;
		}
		Array<BuildingEffect> effects = new Array<>();
		if(b1!=null)effects.add(b1);
		if(b2!=null)effects.add(b2);
		buildings.add(new Building(title, description, l, new Cost(w, f), effects));
		w=0; f=0;
		title=null; description=null;
		b1=null;
		b2=null;
	}

	public static Array<Building> getBuildings(){
        Array<Building> result = buildings;
        buildings = new Array<Building>();
        return result;
    }
}
