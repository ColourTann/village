package tann.village.screens.gameScreen.event;

import java.util.HashMap;

import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;

public class Event {

	String title;
	String description;
	Array<Effect> effects;
	Array<Effect> requirements;
	int luck;
	
	public Event (String title, String description, Array<Effect> effects){
		this.title=title;
		this.description=description;
		this.effects=effects;
	}
	
	
	public static Array<Event> events = new Array<>();
	private static String t; // title
	private static String d; // description
	int l;
	int f, v; // fate, variance
	private static Effect r, r1, r2; // requirement inverse effects
	private static Effect e, e1, e2, e3, e4; // effects
	public static void setup(){
		setupRandomPool();
		setupStory();
	}
	
	private static void setupRandomPool(){
		t="Spawning";
		d="Hundreds of small fish have come near the shore, it's easy to grab a bunch of them!";
		e=new Effect(EffectType.Food, 1, EffectSource.Event);
		e1=new Effect(EffectType.Food, 1, EffectSource.Event);
		makeRandom();
		
		t="Driftwood";
		d="Looks like it was part of a boat. It's damp but usable.";
		e=new Effect(EffectType.Wood, 1, EffectSource.Event);
		make();
		
		t="Flood";
		d="The weather has taken a turn and washed away your supplies.";
		e=new Effect(EffectType.Food, -2, EffectSource.Event);
		e1=new Effect(EffectType.Wood, -2, EffectSource.Event);
		make();
		
		t="Hot weather";
		d="The heat is getting you down, your people need shelter!";
		e=new Effect(EffectType.Morale, -2, EffectSource.Event);
		make();
		
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
	
	
	public static HashMap<Integer, Event> storyEvents = new HashMap<>();
	private static void makeStory() {
		storyEvents.put(turn, make());
	}

	private static void makeRandom() {
		events.add(make());
	}
	
	public static Event getRandomEvent(){
		return events.random();
	}
	
	private static Event make(){
		Array<Effect> fx = new Array<>();
		if(e!=null) fx.add(e);
		if(e1!=null) fx.add(e1);
		if(e2!=null) fx.add(e2);
		if(e3!=null) fx.add(e3);
		if(e4!=null) fx.add(e4);
		Event result = new Event(t,d,fx);
		e=null;
		e1=null;
		e2=null;
		e3=null;
		e4=null;
		return result;
	}

	public void action() {
		GameScreen.get().refreshPanels();
		for(Effect e:effects){
			if(e.source==EffectSource.Upkeep) GameScreen.get().increaseUpkeepEffect(e);
			else e.activate();
		}
		GameScreen.get().showWisps();
	}

	public static Event getEventForTurn(int turn) {
		Event e = storyEvents.get(turn);
		if(e==null) e= getRandomEvent();
		return e;
	}
	
	
}
