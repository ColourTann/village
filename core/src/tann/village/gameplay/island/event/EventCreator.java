package tann.village.gameplay.island.event;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;

public class EventCreator {
	public enum EventType{
		Tutorial,
	}
	
	private static Map<EventType, Array<Event>> eventMap = new HashMap<>();
	private static Array<Event> events = new Array<>();
	public static Array<Event> getEvents(EventType type){
		if(eventMap.get(type)!=null) return eventMap.get(type);
		events = new Array<>();
		
		switch(type){
			case Tutorial: makeBasicEvents(); break;
		}
		
		eventMap.put(type, events);
		return events;
	}
	
	private static String title, description; // title  description
	private static int l, r; // l, r
    private static int fd; // fate delta
    private static int uses=999;
	private static float chance;
	private static Effect e, e1, e2, e3, e4; // effects
	private static EffectSource ev = EffectSource.Event;

	// story-only
    private static int turn;
	
	public static void makeBasicEvents() {
        //great//
        title="Supply Crate";
        description="A crate from the ship washes ashore!";
        e=new Effect(EffectType.Food, 2, ev);
        e1=new Effect(EffectType.Wood, 2, ev);
        e2=new Effect(EffectType.Morale, 1, ev);
        l =3; r =12; fd=-3;
        chance=1;
        make();

        title="Spawning";
        description="Hundreds of small fish have come near the shore, it's easy to grab a bunch of them!";
        e=new Effect(EffectType.Food, 3, ev);
        l =2; r =12; fd=-1;
        chance=1;
        make();

        title="Buried Chest";
        description="An incredible find! Has some useful tools in it";
        e=new Effect(EffectType.Wood, +2, ev);
        e1=new Effect(EffectType.Morale, +1, ev);
        l =2; r =12; fd=-2;
        chance=1;
        make();

        title="Floating Mast";
        description="A huge bit of boat is spotted floating, someone swims out to gather it!";
        e=new Effect(EffectType.Wood, +2, ev);
        l =-2; r =5; fd=-1;
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
        description="Thankfully uneventful";
        l =-12; r =12;
        chance=0.2f;
        make();

        title="Clear skies";
        description="Everyone wakes up with a clear head.";
        l =0; r =3;
        chance=1f;
        e=new Effect(EffectType.Reroll, +1, ev, 1);
        make();

        //not ok//
        title="Cursed Orange";
        description="Lightning strikes the ground as someone picked an orange from a tree";
        l =-1; r =3; fd=-1;
        chance=1;
        e=new Effect(EffectType.Food, 1, ev);
        make();

        title="Tiny Thieves";
        description="A few monkeys run off with handfuls of food!";
        e=new Effect(EffectType.Food, -2, ev);
        l =-7; r =0; fd =1;
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
        description="The sweltering heat is draining the village";
        e=new Effect(EffectType.Morale, -1, ev);
        l =-6; r =-2; fd =1;
        chance=1;
        make();

        title="Despair";
        description="Dark dreams haunt the village";
        e=new Effect(EffectType.Morale, -2, ev);
        l =-12; r =-3; fd=2;
        chance=1;
        make();

        title="Gorilla";
        description="Ook Ook OOK!!";
        e=new Effect(EffectType.Food, -4, ev);
        l =-12; r =-1; fd=2;
        chance=1;
        make();

        title="Noises in the night";
        description="Some of your food is gone, along with some storage!";
        e=new Effect(EffectType.Food, -1, ev);
        e1=new Effect(EffectType.FoodStorage, -1, ev);
        l =-8; r =0; fd=1;
        chance=1;
        make();

        title="Flood";
        description="The weather has taken a turn and washed away your supplies.";
        e=new Effect(EffectType.Food, -3, ev);
        e1=new Effect(EffectType.Wood, -3, ev);
        l =-12; r =-1; fd=2;
        chance=1;
        make();
	}

