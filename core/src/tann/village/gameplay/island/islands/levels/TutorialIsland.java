package tann.village.gameplay.island.islands.levels;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.BuildingGenerator;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.villager.Villager;
import tann.village.util.Sounds;

public class TutorialIsland extends Island {

	public TutorialIsland(TextureRegion tr, int x, int y) {
		super(tr, x, y);
	}
    Event ev;
	@Override
	protected void setupRandomPool() {

        addEvents(EventCreator.makeBasicEvents());

        ev = new Event("Heatwave", "The sweltering heat is draining the village");
        ev.eff(new Eff().morale(-1));
        ev.fate(-6,-2,1);
        addEvent(ev);

        ev = new Event("Clear skies", "Everyone wakes up with a clear head.");
        ev.fate(0,3,0);
        ev.eff(new Eff(Eff.EffectType.Reroll, +1));
        addEvent(ev);
	}

	@Override
    protected void setupStory() {
        ev = new Event("Land ho!", "You've found land again, it looks like a perfect place to start a new village!");
        ev.storyTurn(0);
        ev.eff(new Eff().food(3));
        ev.eff(new Eff().wood(3));
        addEvent(ev);

        ev = new Event("Build a village", "In order to survive you're going to need to make this a home.");
        ev.eff(new Eff(Eff.EffectType.BuildTown, 7));
        ev.storyTurn(3);
        addEvent(ev);

        ev = new Event("Hunger", "The village grows hungry. Upkeep increased by one.");
        ev.eff(new Eff().upkeep().food(-1));
        ev.storyTurn(8);
        addEvent(ev);

        ev = new Event("Cold", "The nights are getting colder, you need fuel to keep warm.");
        ev.effR(new Eff().upkeep().wood(-1));
        ev.storyTurn(16);
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
    protected void setupClasses() {
        this.availablesVillagerTypes.addAll(Villager.basicVillagerTypes);
    }

    @Override
    protected void setupBuildings() {
        BuildingGenerator.makeBasicBuildings();
        this.availableBuildings = BuildingGenerator.getBuildings();
    }

    @Override
    public String getVictoryText() {
        return "You win!!";
    }

}
