package tann.village.gameplay.island.islands.levels;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.island.BuildingGenerator;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.building.Building;
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
        ev.fate(0, 12, -2);
        addEvent(ev);

        ev = new Event("Starstorm", "A barrage from the skies assaults the village. In the carnage you find a lot of red shards and one whole gem.");
        ev.eff(new Eff().gem(1));
        ev.eff(new Eff().storage(-3));
        ev.eff(new Eff().food(-3));
        ev.eff(new Eff().wood(-2));
        ev.eff(new Eff().morale(-2));
        ev.fate(-12, -1, 2);
        addEvent(ev);

        ev = new Event("Lights in the sky", "A dance of red and green in the sky awes the village");
        ev.eff(new Eff().morale(-1));
        ev.addOutcome("Cower inside");
        ev.eff(new Eff().food(-2));
        ev.eff(new Eff().wood(-2));
        ev.eff(new Eff().fate(2));
        ev.addOutcome("Make an offering");
        ev.fate(-2, 2, 0);
        ev.chance(.4f, 1);
        addEvent(ev);

        ev = new Event("Cursed Orange", "Lightning strikes the ground as someone picked an orange from a tree");
        ev.effR(new Eff().food(1));
        ev.fate(-2, 1, -1);
        addEvent(ev);

        ev = new Event("Astral Visitor","A shining deer approaches the village, it seems unafraid. It doesn't linger long and once it's gone you notice it left you something");
        ev.eff(new Eff(Eff.EffectType.Gem, 2));
        ev.fate(3,12,-4);
        ev.chance(2,1);
        addEvent(ev);

        ev = new Event("Fury","A ferocious bull charges through the village and eats your food");
        ev.effR(new Eff().food(-2));
        ev.eff(new Eff().storage(-2));
        ev.eff(new Eff().morale(-2));
        ev.fate(-12,-1, 3);
        addEvent(ev);

        addEvents(EventCreator.makeBasicEvents());
    }

    @Override
    protected void setupStory() {
        Event ev;
        ev = new Event("Red Island", "The island faintly glows red, you are worried that it may erupt");
        ev.storyTurn(0);
        ev.eff(new Eff().food(2));
        ev.eff(new Eff().wood(2));
        addEvent(ev);

        ev = new Event("Crimson Dreams", "The eldest villager wakes up from a dream. They tell of a great catastrophe unless 13 crimson gems are offered to the gods here.");
        ev.storyTurn(3);
        ev.eff(new Eff(Eff.EffectType.CollectGems, 13));
        ev.eff(new Eff().gem(1));
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
    protected void setupClasses() {
        availablesVillagerTypes.addAll(Villager.basicVillagerTypes);
        availablesVillagerTypes.addAll(Villager.VillagerType.ShineEye, Villager.VillagerType.Digger, Villager.VillagerType.ShineEye, Villager.VillagerType.Digger);
    }

    @Override
    protected void setupBuildings() {
        this.availableBuildings.addAll(BuildingGenerator.makeBasicBuildings());

        Building b;

        b = new Building("Mining","");
        b.setCost(5,3);
        b.addEffect(new Eff().gem(3));
        b.addEffect(new Eff().morale(-1));
        availableBuildings.add(b);

        b = new Building("Fountain","");
        b.setCost(15,5);
        b.addEffect(new Eff().gem(5));
        availableBuildings.add(b);

        b = new Building("Expedition");
        b.setCost(10,2);
        b.addEffect(new Eff().gem(2));
        b.addEffect(new Eff().morale(1));
        availableBuildings.add(b);

        b = new Building("Ocean sifting");
        b.setCost(4,1);
        b.addEffect(new Eff().gem(1));
        availableBuildings.add(b);
    }

    @Override
    public String getVictoryText() {
        return "you win!";
    }
}
