package tann.village.gameplay.island.islands.levels;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.ProjectGenerator;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.objective.GemsObjective;
import tann.village.gameplay.village.project.Project;
import tann.village.gameplay.village.villager.Villager;
import tann.village.util.Sounds;

public class GemIsland extends Island{
    public GemIsland(TextureRegion tr, int x, int y) {
        super(tr, x, y);
    }

    @Override
    protected void setupRandomPool() {
        Event ev;
        ev = new Event("Shooting star", "Incredible! A shooting star lands at the outskirts of your village. Inside you find a small red gem.");
        ev.eff(new Eff().gem(1));
        ev.joel(.5f);
        addEvent(ev);

        ev = new Event("Starstorm", "A barrage from the skies assaults the village. In the carnage you find a lot of red shards and one whole gem.");
        ev.eff(new Eff().gem(1));
        ev.effR(new Eff().storage(-3));
        ev.effR(new Eff().food(-3));
        ev.effR(new Eff().wood(-2));
        ev.eff(new Eff().morale(-2));
        ev.joel(-.7f);
        addEvent(ev);

        ev = new Event("Lights in the sky", "A dance of red and green in the sky awes the village");
        ev.eff(new Eff().morale(-1));
        ev.addOutcome("Cower inside");
        ev.eff(new Eff().food(-2));
        ev.eff(new Eff().wood(-2));
        ev.eff(new Eff().fate(2));
        ev.addOutcome("Make an offering");
        ev.joel(-.3);
        ev.chance(.4f, 1);
        addEvent(ev);

        ev = new Event("Cursed Orange", "Lightning strikes the ground as someone picked an orange from a tree");
        ev.eff(new Eff().food(1));
        ev.effR(new Eff().fate(-1));
        ev.joel(-.2);
        ev.chance(1,1);
        addEvent(ev);

        ev = new Event("Astral Visitor","A shining deer approaches the village, it seems unafraid. It doesn't linger long and once it's gone you notice it left you something");
        ev.eff(new Eff(Eff.EffectType.Gem, 2));
        ev.joel(1);
        ev.chance(1,1);
        addEvent(ev);

        ev = new Event("Fury","A ferocious bull charges through the village and eats your food");
        ev.effR(new Eff().food(-2));
        ev.effR(new Eff().storage(-2));
        ev.eff(new Eff().morale(-2));
        ev.joel(-1.4f);
        addEvent(ev);

        ev = new Event("Rainy night", "The rain beats down all night");
        ev.eff(new Eff().morale(-1));
        ev.addOutcome("You wake up damp");
        ev.eff(new Eff().gem(1));
        ev.addOutcome("The rain uncovers a gem!", 3);
        ev.joel(-.4);
        addEvent(ev);

        ev = new Event("High Tide", "The tide is coming in fast, there's not much time!");
        ev.effR(new Eff().food(-2));
        ev.addOutcome("Get everyone to safety");
        ev.eff(new Eff().morale(1));
        ev.addOutcome("Quickly build some flood defences", 0,3,0);
        ev.eff(new Eff().gem(2));
        ev.addOutcome("The tide draws back and reveals two gems!", 1);
        ev.effR(new Eff().storage(-2));
        ev.joel(-.5);
        addEvent(ev);
        //TODO tide parts to show a cool gem!
    }

    @Override
    protected void setupStory() {
        Event ev;
        ev = new Event("Crimson Dreams", "The island faintly glows red, you are worried that it may erupt");
        ev.eff(new Eff(new GemsObjective(13)));
        ev.storyTurn(0);
        addEvent(ev);

        ev = new Event("Blood Skies", "The sky has turned deep red, the heat makes it hard to work");
        ev.storyTurn(8);
        ev.eff(new Eff().upkeep().food(-1));
        addEvent(ev);

        ev = new Event("Burning ground", "The ground shifts beneath your feet");
        ev.eff(new Eff().upkeep().food(-1));
        ev.storyTurn(16);
        ev.eff(new Eff().upkeep().wood(-1));
        addEvent(ev);

        ev = new Event("Dire omen", "The sky turns black, the gods grow tired of your sloth.");
        ev.eff(new Eff().upkeep().food(-2));
        ev.storyTurn(24);
        ev.eff(new Eff().upkeep().wood(-2));
        addEvent(ev);
    }

    @Override
    protected String getBackgroundString() {
        return "gamescreen1";
    }

    @Override
    public String getAmbienceString() {
        return Sounds.gem;
    }

    @Override
    public String getIslandName() {
        return "Ruby Cove";
    }

    @Override
    protected void setupBuildings() {
        this.availableBuildings.addAll(ProjectGenerator.makeBasicProjects());

        Project b;

        b = new Project("Mining","");
        b.setCost(5,3);
        b.addEffect(new Eff().gem(3));
        b.addEffect(new Eff().morale(-1));
        availableBuildings.add(b);

        b = new Project("Fountain","");
        b.setCost(15,5);
        b.addEffect(new Eff().gem(5));
        availableBuildings.add(b);

        b = new Project("Expedition");
        b.setCost(10,2);
        b.addEffect(new Eff().gem(2));
        b.addEffect(new Eff().morale(1));
        availableBuildings.add(b);

        b = new Project("Ocean sifting");
        b.setCost(4,1);
        b.addEffect(new Eff().gem(1));
        availableBuildings.add(b);
    }

    @Override
    public String getVictoryText() {
        return "you win!";
    }
}
