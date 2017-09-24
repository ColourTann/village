package tann.village.gameplay.island;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.project.Project;

public class ProjectGenerator {

	private static Array<Project> projects = new Array<>();
    static Project p;
	public static Array<Project> makeBasicProjects(){
        Array<Project> results = new Array<>();
        p = new Project("Dock", "A short pier leading into the ocean. +1 food per turn for 10 turns.");
        p.setCost(7);
        p.addEffect(new Eff().eachTurn(10).food(1));
        results.add(p);

        p = new Project("Salvage Hut", "A place to sort through useful materials. +1 wood per turn for 10 turns");
        p.setCost(6);
        p.addEffect(new Eff().eachTurn(10).wood(1));
        results.add(p);

        p = new Project("Fishing Trip", "A small expedition to catch fish. +4 food next turn");
        p.setCost(4);
        p.addEffect(new Eff().inTurns(1).food(4));
        results.add(p);

        p = new Project("Bonfire", "A big bonfire can really bring the community together");
        p.setCost(4);
        p.addEffect(new Eff().morale(2));
        results.add(p);

        p = new Project("Offering", "If the gods exist, it's a good idea to get on their good side");
        p.setCost(4,2);
        p.addEffect(new Eff().morale(2));
        p.addEffect(new Eff().fate(1));
        results.add(p);

        p = new Project("Crate", "A little extra storage for food can help out when times are hard");
        p.setCost(3);
        p.addEffect(new Eff().storage(2));
        results.add(p);

        p = new Project("Palm Grove", "Fast-growin trees! +1 wood and food per turn for 10 turns");
        p.setCost(8, 4);
        p.addEffect(new Eff().eachTurn(10).wood(1));
        p.addEffect(new Eff().eachTurn(10).food(1));
        results.add(p);

        p = new Project("Larder", "Large storage area for food");
        p.setCost(4);
        p.addEffect(new Eff().storage(6));
        results.add(p);

        p = new Project("Shrine", "An offering to the gods");
        p.setCost(10);
        p.addEffect(new Eff().fate(4));
        p.addEffect(new Eff().morale(1));
        results.add(p);


        return results;
    }
}
