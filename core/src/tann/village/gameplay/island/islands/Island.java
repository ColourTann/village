package tann.village.gameplay.island.islands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import tann.village.Images;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.objective.BuildingObjective;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.village.building.BuildingEffect;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Colours;
import tann.village.util.Draw;

public abstract class Island {

	public boolean available;
	public boolean explored;
	TextureRegion tr;
	int x,y;
	
	public Island(TextureRegion tr, int x, int y){
		this.tr=tr;
		this.x=x; this.y=y;
	}
	

	
	IslandActor actor;
	public IslandActor getActor() {
		if(actor==null) actor = new IslandActor(this);
		return actor;
	}
	
	// -----------------event stuff----------------- //
	
	public Event getEventForTurn(int dayNum) {
		Event e = storyEvents.get(dayNum);
		if(e==null) e= getRandomEvent();
		return e;
	}
	
	private static Event getRandomEvent(){
		// make a list of possible events
		validEvents.clear();
		float totalChance =0;
		for(Event e: randomEventsPool){
			if(e.isPotential()){
				validEvents.add(e);
				totalChance += e.chance;
			}
		}
		if(validEvents.size==0) return null;
		float randomRoll = (float) (Math.random()*totalChance);
		for(Event e:validEvents){
			if(e.chance>=randomRoll)return e;
			randomRoll -= e.chance;
		}
		if(validEvents.size!=0){
            System.err.print("No event generated for some reason, getting random valid event");
            return validEvents.get((int)(Math.random()*validEvents.size));
        }
        System.err.print("No event generated for some reason, getting random event");
        return randomEventsPool.get((int)(Math.random()*randomEventsPool.size));

	}
	
	private static Array<Event> validEvents = new Array<>();
	private static Array<Event> randomEventsPool = new Array<>();
	private static HashMap<Integer, Event> storyEvents = new HashMap<>();
	
	abstract void setupRandomPool();
	abstract void setupStory();
	
	public void setup(){
		validEvents.clear();
		storyEvents.clear();
		randomEventsPool.clear();
		setupRandomPool();
		setupStory();
	}
	
	public void addEvents(Array<Event> events, boolean story){
		if(story){
		    for(Event e:events){
		        storyEvents.put(e.turn, e);
            }
        }
        else{
            randomEventsPool.addAll(events);
        }
	}
	

    public Objective objective;
    public void addObjective(Effect effect){
	    switch (effect.type){
            case BuildTown:
                objective = new BuildingObjective(effect.value);
                break;
        }
        objective.init();
        GameScreen.get().addObjectivePanel(objective.getPanel());
    }
}
