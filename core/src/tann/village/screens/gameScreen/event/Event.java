package tann.village.screens.gameScreen.event;

import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.Effect;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.Effect.EffectType;

public class Event {

	String title;
	String description;
	Array<Effect> effects;
	public Event (String title, String description, Array<Effect> effects){
		this.title=title;
		this.description=description;
		this.effects=effects;
	}
	
	
	public static Array<Event> events = new Array<>();
	private static String t;
	private static String d;
	private static Effect e;
	private static Effect e1;
	private static Effect e2;
	private static Effect e3;
	public static void setup(){
		t="Spawning";
		d="Hundreds of small fish have come near the shore, it's easy to grab a bunch of them!";
		e=new Effect(EffectType.Event_Food, 3);
		make();
		
		t="Driftwood";
		d="Looks like it was part of a boat. It's damp but usable.";
		e=new Effect(EffectType.Event_Wood, 2);
		make();
		
		t="Flood";
		d="The weather has taken a turn and washed away your supplies.";
		e=new Effect(EffectType.Event_Food, -2);
		e1=new Effect(EffectType.Event_Wood, -2);
		make();
		
		t="Hot weather";
		d="The heat is getting you down, your people need shelter!";
		e=new Effect(EffectType.Morale_Event, -2);
		make();
	}
	
	public static Event getRandomEvent(){
		return events.random();
	}
	
	private static void make(){
		Array<Effect> fx = new Array<>();
		if(e!=null) fx.add(e);
		if(e1!=null) fx.add(e1);
		if(e2!=null) fx.add(e2);
		if(e3!=null) fx.add(e3);
		events.add(new Event(t,d,fx));
		e=null;
		e1=null;
		e2=null;
		e3=null;
		
	}

	public void action() {
		GameScreen.get().refreshPanels();
		for(Effect e:effects){
			e.activate();
		}
		GameScreen.get().showWisps();
	}
	
	
}
