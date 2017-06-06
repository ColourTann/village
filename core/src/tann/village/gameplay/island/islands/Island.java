package tann.village.gameplay.island.islands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.objective.*;
import tann.village.gameplay.village.building.Building;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.island.objective.Objective.ObjectiveEffect;

public abstract class Island {

	public boolean available;
	public boolean explored;
	TextureRegion tr;
	int x,y;

	protected Array<Villager.VillagerType> availablesVillagerTypes = new Array<>();
    protected Array<Building> availableBuildings = new Array<>();

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
        Event toReturn = null;
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
			if(e.chance>=randomRoll){
			    toReturn = e;
			    break;
            }
			randomRoll -= e.chance;
		}
		if(toReturn == null){
            System.err.print("No event generated for some reason, getting random valid event");
            toReturn = validEvents.get((int)(Math.random()*validEvents.size));
        }
		toReturn.uses--;
        return toReturn;

	}
	
	private static Array<Event> validEvents = new Array<>();
	private static Array<Event> randomEventsPool = new Array<>();
	private static HashMap<Integer, Event> storyEvents = new HashMap<>();

    protected abstract void setupRandomPool();
    protected abstract void setupStory();
    protected abstract void setupClasses();
    protected abstract void setupBuildings();

	public void setup(){
		validEvents.clear();
		storyEvents.clear();
		randomEventsPool.clear();
		setupRandomPool();
		setupStory();
		setupBuildings();
		setupClasses();
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
	

    public Array<Objective> objectives = new Array<Objective>();
    public void addObjective(Effect effect){
        Objective objective = null;
	    switch (effect.type){
            case BuildTown:
                objective = new BuildingObjective(effect.value);
                break;
            case CollectGems:
                objective = new GemsObjective(effect.value);
                break;
            case TimeLimit:
                objective = new TimeLimitObjective(effect.value);
                break;
            case Survive:
                objective = new SurviveObjective(effect.value);
                break;
        }
        objective.init();
        GameScreen.get().addObjectiveToPanel(objective);
        objectives.add(objective);
    }

    public void objectiveProgress(ObjectiveEffect type, int i) {
        for(Objective o:objectives){
            o.objectiveProgress(type, i);
        }
    }

    public abstract String getVictoryText();

    public Building getRandomBuilding() {
        return availableBuildings.random();
    }

    public Villager.VillagerType[] getRandomVillagerTypes(int level, int amount){
        Villager.VillagerType[] results = new Villager.VillagerType[amount];
        List<Villager.VillagerType> availables = new ArrayList<>();
        for(Villager.VillagerType t: availablesVillagerTypes){
            if(t.level==level){
                availables.add(t);
            }
        }
        if(availables.size()<amount) return null;
        Collections.shuffle(availables);
        for(int i=0;i<amount;i++){
            results[i]=availables.remove(0);
        }
        return results;
    }

    public enum ObjectiveOutcome{Success, Fail, Nothing}
    public ObjectiveOutcome objectivesCompletes() {
        if(objectives.size==0){
            return ObjectiveOutcome.Nothing;
        }
        boolean complete = true;
        for(Objective o:objectives){
            if(o.isComplete() && o.isDeath()){
               return ObjectiveOutcome.Fail;
            }
            if(!o.isComplete() && !o.isDeath()){
                complete = false;
            }
        }
        return complete?ObjectiveOutcome.Success:ObjectiveOutcome.Nothing;
    }
}
