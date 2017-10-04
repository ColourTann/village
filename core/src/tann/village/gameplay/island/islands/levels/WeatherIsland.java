package tann.village.gameplay.island.islands.levels;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.ProjectGenerator;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.objective.SurviveObjective;
import tann.village.gameplay.village.Buff;
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
        ev.eff(new Eff(new Buff().rerolls(2)));
        ev.addOutcome("Plenty of time to work");
        ev.eff(new Eff().villagerXP(4));
        ev.addOutcome("A moment to reflect leads to a new idea!", 1);
        ev.chance(5000);
        ev.joel(-.6f);
        ev.eff(new Eff().morale(2));
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
        ev.effR(new Eff().food(-2));
        ev.effR(new Eff().storage(-2));
        ev.effR(new Eff().wood(-2));
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
        ev = new Event("A stormy beach", "This island is known to be stormy, you'd better prepare for a rough time.");
        ev.storyTurn(0);
        ev.eff(new Eff(new SurviveObjective(20)));
        addEvent(ev);

        ev = new Event("Dark skies", "The weather takes a turn, it's going to be tough");
        ev.storyTurn(5);
        ev.effR(new Eff().upkeep().food(-1));
        addEvent(ev);

        ev = new Event("Storm", "The storm has hit you, it will be tough to survive this.");
        ev.storyTurn(9);
        ev.eff(new Eff().upkeep().food(-2));
        addEvent(ev);

        ev = new Event("Thunderstorm", "And then the rain started");
        ev.storyTurn(13);
        ev.eff(new Eff().upkeep().food(-2));
        addEvent(ev);

        ev = new Event("Soaked through", "The dirt has turned to mud");
        ev.storyTurn(16);
        ev.eff(new Eff().upkeep().food(-1));
        addEvent(ev);
    }

    @Override
    protected void setupBuildings() {
        this.availableProjects.addAll(ProjectGenerator.makeBasicProjects());
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
