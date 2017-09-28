package tann.village.gameplay.island;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.project.Project;

public class ProjectGenerator {

	private static Array<Project> projects = new Array<>();
    static Project p;
	public static Array<Project> makeBasicProjects(){

        // ------------- boatey stuff! ------------------- //
        Array<Project> results = new Array<>();
        p = new Project("Dock", "A short pier leading into the ocean. +1 food per turn for 10 turns.");
        p.setCost(7);
        p.addEffect(new Eff().eachTurn(10).food(1));
        results.add(p);

        // dock
        // high chance
        p = new Project("Boat", "Build a boat at your dock");
        p.setCost(5);
        p.addEffect(new Eff().inTurns(2).morale(2));
        results.add(p);


        // unlocked by Boat
        p = new Project("Whaling", "Deck out a boat and go hunting");
        p.setCost(9);
        p.addEffect(new Eff().inTurns(3).food(15));
        results.add(p);

        p = new Project("Figurehead", "A beautiful decoration for a prow");
        p.setCost(5);
        p.addEffect(new Eff().inTurns(2).morale(2));
        p.addEffect(new Eff().inTurns(2).fate(1));
        results.add(p);

        p = new Project("Salvaging", "Sail out in search of anything useful");
        p.setCost(5);
        p.addEffect(new Eff().inTurns(2).wood(9));
        results.add(p);

        // -------------- buildings?? ---------------- //
        // buildings
        p = new Project("Tool Hut", "A place to sort through useful materials. +1 wood per turn for 10 turns");
        p.setCost(6);
        p.addEffect(new Eff().eachTurn(10).wood(1));
        results.add(p);

        p = new Project("Larder", "Large storage area for food");
        p.setCost(4);
        p.addEffect(new Eff().storage(7));
        results.add(p);

        p = new Project("Shrine", "An offering to the gods");
        p.setCost(5);
        p.addEffect(new Eff().inTurns(2).fate(3));
        results.add(p);

        p = new Project("Oven", "Cooking food better increases nutrition");
        p.setCost(12);
        p.addEffect(new Eff().eachTurn(50).food(1));
        results.add(p);

        p = new Project("Clay Hut", "Better sleeping quarters");
        p.setCost(8);
        p.addEffect(new Eff().inTurns(2).morale(5));
        results.add(p);

//        p = new Project("A baby!", "Why does this cost so much wood?");
//        p.setCost(12);
//        p.addEffect(new Eff().newVillager());
//        results.add(p);

        // -------------- Expeditions?? ------------ //
        p = new Project("Foraging Trip", "A small expedition to find some extra food. +4 food next turn");
        p.setCost(4);
        p.addEffect(new Eff().inTurns(1).food(4));
        results.add(p);

        p = new Project("Cave Exploration", "You think the nearby cave has metal inside!");
        p.setCost(6);
        p.addEffect(new Eff().inTurns(3).wood(10));
        results.add(p);



        // Supersitions
        p = new Project("Offering", "If the gods exist, it's a good idea to get on their good side");
        p.setCost(4,2);
        p.addEffect(new Eff().morale(2));
        p.addEffect(new Eff().fate(1));
        results.add(p);

        // home improvements
        p = new Project("Crate", "A little extra storage for food can help out when times are hard");
        p.setCost(3);
        p.addEffect(new Eff().storage(3));
        results.add(p);

        p = new Project("Campfire", "A big bonfire can really bring the community together");
        p.setCost(4);
        p.addEffect(new Eff().morale(2));
        results.add(p);

        // Nature
        p = new Project("Palm Grove", "Fast-growin trees! +1 wood and food per turn for 10 turns");
        p.setCost(8, 4);
        p.addEffect(new Eff().eachTurn(10).wood(1));
        p.addEffect(new Eff().eachTurn(10).food(1));
        results.add(p);

        return results;
    }
}
