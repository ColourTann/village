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
		t="Spawning";
		d="Hundreds of small fish have come near the shore, it's easy to grab a bunch of them!";
		e=new Effect(EffectType.Food, 2, ev);
		c=1; f=2; v=1;
		makeRandom();
		
		t="Driftwood";
		d="Looks like it was part of a boat. It's damp but usable.";
		e=new Effect(EffectType.Wood, 1, ev);
		c=1; f=1; v=1;
		makeRandom();
		
		t="Flood";
		d="The weather has taken a turn and washed away your supplies.";
		e=new Effect(EffectType.Food, -2, ev);
		e1=new Effect(EffectType.Wood, -2, ev);
		f=-2; v=1; c=1;
		makeRandom();
		
		t="Hot weather";
		d="The heat is getting you down, your people need shelter!";
		e=new Effect(EffectType.Morale, -2, ev);
		c=1; f=-2; v=2;
		makeRandom();
		
		t="Quiet day";
		d="Nothing special";
		c=2; f=0; v=10;
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
		d="The reduced rations you decided on are not enough to fuel your hard labour on the island. Upkeep increased by two.";
		e = new Effect(EffectType.Food, -2, EffectSource.Upkeep);
		makeStory();
		
		turn=10;
		t="Hunger";
		d="Your village needs more food, they're looking malnourished and weak.";
		e = new Effect(EffectType.Food, -2, EffectSource.Upkeep);
		makeStory();
		
		turn=15;
		t="Howling";
		d="";
		
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
