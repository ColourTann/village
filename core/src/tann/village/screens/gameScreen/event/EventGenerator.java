package tann.village.screens.gameScreen.event;

import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;

public class EventGenerator {
	
	private static String t, d; // title  description
	static int f, v; // fate, variance
	static float c; // chance
	private static Effect e, e1, e2, e3, e4; // effects
	private static EffectSource ev = EffectSource.Event;
	public static void setup(){
		setupRandomPool();
		setupStory();
	}
	
	private static void setupRandomPool(){
		//amazing//
		t="Supply Crate";
		d="A crate from the ship washes ashore!";
		e=new Effect(EffectType.Food, 2, ev);
		e1=new Effect(EffectType.Wood, 2, ev);
		e2=new Effect(EffectType.Morale, 1, ev);
		e3=new Effect(EffectType.Fate, -2, ev);
		f=3; v=1;
		c=1; 
		makeRandom();
		
		//great//
		t="Spawning";
		d="Hundreds of small fish have come near the shore, it's easy to grab a bunch of them!";
		e=new Effect(EffectType.Food, 3, ev);
		e1=new Effect(EffectType.Fate, -1, ev);
		f=2; v=1;
		c=1; 
		makeRandom();
		
		//ok//
		t="Driftwood";
		d="Looks like it was part of a boat. It's damp but usable.";
		e=new Effect(EffectType.Wood, 1, ev);
		f=1; v=1;
		c=1; 
		makeRandom();
		
		//neutral//
		t="Quiet day";
		d="Nothing special";
		f=0; v=10;
		c=2; 
		makeRandom();

		//not ok//
		t="Cursed Orange";
		d="Lightning struck the ground as someone picked an orange from a tree";
		f=1; v=1;
		c=1;
		e=new Effect(EffectType.Food, 1, ev);
		e1=new Effect(EffectType.Fate, -1, ev);
		makeRandom();
		
		t="Tiny Thief";
		d="A monkey runs off with a handful of food!";
		e=new Effect(EffectType.Food, -1, ev);
		f=-1; v=1;
		c=1; 
		makeRandom();
		
		t="Rot";
		d="Rot starts to set in";
		e=new Effect(EffectType.Wood, -1, ev);
		f=-1; v=1;
		c=1; 
		makeRandom();
		
		//bad//
		t="Heatwave";
		d="The heat is getting you down, your people need shelter!";
		e=new Effect(EffectType.Morale, -2, ev);
		e1=new Effect(EffectType.Fate, +1, ev);
		f=-2; v=1;
		c=1; 
		makeRandom();
		
		t="Noises in the night";
		d="Some of your food is gone, along with some storage!";
		e=new Effect(EffectType.Food, -2, ev);
		e1=new Effect(EffectType.FoodStorage, -1, ev);
		e2=new Effect(EffectType.Fate, +1, ev);
		f=-2; v=1;
		c=1; 
		makeRandom();
		
		//awful//
		t="Flood";
		d="The weather has taken a turn and washed away your supplies.";
		e=new Effect(EffectType.Food, -3, ev);
		e1=new Effect(EffectType.Wood, -3, ev);
		e2=new Effect(EffectType.Fate, +2, ev);
		f=-3; v=-1;
		c=1; 
		makeRandom();
	}
	
	


	private static int turn;
	private static void setupStory(){
		turn=0;
		t="Stranded!";
		d="A sudden storm cause your ship to crash upon an island. It looks like there are only 5 survivors. You manage to salvage some supplies before they're carried away by the waves.";
		e = new Effect(EffectType.Food, 2, EffectSource.Event);
		e1 = new Effect(EffectType.Wood, 2, EffectSource.Event);
		makeStory();
		
		turn=3;
		t="Hunger";
		d="The reduced rations you decided on are not enough keep you alive on the island. Upkeep increased by two.";
		e = new Effect(EffectType.Food, -2, EffectSource.Upkeep);
		makeStory();
		
		turn=6;
		t="Cold";
		d="The nights are getting colder, you need fuel to keep warm.";
		e = new Effect(EffectType.Wood, -1, EffectSource.Upkeep);
		makeStory();
		
		turn=9;
		t="You Must Build A Boat";
		d="The weather is not getting better, to stand a chance at survival you have to escape!";
//		e = new Effect(EffectType.Boat);
		makeStory();
		
		
	}
	
	private static void makeStory() {
		Event.storyEvents.put(turn, make(true));
	}

	private static void makeRandom() {
		Event.randomEventsPool.add(make(false));
	}
	
	private static Event make(boolean story){
		if(!story && c==-1){
			printBadEvent();
			return null;
		}
		Array<Effect> fx = new Array<>();
		if(e!=null) fx.add(e);
		if(e1!=null) fx.add(e1);
		if(e2!=null) fx.add(e2);
		if(e3!=null) fx.add(e3);
		if(e4!=null) fx.add(e4);
		Event result = new Event(t,d,fx,c,f,v);
		e=null;
		e1=null;
		e2=null;
		e3=null;
		e4=null;
		c=-1;
		return result;
	}

	private static void printBadEvent() {
		System.out.println("error generating event "+t);
	}
}
