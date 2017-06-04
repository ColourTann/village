package tann.village.gameplay.island.islands.levels;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.gameplay.island.BuildingGenerator;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.villager.Villager;

public class TutorialIsland extends Island {

	public TutorialIsland(TextureRegion tr, int x, int y) {
		super(tr, x, y);
	}

	@Override
	protected void setupRandomPool() {
        EventCreator.makeBasicEvents();
	    addEvents(EventCreator.getEvents(), false);
	}

	@Override
    protected void setupStory() {
        EventCreator.makeTutorialIslandStory();
        addEvents(EventCreator.getEvents(), true);
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
