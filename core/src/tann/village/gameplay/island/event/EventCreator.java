package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;

import java.util.ArrayList;
import java.util.List;

public class EventCreator {
	public enum EventType{
		Tutorial,
	}
	
	static Event ev;
	public static Array<Event> makeBasicEvents() {
        Array<Event> current = new Array<>();

        //great//
        ev = new Event("Supply Crate", "A crate from the ship washes ashore!");
        ev.effR(new Eff().food(2));
        ev.effR(new Eff().wood(2));
        ev.eff(new Eff().morale(1));
        ev.fate(3,12,-3);
        current.add(ev);

        ev = new Event("Spawning", "Hundreds of small fish have come near the shore, it's easy to grab a bunch of them!");
        ev.effR(new Eff().food(3));
        ev.fate(2,12,-1);
        current.add(ev);

        ev = new Event("Buried Chest","An incredible find! Has some useful tools in it"); 
        ev.effR(new Eff().wood(2));
        ev.effR(new Eff().morale(1));
        ev.fate(2,12,-2);
        current.add(ev);

        ev = new Event("Floating Mast", "A huge bit of boat is spotted floating, someone swims out to gather it!");
        ev.effR(new Eff().wood(2));
        ev.fate(-2,5,-1);
        current.add(ev);

        ev = new Event("Driftwood", "Looks like it was part of a boat. It's damp but usable.");
        ev.effR(new Eff().wood(1));
        ev.fate(1,6,0);
        current.add(ev);

        //neutral//
        ev = new Event("Quiet day", "Thankfully uneventful");
        ev.fate(-12,12,1);
        ev.chance(.2f);
        current.add(ev);

        //not ok//

        ev = new Event("Tiny Thieves", "A few monkeys run off with handfuls of food!");
        ev.effR(new Eff().food(-2));
        ev.fate(-7,0,1);
        current.add(ev);

        ev = new Event("Rot", "Rot starts to set in");
        ev.effR(new Eff().wood(-1));
        ev.fate(-7,-1,0);
        current.add(ev);

        ev = new Event("Despair", "Dark dreams haunt the village");
        ev.effR(new Eff().morale(-2));
        ev.fate(-12,-3,2);
        current.add(ev);

        ev = new Event("Gorilla", "Ook Ook OOK!!");
        ev.effR(new Eff().food(-4));
        ev.fate(-12,-1,2);
        current.add(ev);

        ev = new Event("Noises in the night", "Some of your food is gone, along with some storage!");
        ev.effR(new Eff().food(-1));
        ev.effR(new Eff().storage(-1));
        ev.fate(-8,0,1);
        current.add(ev);

        ev = new Event("Flood", "The weather has taken a turn and washed away your supplies.");
        ev.effR(new Eff().food(-3));
        ev.effR(new Eff().wood(-3));
        ev.fate(-12,-1,2);
        current.add(ev);

        return current;
	}
}
