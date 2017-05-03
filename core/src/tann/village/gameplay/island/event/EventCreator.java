package tann.village.gameplay.island.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;

public class EventCreator {
	public enum EventType{
		Tutorial, Animals
	}
	
	private static Map<EventType, List<Event>> eventMap = new HashMap<>();
	private static List<Event> events;
	public static List<Event> getEvents(EventType type){
		if(eventMap.get(type)!=null) return eventMap.get(type);
		events = new ArrayList<>();
		
		switch(type){
			case Tutorial: makeTutorialEvents(); break;
			case Animals: makeAnimalEvents(); break;
		}
		
		eventMap.put(type, events);
		return events;
	}
	
	private static String title, description; // title  description
	private static int l, r; // l, r
	private static float chance;
	private static Effect e, e1, e2, e3, e4; // effects
	private static EffectSource ev = EffectSource.Event;
	
	private static void makeTutorialEvents() {
        //great//
        title="Supply Crate";
        description="A crate from the ship washes ashore!";
        e=new Effect(EffectType.Food, 2, ev);
        e1=new Effect(EffectType.Wood, 2, ev);
        e2=new Effect(EffectType.Morale, 1, ev);
        e3=new Effect(EffectType.Fate, -2, ev);
        l =3; r =12;
        chance=1;
        make();

        title="Spawning";
        description="Hundreds of small fish have come near the shore, it's easy to grab a bunch of them!";
        e=new Effect(EffectType.Food, 3, ev);
        e1=new Effect(EffectType.Fate, -1, ev);
        l =2; r =12;
        chance=1;
        make();

        title="Buried Chest";
        description="An incredible find! Has some useful tools in it";
        e=new Effect(EffectType.Wood, +2, ev);
        e1=new Effect(EffectType.Morale, +1, ev);
        e2=new Effect(EffectType.Fate, -2, ev);
        l =2; r =12;
        chance=1;
        make();

        title="Floating Mast";
        description="A huge bit of boat is spotted floating, someone swims out to gather it!";
        e=new Effect(EffectType.Wood, +2, ev);
        e2=new Effect(EffectType.Fate, -1, ev);
        l =-2; r =5;
        chance=1;
        make();

        title="Driftwood";
        description="Looks like it was part of a boat. It's damp but usable.";
        e=new Effect(EffectType.Wood, 1, ev);
        l =1; r =6;
        chance=1;
        make();

        //neutral//
        title="Quiet day";
        description="Nothing special";
        l =-12; r =12;
        chance=0.2f;
        make();

        title="Clear skies";
        description="Everyone wakes up with a clear head.";
        l =0; r =3;
        chance=1f;
        e=new Effect(EffectType.Reroll, +1, ev);
        make();

        //not ok//
        title="Cursed Orange";
        description="Lightning strikes the ground as someone picked an orange from a tree";
        l =-1; r =3;
        chance=1;
        e=new Effect(EffectType.Food, 1, ev);
        e1=new Effect(EffectType.Fate, -1, ev);
        make();

        title="Tiny Thieves";
        description="A few monkeys run off with handfuls of food!";
        e=new Effect(EffectType.Food, -2, ev);
        e1=new Effect(EffectType.Fate, +1, ev);
        l =-7; r =0;
        chance=1;
        make();

        title="Rot";
        description="Rot starts to set in";
        e=new Effect(EffectType.Wood, -1, ev);
        l =-7; r =-1;
        chance=1;
        make();

        //bad//
        title="Heatwave";
        description="The heat is getting you down, your people need shelter!";
        e=new Effect(EffectType.Morale, -1, ev);
        e1=new Effect(EffectType.Fate, +1, ev);
        l =-6; r =-2;
        chance=1;
        make();

        title="Despair";
        description="Dark dreams haunt the village";
        e=new Effect(EffectType.Morale, -2, ev);
        e1=new Effect(EffectType.Fate, +2, ev);
        l =-12; r =-3;
        chance=1;
        make();

        title="Gorilla";
        description="Ook Ook OOK!!";
        e=new Effect(EffectType.Food, -4, ev);
        e1=new Effect(EffectType.Fate, +2, ev);
        l =-12; r =-1;
        chance=1;
        make();

        title="Noises in the night";
        description="Some of your food is gone, along with some storage!";
        e=new Effect(EffectType.Food, -1, ev);
        e1=new Effect(EffectType.FoodStorage, -1, ev);
        e2=new Effect(EffectType.Fate, +1, ev);
        l =-8; r =0;
        chance=1;
        make();

        title="Flood";
        description="The weather has taken a turn and washed away your supplies.";
        e=new Effect(EffectType.Food, -3, ev);
        e1=new Effect(EffectType.Wood, -3, ev);
        e2=new Effect(EffectType.Fate, +2, ev);
        l =-12; r =-1;
        chance=1;
        make();
	}

	
	private static void makeAnimalEvents() {
		
	}

	
	private static void make(){
		if(chance==-1 || r<=l){
			printBadEvent();
			return;
		}
		Array<Effect> fx = new Array<>();
		if(e!=null) fx.add(e);
		if(e1!=null) fx.add(e1);
		if(e2!=null) fx.add(e2);
		if(e3!=null) fx.add(e3);
		if(e4!=null) fx.add(e4);
		Event result = new Event(title,description,fx,chance, l, r);
		e=null;
		e1=null;
		e2=null;
		e3=null;
		e4=null;
		chance=-1;
		events.add(result);
	}
	
	private static void printBadEvent() {
		System.out.println("error generating event "+title);
	}
	
}
