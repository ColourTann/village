package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;

public class EventCreator {
	public enum EventType{
		Tutorial,
	}
	
	private static Array<Event> events = new Array<>();

	private static String title, description; // title  description
	private static int l, r; // l, r
    private static int fd; // fate delta
    private static int uses=999;
	private static float chance;
    private static Eff e, e1, e2, e3, e4; // effects
    private static Eff req, req1, req2, req3, req4; // effects

	// story-only
    private static int turn;
	
	public static void makeBasicEvents() {
        //great//
        title="Supply Crate";
        description="A crate from the ship washes ashore!";
        e=new Eff(EffectType.Food, 2);
        e1=new Eff(EffectType.Wood, 2);
        e2=new Eff(EffectType.Morale, 1);
        req = e;
        req1 = e1;
        l =3; r =12; fd=-3;
        chance=1;
        make();

        title="Spawning";
        description="Hundreds of small fish have come near the shore, it's easy to grab a bunch of them!";
        e=new Eff(EffectType.Food, 3);
        l =2; r =12; fd=-1;
        req = e;
        chance=1;
        make();

        title="Buried Chest";
        description="An incredible find! Has some useful tools in it";
        e=new Eff(EffectType.Wood, +2);
        e1=new Eff(EffectType.Morale, +1);
        l =2; r =12; fd=-2;
        req= e;
        chance=1;
        make();

        title="Floating Mast";
        description="A huge bit of boat is spotted floating, someone swims out to gather it!";
        e=new Eff(EffectType.Wood, +2);
        l =-2; r =5; fd=-1;
        chance=1;
        make();

        title="Driftwood";
        description="Looks like it was part of a boat. It's damp but usable.";
        e=new Eff(EffectType.Wood, 1);
        l =1; r =6;
        chance=1;
        make();

        //neutral//
        title="Quiet day";
        description="Thankfully uneventful";
        l =-12; r =12;
        chance=0.2f;
        make();

        //not ok//

        title="Tiny Thieves";
        description="A few monkeys run off with handfuls of food!";
        e=new Eff(EffectType.Food, -2);
        req = e;
        l =-7; r =0; fd =1;
        chance=1;
        make();

        title="Rot";
        description="Rot starts to set in";
        e=new Eff(EffectType.Wood, -1);
        req = e;
        l =-7; r =-1;
        chance=1;
        make();

        title="Despair";
        description="Dark dreams haunt the village";
        e=new Eff(EffectType.Morale, -2);
        l =-12; r =-3; fd=2;
        chance=1;
        make();

        title="Gorilla";
        description="Ook Ook OOK!!";
        e=new Eff(EffectType.Food, -4);
        req = e;
        l =-12; r =-1; fd=2;
        chance=1;
        make();

        title="Noises in the night";
        description="Some of your food is gone, along with some storage!";
        e=new Eff(EffectType.Food, -1);
        e1=new Eff(EffectType.FoodStorage, -1);
        req = e;
        l =-8; r =0; fd=1;
        chance=1;
        make();

        title="Flood";
        description="The weather has taken a turn and washed away your supplies.";
        e=new Eff(EffectType.Food, -3);
        e1=new Eff(EffectType.Wood, -3);
        req = e;
        req1 = e1;
        l =-12; r =-1; fd=2;
        chance=1;
        make();
	}

	public static void makeTutorialIslandEvents(){
        title="Heatwave";
        description="The sweltering heat is draining the village";
        e=new Eff(EffectType.Morale, -1);
        l =-6; r =-2; fd =1;
        chance=1;
        make();

        title="Clear skies";
        description="Everyone wakes up with a clear head.";
        l =0; r =3;
        chance=1f;
        e=new Eff(EffectType.Reroll, +1);
        make();
    }

