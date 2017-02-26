package tann.village.screens.gameScreen.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;
import tann.village.screens.gameScreen.panels.inventory.Inventory;

public class Event {
	protected static Array<Event> randomEventsPool = new Array<>();
	protected static HashMap<Integer, Event> storyEvents = new HashMap<>();
	
	String title;
	String description;
	Array<Effect> effects;
	float chance;
	int fate, variance;
	public Event (String title, String description, Array<Effect> effects, float chance, int fate, int variance){
		this.title=title;
		this.description=description;
		this.effects=effects;
		this.chance=chance;
		this.fate=fate;
		this.variance=variance;
	}
	
	boolean isPotential() {
		int currentFate = Inventory.get().getResourceAmount(EffectType.Fate);
		int diff = currentFate-fate;
		if(Math.abs(diff) > variance) return false;
		for(Effect e: effects){
			if(!Inventory.get().isEffectValid(e)) return false;
		}
		return true;
	}
	
	

	public void action() {
		GameScreen.get().refreshPanels();
		for(Effect e:effects){
			if(e.source==EffectSource.Upkeep) GameScreen.get().increaseUpkeepEffect(e);
			else e.activate();
		}
		GameScreen.get().showWisps();
	}

	private static List<Event> validEvents = new ArrayList<>();
	public static Event getRandomEvent(){
		// make a list of possible events
		validEvents.clear();
		float totalChance =0;
		for(Event e: randomEventsPool){
			if(e.isPotential()){
				validEvents.add(e);
				totalChance += e.chance;
			}
		}
		if(validEvents.size()==0) return null;
		float randomRoll = (float) (Math.random()*totalChance);
		for(Event e:validEvents){
			if(e.chance>=randomRoll)return e;
			randomRoll -= e.chance;
		}
		
		System.err.print("No event generated for some reason, getting random event");
		return validEvents.get((int)(Math.random()*validEvents.size()));
	}
	
	public static Event getEventForTurn(int turn) {
		Event e = storyEvents.get(turn);
		if(e==null) e= getRandomEvent();
		return e;
	}
	
	
}
