package tann.village.gameplay.island.islands.levels;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.BuildingGenerator;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.villager.Villager;
import tann.village.util.Sounds;

public class WeatherIsland extends Island {
    public WeatherIsland(TextureRegion tr, int x, int y) {
        super(tr, x, y);
    }

    @Override
    protected void setupRandomPool() {
        addEvents(EventCreator.makeBasicEvents());

        Event ev;
        ev = new Event("Hailstorm", "Huge hailstones batter your huts");
        ev.eff(new Eff().morale(-1));
        ev.addOutcome("Cold night");
        ev.eff(new Eff().wood(-3));
        ev.addOutcome("Quickly, repair the roof!");
        ev.fate(-5,-1,1);
        addEvent(ev);

        ev = new Event("Momentary Repose", "A break in the clouds lifts your spirits");
        ev.eff(new Eff().morale(2));
        ev.eff(new Eff(Eff.EffectType.Reroll, 2));
        ev.fate(1,12, -2);
        addEvent(ev);

        ev = new Event("Whale Carcass",  "The storm in the night washed up a grisly prize");
        ev.effR(new Eff().food(5));
        ev.eff(new Eff().morale(1));
        ev.fate(1, 12, -3);
        addEvent(ev);

        ev = new Event("Relentless Rain", "It never stops");
        ev.effR(new Eff().food(-1));
        ev.fate(0,-1, 0);
        addEvent(ev);

        ev = new Event("Lightning", "Lightning strikes, setting fire to your storage hut");
        ev.eff(new Eff().food(-2));
        ev.eff(new Eff().storage(-2));
        ev.eff(new Eff().wood(-2));
        ev.fate(2,04,2);
        addEvent(ev);

        ev = new Event("Fallen tree", "In the night, a tree falls near one of the huts. You're lucky nobody was injured!");
        ev.effR(new Eff().wood(2));
        ev.fate(-1, 3, -1);
        addEvent(ev);
    }

    @Override
    protected void setupStory() {
        Event ev;
        ev = new Event("A stormy beach", "This island is known to be stormy, good job you brought a lot of supplies.");
        ev.storyTurn(0);
        ev.eff(new Eff().storage(2));
        ev.eff(new Eff().food(7));
        ev.eff(new Eff().wood(5));
        addEvent(ev);

        ev = new Event("Survive", "There's a storm on the way, you think that if you can weather the storm you will claim the island");
        ev.storyTurn(2);
        ev.eff(new Eff(Eff.EffectType.Survive, 25));
        addEvent(ev);

        ev = new Event("Dark skies", "The weather takes a turn, you must be ready soon!");
        ev.storyTurn(8);
        ev.effR(new Eff().upkeep().food(-1));
        addEvent(ev);

        ev = new Event("Storm", "The storm has hit you, it will be tough to survive this.");
        ev.storyTurn(10);
        ev.eff(new Eff().upkeep().food(-2));
        ev.eff(new Eff().upkeep().wood(-1));
        addEvent(ev);

        ev = new Event("Thunderstorm", "And then the rain started");
        ev.storyTurn(14);
        ev.eff(new Eff().upkeep().wood(-2));
        addEvent(ev);

        ev = new Event("Gap", "A gap in the clouds");
        ev.storyTurn(18);
        ev.eff(new Eff().upkeep().wood(1));
        addEvent(ev);

        ev = new Event("Light", "The storm is clearing, finally");
        ev.storyTurn(22);
        ev.eff(new Eff().upkeep().food(1));
        ev.eff(new Eff().upkeep().wood(1));
        addEvent(ev);
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
    protected String getBackgroundString() {
        return "gamescreen2";
    }

    @Override
    public String getAmbienceString() {
        return Sounds.storm;
    }

    @Override
    public String getIslandName() {
        return "StormRock";
    }

    @Override
    public String getVictoryText() {
        return "You win!";
    }
}