	public static void makeTutorialIslandStory(){
        turn=0;
        title="Land ho!";
        description="You've has found land again, it looks like a perfect place to start a new village!";
        e = new Effect(EffectType.Food, 3, EffectSource.Event);
        e1 = new Effect(EffectType.Wood, 3, EffectSource.Event);
        makeStory();

        turn=4;
        title="Hunger";
        description="The village grows hungry. Upkeep increased by two.";
        e = new Effect(EffectType.Food, -2, EffectSource.Upkeep);
        makeStory();

        turn=6;
        title="Build a village";
        description="In order to survive you're going to need to make this a home.";
        e = new Effect(EffectType.BuildTown, 7, ev);
        makeStory();

        turn=9;
        title="Cold";
        description="The nights are getting colder, you need fuel to keep warm.";
        e = new Effect(EffectType.Wood, -1, EffectSource.Upkeep);
        makeStory();
    }

    public static Array<Event> getEvents(){
	    Array<Event> eventsToReturn = events;
	    events = new Array<Event>();
        return eventsToReturn;
    }


    public static void makeStormStory() {
        turn=0;
        title="A stormy beach";
        description="This island is known to be stormy, good job you brought a lot of supplies.";
        e = new Effect(EffectType.FoodStorage, 2, EffectSource.Event);
        e2 = new Effect(EffectType.Food, 7, EffectSource.Event);
        e3 = new Effect(EffectType.Wood, 5, EffectSource.Event);
        makeStory();

        turn=2;
        title="Survive";
        description="There's a storm on the way, you think that if you can weather the storm you will claim the island";
        e = new Effect(EffectType.Survive, 25, EffectSource.Event);
        makeStory();

        turn=8;
        title="Dark skies";
        description="The weather takes a turn, you must be ready soon!";
        e = new Effect(EffectType.Food, -1, EffectSource.Upkeep);
        makeStory();

        turn=10;
        title="Storm";
        description="The storm has hit you, it will be tough to survive this.";
        e = new Effect(EffectType.Food, -2, EffectSource.Upkeep);
        e1 = new Effect(EffectType.Wood, -1, EffectSource.Upkeep);
//        e2 = new Effect(EffectType.FoodBonus, -1, EffectSource.Event, -1);
        makeStory();

        turn=14;
        title="Thunderstorm";
        description="And then the rain started";
        e1 = new Effect(EffectType.Wood, -2, EffectSource.Upkeep);
        makeStory();

        turn=18;
        title="Gap";
        description="A gap in the clouds";
        e1 = new Effect(EffectType.Wood, +1, EffectSource.Upkeep);
//        e2 = new Effect(EffectType.FoodBonus, +1, EffectSource.Event);

        turn=22;
        title="Light";
        description="The storm is clearing, finally";
        e = new Effect(EffectType.Food, +1, EffectSource.Upkeep);
        e1 = new Effect(EffectType.Wood, +1, EffectSource.Upkeep);
        makeStory();
    }

    public static void makeStormEvents(){

        description = "A cold night";
        e = new Effect(EffectType.Morale, -2, ev);
        addOutcome();
        description = "Quickly, repair the roof!";
        e = new Effect(EffectType.Wood, -3, ev);
        addOutcome();
        title="Hailstorm";
        description="Huge hailstones batter your huts";
        l =-5; r =-1; fd =1;
        chance=1;
        make();

        title = "Momentary Repose";
        description = "A break in the clouds lifts your spirits";
        e = new Effect(EffectType.Morale, 2, ev);
        e1 = new Effect(EffectType.Reroll, 2, ev, 1);
        l = 1; r=12; fd=-2;
        make();

        title = "Whale Carcass";
        description= "The storm in the night washed up a grisly prize";
        e = new Effect(EffectType.Food, 5, ev);
        e1 = new Effect(EffectType.Morale, 1, ev);
        fd = -3; l=3; r=12;
        make();

        title = "Relentless Rain";
        description = "It never stops";
        e = new Effect(EffectType.Food, -1, ev);
        fd=0; l=-2; r=0;
        make();

        title = "Lightning";
        description = "Lightning strikes, setting fire to your storage hut";
        e= new Effect(EffectType.Food, -2, ev);
        e1= new Effect(EffectType.FoodStorage, -2, ev);
        e2= new Effect(EffectType.Wood, -2, ev);
        fd = 2; l=-4; r=2;
        make();

        title = "Fallen tree";
        description = "In the night, a tree falls near one of the huts. You're lucky nobody was injured!";
        e = new Effect(EffectType.Wood, 2, ev);
        fd=-1; l=-1; r= 3;
        make();
    }

