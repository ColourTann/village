package tann.village.gameplay.island.islands.levels;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.ProjectGenerator;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.objective.ProjectObjective;
import tann.village.gameplay.village.Buff;
import tann.village.gameplay.village.villager.Villager;
import tann.village.util.Sounds;

public class TutorialIsland extends Island {

	public TutorialIsland(TextureRegion tr, int x, int y) {
		super(tr, x, y);
	}
    Event ev;
	@Override
	protected void setupRandomPool() {
        ev = new Event("Heatwave", "The sweltering heat is draining the village");
        ev.eff(new Eff().morale(-1));
        ev.joel(-.4);
        addEvent(ev);
	}

	@Override
    protected void setupStory() {
        ev = new Event("Build a village", "In order to survive you're going to need to make this a home.");
        ev.storyTurn(1110);
        ev.eff(new Eff().food(2));
        ev.eff(new Eff().wood(2));
        ev.eff(new Eff(new ProjectObjective(5)));
        addEvent(ev);


        ev = new Event("Hunger", "The village grows hungry. Upkeep increased by one.");
        ev.eff(new Eff().upkeep().food(-1));
        ev.storyTurn(6);
        addEvent(ev);
	}

    @Override
    protected String getBackgroundString() {
        return "gamescreen";
    }

    @Override
    public String getAmbienceString() {
        return Sounds.beach;
    }

    @Override
    public String getIslandName() {
        return "Outset Island\n(you should start here probably!)";
    }

    @Override
    protected void setupBuildings() {
        this.availableBuildings.addAll(ProjectGenerator.makeBasicProjects());
    }

    @Override
    public String getVictoryText() {
        return "You win!!";
    }

}
