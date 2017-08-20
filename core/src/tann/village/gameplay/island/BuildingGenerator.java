package tann.village.gameplay.island;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.village.building.Building;

public class BuildingGenerator {

	private static Array<Building> buildings = new Array<>();
    static Building b;
	public static Array<Building> makeBasicBuildings(){
        Array<Building> results = new Array<>();

        b = new Building("Dock", "A short pier leading into the ocean. +1 food per turn for 12 turns.");
        b.setCost(6);
        b.addEffect(new Eff().eachTurn(12).food(1));
        results.add(b);

        b = new Building("Fishing Trip", "A small expedition to catch fish. +4 food next turn");
        b.setCost(4);
        b.addEffect(new Eff().inTurns(1).food(4));
        results.add(b);

        b = new Building("Bonfire", "A big bonfire can really bring the community together");
        b.setCost(4);
        b.addEffect(new Eff().morale(2));
        results.add(b);

        b = new Building("Offering", "If the gods exist, it's a good idea to get on their good side");
        b.setCost(4,2);
        b.addEffect(new Eff().morale(2));
        b.addEffect(new Eff().fate(1));
        results.add(b);

        b = new Building("Crate", "A little extra storage for food can help out when times are hard");
        b.setCost(3);
        b.addEffect(new Eff().storage(2));
        results.add(b);

        b = new Building("Salvage Hut", "A place to sort through useful materials. +1 wood per turn for 10 turns");
        b.setCost(5);
        b.addEffect(new Eff().eachTurn(10).wood(1));
        results.add(b);

        b = new Building("Palm Grove", "A small grove for harvesting fast-growing trees, +1 wood and food per turn for 10 turns");
        b.setCost(6, 4);
        b.addEffect(new Eff().eachTurn(10).wood(1));
        b.addEffect(new Eff().eachTurn(10).food(1));
        results.add(b);

        b = new Building("Larder", "Large storage area for food");
        b.setCost(4);
        b.addEffect(new Eff().storage(6));
        results.add(b);

        b = new Building("Shrine", "An offering to the gods");
        b.setCost(10);
        b.addEffect(new Eff().fate(4));
        b.addEffect(new Eff().morale(1));
        results.add(b);


        return results;
    }
}