    public static void makeGemStory() {
        turn=0;
        title="Red Island";
        description="The island faintly glows red, you are worried that it may erupt";
        e2 = new Effect(EffectType.Food, 2, EffectSource.Event);
        e3 = new Effect(EffectType.Wood, 2, EffectSource.Event);
        makeStory();

        turn=3;
        title="Crimson Dreams";
        description="The eldest villager wakes up from a dream. They tell of a great catastrophe unless 7 crisom gems are offered to the gods here.";
        e = new Effect(EffectType.CollectGems, 7, EffectSource.Event);
        e1= new Effect(EffectType.TimeLimit, 20, EffectSource.Event);
        e2 = new Effect(EffectType.Gem, 1, ev);
        makeStory();

        turn=8;
        title="Blood Skies";
        description="The sky has turned deep red, the heat makes it hard to work. -1 permanent reroll";
        e = new Effect(EffectType.Food, -1, EffectSource.Upkeep);
        e = new Effect(EffectType.Reroll, -1, EffectSource.Upkeep, -1);
        makeStory();

        turn=15;
        title="Maroon Sickness";
        description="A sickness afflicts the village, you need to get the remaining gems fast";
        e = new Effect(EffectType.Food, -1, EffectSource.Upkeep);
        e1 = new Effect(EffectType.Wood, -1, EffectSource.Upkeep);
        e2 = new Effect(EffectType.FoodBonus, -1, EffectSource.Upkeep);
        makeStory();
    }

    public static void makeGemEvents(){
        title="Heatwave";
        description="The sweltering heat is draining the village";
        e=new Effect(EffectType.Morale, -1, ev);
        l =-6; r =-2; fd =1;
        chance=1;
        make();
    }

	private static Array<Outcome> outcomes = new Array<>();

	private static void addOutcome(){
        Array<Effect> fx = new Array<>();
        if(e!=null) fx.add(e);
        if(e1!=null) fx.add(e1);
        if(e2!=null) fx.add(e2);
        if(e3!=null) fx.add(e3);
        if(e4!=null) fx.add(e4);
	    Outcome o = new Outcome(description, fx);
	    outcomes.add(o);
        e=null;
        e1=null;
        e2=null;
        e3=null;
        e4=null;
        description = null;
    }

    private static void make(){
	    makeEvent(false);
    }

    private static void makeStory(){
        makeEvent(true);
    }

	private static void makeEvent(boolean story){
        if(r<l) {
            printBadEvent("r<1");
            return;
        }
        if(description == null || title == null ){
            printBadEvent("title/desc");
			return;
		}
        Array<Effect> reqs = new Array<>();
        Array<Effect> fx = new Array<>();
        if(e!=null) fx.add(e);
        if(e1!=null) fx.add(e1);
		if(e2!=null) fx.add(e2);
		if(e3!=null) fx.add(e3);
		if(e4!=null) fx.add(e4);
		Event result = new Event(title,description,fx, outcomes, reqs, chance, l, r, fd, story, turn, uses);
        e=null;
		e1=null;
		e2=null;
		e3=null;
		e4=null;
		uses=999;
		description = null;
		l=0; r=0; fd=0;
		chance=1;
		outcomes= new Array<>();
		events.add(result);
	}
	
	private static void printBadEvent(String arg) {
		System.out.println("error generating event "+title+" because "+arg);
	}
	
}
