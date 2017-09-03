package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Buff;

public class EventCreator {
	public enum EventType{
		Tutorial,
	}
	
	static Event ev;
	public static Array<Event> makeBasicEvents() {
        Array<Event> current = new Array<>();

        ev = new Event("High Tide", "The tide is coming in fast, there's not much time!");
        ev.effR(new Eff().food(-2));
        ev.effR(new Eff().storage(-2));
        ev.addOutcome("Get everyone to safety");
        ev.addOutcome("Quickly build some flood defences", 0,3,0);
        ev.joel(-.5);
        ev.chance(1,1);
        current.add(ev);

        ev = new Event("Nightmares", "A deep fear invades the sleep of the vilage");
        ev.eff(new Eff().morale(-1));
        ev.addOutcome("You know it was just a dream but it still haunts you");
        ev.eff(new Eff().morale(1));
        ev.addOutcome("You fly away on a shining bird", 2);
        ev.joel(-.4);
        ev.chance(1,1);
        current.add(ev);

        ev = new Event("Stellar Alignment", "The sacred stars are in alignment");
        ev.eff(new Eff().fate(1));
        ev.addOutcome("They say it means the gull has found a new egg");
        ev.eff(new Eff().wood(30));
        ev.addOutcome("??????", 5);
        ev.joel(.3);
        ev.chance(.08, 1);
        current.add(ev);

        ev = new Event("Supply Crate", "You don't understand where it could have come from");
        ev.eff(new Eff().wood(3));
        ev.addOutcome("It contains a strange tool, may be useful");
        ev.eff(new Eff().storage(2));
        ev.eff(new Eff().food(2));
        ev.addOutcome("A fish is stuck inside. Plus you can store things in it");
        ev.joel(1.1);
        ev.chance(.5,1);
        ev.eff(new Eff().morale(1));
        current.add(ev);

//        ev = new Event("Spawning", "Hundreds of small fish have come near the shore");
//        ev.eff(new Eff().food(2));
//        ev.addOutcome("A lot of them just wash up on the beach!");
//        ev.eff(new Eff(new Buff().bonusFood(1)));
//        ev.addOutcome("You rush out to gather them!");
//        ev.joel(-.5f);
//        ev.chance(100000);
//        current.add(ev);

        ev = new Event("Buried Chest","An incredible find! Has some useful tools in it"); 
        ev.effR(new Eff().wood(2));
        ev.effR(new Eff().morale(1));
        ev.joel(-.8f);
        current.add(ev);

        ev = new Event("Floating Mast", "A huge bit of boat is spotted floating, someone swims out to gather it!");
        ev.effR(new Eff().wood(2));
        ev.joel(-.5f);
        current.add(ev);

        ev = new Event("Driftwood", "Looks like it was part of a boat. It's damp but usable.");
        ev.effR(new Eff().wood(1));
        ev.joel(-.2f);
        current.add(ev);


        //neutral//
        ev = new Event("Quiet day", "Thankfully uneventful");
        ev.joel(0);
        ev.chance(.2f);
        current.add(ev);

        //not ok//

        ev = new Event("Tiny Thieves", "A few monkeys run off with handfuls of food!");
        ev.effR(new Eff().food(-2));
        ev.joel(.4f);
        current.add(ev);

        ev = new Event("Rot", "Rot starts to set in");
        ev.effR(new Eff().wood(-1));
        ev.joel(.2f);
        current.add(ev);

        ev = new Event("Despair", "Dark dreams haunt the village");
        ev.effR(new Eff().morale(-2));
        ev.joel(.4f);
        current.add(ev);

        ev = new Event("Gorilla", "An alpha male gorilla approaches the village");
        ev.effR(new Eff().food(-4));
        ev.addOutcome("Back away and watch the enormous ape steal your food");
        ev.eff(new Eff().food(2));
        ev.eff(new Eff().morale(1));
        ev.addOutcome("He approaches and leaves a small bundle wrapped in leaves", 3);
        ev.joel(-.8f);
        current.add(ev);

        ev = new Event("Noises in the night", "Some of your food is gone, along with some storage!");
        ev.effR(new Eff().food(-1));
        ev.effR(new Eff().storage(-1));
        ev.joel(.3f);
        current.add(ev);

        ev = new Event("Flood", "The weather has taken a turn and washed away your supplies.");
        ev.effR(new Eff().food(-3));
        ev.effR(new Eff().wood(-3));
        ev.joel(1.2f);
        current.add(ev);

        return current;
	}
}