	public static void makeTutorialIslandStory(){
        turn=0;
        title="Land ho!";
        description="You've found land again, it looks like a perfect place to start a new village!";
        e = new Eff(EffectType.Food, 3);
        e1 = new Eff(EffectType.Wood, 3);
        makeStory();

        turn=1;
        title="Build a village";
        description="In order to survive you're going to need to make this a home.";
        e = new Eff(EffectType.BuildTown, 7);
        makeStory();

        turn=8;
        title="Hunger";
        description="The village grows hungry. Upkeep increased by one.";
        e = new Eff(EffectType.Food, -1);
        makeStory();

        turn=16;
        title="Cold";
        description="The nights are getting colder, you need fuel to keep warm.";
        e = new Eff(EffectType.Wood, -1);
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
        e = new Eff(EffectType.FoodStorage, 2);
        e2 = new Eff(EffectType.Food, 7);
        e3 = new Eff(EffectType.Wood, 5);
        makeStory();

        turn=2;
        title="Survive";
        description="There's a storm on the way, you think that if you can weather the storm you will claim the island";
        e = new Eff(EffectType.Survive, 25);
        makeStory();

        turn=8;
        title="Dark skies";
        description="The weather takes a turn, you must be ready soon!";
        e = new Eff(EffectType.Food, -1);
        makeStory();

        turn=10;
        title="Storm";
        description="The storm has hit you, it will be tough to survive this.";
        e = new Eff(EffectType.Food, -2);
        e1 = new Eff(EffectType.Wood, -1);
//        e2 = new Eff(EffectType.FoodBonus, -1, -1);
        makeStory();

        turn=14;
        title="Thunderstorm";
        description="And then the rain started";
        e1 = new Eff(EffectType.Wood, -2);
        makeStory();

        turn=18;
        title="Gap";
        description="A gap in the clouds";
        e1 = new Eff(EffectType.Wood, +1);
//        e2 = new Eff(EffectType.FoodBonus, +1);

        turn=22;
        title="Light";
        description="The storm is clearing, finally";
        e = new Eff(EffectType.Food, +1);
        e1 = new Eff(EffectType.Wood, +1);
        makeStory();
    } 

    public static void makeStormEvents(){

        description = "A cold night";
        e1 = new Eff(EffectType.Morale, -2);
        addOutcome();
        description = "Quickly, repair the roof!";
        e = new Eff(EffectType.Wood, -3);
        addOutcome();
        title="Hailstorm";
        description="Huge hailstones batter your huts";
        l =-5; r =-1; fd =1;
        chance=1;
        req = e1;
        make();

        title = "Momentary Repose";
        description = "A break in the clouds lifts your spirits";
        e = new Eff(EffectType.Morale, 2);
        e1 = new Eff(EffectType.Reroll, 2);
        l = 1; r=12; fd=-2;
        make();

        title = "Whale Carcass";
        description= "The storm in the night washed up a grisly prize";
        e = new Eff(EffectType.Food, 5);
        e1 = new Eff(EffectType.Morale, 1);
        req = e;
        fd = -3; l=1; r=12;
        make();

        title = "Relentless Rain";
        description = "It never stops";
        e = new Eff(EffectType.Food, -1);
        req = e;
        fd=0; l=-2; r=0;
        make();

        title = "Lightning";
        description = "Lightning strikes, setting fire to your storage hut";
        e= new Eff(EffectType.Food, -2);
        e1= new Eff(EffectType.FoodStorage, -2);
        e2= new Eff(EffectType.Wood, -2);
        req =e;
        req1 = e2;
        fd = 2; l=-4; r=2;
        make();

        title = "Fallen tree";
        description = "In the night, a tree falls near one of the huts. You're lucky nobody was injured!";
        e = new Eff(EffectType.Wood, 2);
        fd=-1; l=-1; r= 3;
        make();
    }

