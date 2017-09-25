package tann.village.gameplay.island.islands.levels;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.ProjectGenerator;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.island.objective.SurviveObjective;
import tann.village.gameplay.village.Buff;
import tann.village.gameplay.village.villager.Villager;
import tann.village.util.Sounds;

public class WeatherIsland extends Island {
    public WeatherIsland(TextureRegion tr, int x, int y) {
        super(tr, x, y);
    }

    @Override
    protected void setupRandomPool() {
        Event ev;
        ev = new Event("Hailstorm", "Huge hailstones batter your huts");
        ev.eff(new Eff().morale(-2));
        ev.addOutcome("Cold night");
        ev.eff(new Eff().wood(-3));
        ev.addOutcome("Quickly, repair the roof!");
        ev.joel(.7f);
        addEvent(ev);

        ev = new Event("Momentary Repose", "A break in the clouds lifts your spirits");
        ev.eff(new Eff().morale(2));
        ev.eff(new Eff(new Buff().rerolls(2)));
        ev.joel(-.6f);
        addEvent(ev);

        ev = new Event("Whale Carcass",  "The storm in the night washed up a grisly prize");
        ev.effR(new Eff().food(5));
        ev.eff(new Eff().morale(1));
        ev.joel(-1.2f);
        addEvent(ev);

        ev = new Event("Relentless Rain", "It never stops");
        ev.effR(new Eff().food(-1));
        ev.joel(.5f);
        addEvent(ev);

        ev = new Event("Lightning", "Lightning strikes, setting fire to your storage hut");
        ev.eff(new Eff().food(-2));
        ev.eff(new Eff().storage(-2));
        ev.eff(new Eff().wood(-2));
        ev.joel(1);
        addEvent(ev);

        ev = new Event("Fallen tree", "In the night, a tree falls near one of the huts. You're lucky nobody was injured!");
        ev.effR(new Eff().wood(2));
        ev.joel(.4f);
        addEvent(ev);
    }

    @Override
    protected void setupStory() {
        Event ev;
        ev = new Event("A stormy beach", "This island is known to be stormy, good job you brought a lot of supplies.");
        ev.storyTurn(0);
        ev.eff(new Eff().food(3));
        ev.eff(new Eff().wood(3));
        ev.eff(new Eff(new SurviveObjective(25)));
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
    protected void setupBuildings() {
        this.availableBuildings.addAll(ProjectGenerator.makeBasicProjects());
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
