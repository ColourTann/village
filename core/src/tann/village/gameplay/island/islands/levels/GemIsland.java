package tann.village.gameplay.island.islands.levels;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.gameplay.island.BuildingGenerator;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.villager.Villager;

public class GemIsland extends Island{
    public GemIsland(TextureRegion tr, int x, int y) {
        super(tr, x, y);
    }

    @Override
    protected void setupRandomPool() {
        EventCreator.makeBasicEvents();
        EventCreator.makeGemEvents();
        addEvents(EventCreator.getEvents(), false);
    }

    @Override
    protected void setupStory() {
        EventCreator.makeGemStory();
        addEvents(EventCreator.getEvents(), true);
    }

    @Override
    protected void setupClasses() {
//        availablesVillagerTypes.addAll(Villager.basicVillagerTypes);
        availablesVillagerTypes.addAll(Villager.VillagerType.ShineEye, Villager.VillagerType.Digger, Villager.VillagerType.Digger, Villager.VillagerType.Digger, Villager.VillagerType.Digger);
    }

    @Override
    protected void setupBuildings() {
        BuildingGenerator.makeBasicBuildings();
        BuildingGenerator.makeGemBuildings();
        this.availableBuildings = BuildingGenerator.getBuildings();
    }

    @Override
    public String getVictoryText() {
        return "you win!";
    }
}