    public static void makeGemStory() {
        turn=0;
        title="Red Island";
        description="The island faintly glows red, you are worried that it may erupt";
        e2 = new Eff(EffectType.Food, 2);
        e3 = new Eff(EffectType.Wood, 2);
        makeStory();

        turn=3;
        title="Crimson Dreams";
        description="The eldest villager wakes up from a dream. They tell of a great catastrophe unless 13 crimson gems are offered to the gods here.";
        e = new Eff(EffectType.CollectGems, 13);
        e2 = new Eff(EffectType.Gem, 1);
        makeStory();

        turn=8;
        title="Blood Skies";
        description="The sky has turned deep red, the heat makes it hard to work";
        e = new Eff(EffectType.Food, -1);
        makeStory();

        turn=16;
        title="Burning ground";
        description="The ground shifts beneath your feet";
        e = new Eff(EffectType.Food, -1);
        e1 = new Eff(EffectType.Wood, -1);
        makeStory();

        turn=24;
        title="Dire omen";
        description="The sky turns black, the gods grow tired of your sloth.";
        e = new Eff(EffectType.Food, -2);
        e1 = new Eff(EffectType.Wood, -2);
        makeStory();
    }

    public static void makeGemEvents(){
        title="Shooting star";
        description="Incredible! A shooting star lands at the outskirts of your village. Inside you find a small red gem.";
        e=new Eff(EffectType.Gem, 1);
        l =0; r =12; fd =-2;
        chance=1;
        make();

        title="Starstorm";
        description="A barrage from the skies assaults the village. In the carnage you find a lot of red shards and one whole gem.";
        e=new Eff(EffectType.Gem, 1);
        e1 = new Eff(EffectType.FoodStorage, -3);
        e2 = new Eff(EffectType.Food, -3);
        e3 = new Eff(EffectType.Wood, -2);
        e4 = new Eff(EffectType.Morale, -2);
        l =-12; r =-1; fd =2;
        req = e2;
        req1 = e3;
        chance=1;
        make();

        title="Lights in the sky";
        description = "Cower inside";
        e3 = new Eff(EffectType.Morale, -1);
        addOutcome();
        description = "make an offering";
        e = new Eff(EffectType.Food, -2);
        e1 = new Eff(EffectType.Wood, -2);
        e2 = new Eff(EffectType.Fate, 2);
        addOutcome();
        description= "A dance of red and green in the sky awes the village";
        l =-2; r =2; fd =0;
        chance = .4f;
        req = e3;
        make();

        title="Cursed Orange";
        description="Lightning strikes the ground as someone picked an orange from a tree";
        l =-2; r =1; fd=-1;
        chance=1;
        e=new Eff(EffectType.Food, 1);
        req = e;
        make();

        title = "Astral Visitor";
        description = "A shining deer approaches the village, it seems unafraid. It doesn't linger long and once it's gone you notice it left you something";
        e = new Eff(EffectType.Gem, 2);
        l = 3; r=12; fd = -4;
        chance = 2;
        make();

        title = "Fury";
        description = "A ferocious bull charges through the village and eats your food";
        e = new Eff(EffectType.Food, -2);
        e1 = new Eff(EffectType.FoodStorage, -2);
        e2 = new Eff(EffectType.Morale, -2);
        req = e;
        l = -12; r=-1; fd = +3;
        make();
        req = e;


    }

	private static Array<Outcome> outcomes = new Array<>();

	private static void addOutcome(){
        Array<Eff> fx = new Array<>();
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
        Array<Eff> fx = new Array<>();
        if(e!=null) fx.add(e);
        if(e1!=null) fx.add(e1);
        if(e2!=null) fx.add(e2);
        if(e3!=null) fx.add(e3);
        if(e4!=null) fx.add(e4);
        Array<Eff> reqs = new Array<>();
        if(req!=null) reqs.add(req);
        if(req1!=null) reqs.add(req1);
        if(req2!=null) reqs.add(req2);
        if(req3!=null) reqs.add(req3);
        if(req4!=null) reqs.add(req4);
        Event result = new Event(title,description,fx, outcomes, reqs, chance, l, r, fd, story, turn, uses);
        e=null;
		e1=null;
		e2=null;
		e3=null;
		e4=null;
        req=null;
        req1=null;
        req2=null;
        req3=null;
        req4=null;
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
