package tann.village.gameplay.island.islands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.village.project.Project;
import tann.village.gameplay.village.villager.Villager;

public abstract class Island {

	public boolean available;
	public boolean explored;
	TextureRegion tr;
	int x,y;

	protected Array<Villager.VillagerType> availablesVillagerTypes = new Array<>();
    protected Array<Project> availableBuildings = new Array<>();

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

	public Array<Event> getRandomEvents(){
        return randomEventsPool;
    }

    protected abstract void setupRandomPool();
    protected abstract void setupStory();
    protected abstract void setupClasses();
    protected abstract void setupBuildings();
    protected abstract String getBackgroundString();
    public abstract String getAmbienceString();
    public abstract String getIslandName();

    public TextureRegion background;

	public void setup(){
		validEvents.clear();
		storyEvents.clear();
		randomEventsPool.clear();
		setupRandomPool();
		setupStory();
		setupBuildings();
		setupClasses();
		background = Main.atlas.findRegion(getBackgroundString());
    }

    public void addEvent(Event event){
        if(event.isStory()){
            storyEvents.put(event.turn, event);
        }
        else{
            randomEventsPool.add(event);
        }
    }

    public void addEvents(Array<Event> events){
        for(Event e:events){
            addEvent(e);
        }
    }

    public abstract String getVictoryText();

    public Project getRandomBuilding() {
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
}
