package tann.village.screens.gameScreen.event;

import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;

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
	private static Effect e4;
	public static void setup(){
		t="Spawning";
		d="Hundreds of small fish have come near the shore, it's easy to grab a bunch of them!";
		e=new Effect(EffectType.Food, 1, EffectSource.Event);
		e1=new Effect(EffectType.Food, 1, EffectSource.Event);
		e2=new Effect(EffectType.Food, 1, EffectSource.Event);
		e3=new Effect(EffectType.Food, 1, EffectSource.Event);
		e4=new Effect(EffectType.Food, 1, EffectSource.Event);
		make();
		
		t="Driftwood";
		d="Looks like it was part of a boat. It's damp but usable.";
		e=new Effect(EffectType.Wood, 2, EffectSource.Event);
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
	
	public static Event getRandomEvent(){
		return events.random();
	}
	
	private static void make(){
		Array<Effect> fx = new Array<>();
		if(e!=null) fx.add(e);
		if(e1!=null) fx.add(e1);
		if(e2!=null) fx.add(e2);
		if(e3!=null) fx.add(e3);
		if(e4!=null) fx.add(e4);
		events.add(new Event(t,d,fx));
		e=null;
		e1=null;
		e2=null;
		e3=null;
		e4=null;
	}

	public void action() {
		GameScreen.get().refreshPanels();
		for(Effect e:effects){
			e.activate();
		}
		GameScreen.get().showWisps();
	}
	
	
}
