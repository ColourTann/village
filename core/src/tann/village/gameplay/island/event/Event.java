package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.village.Inventory;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;

public class Event {


    public String title;
    public String description;
    public Array<Outcome> outcomes = new Array<>();
    public Array<Eff> effects = new Array<>();
    public Array<Eff> requirements = new Array<>();
    public float chance = 1;
	boolean story;
	public int turn = -1;
	public int uses = 999;
	public int minTurn = -1, maxTurn = -1;
    public float joel=-123456789;

    public Event(String title) {
        this(title, null);
    }

	public Event(String title, String description){
	    this.title=title;
	    this.description=description;
    }
	public Event (String title, String description, Array<Eff> effects, Array<Outcome> outcomes, Array<Eff> requirements, float chance, float joel, boolean story, int turn, int uses){
	    this.uses=uses;
	    this.turn=turn;
	    this.requirements=requirements;
	    this.effects=effects;
		this.title=title;
		this.description=description;
		this.outcomes=outcomes;
		this.chance=chance;
		this.joel = joel;
		this.story = story;
	}

    public boolean isPotential() {
		float joel = Village.get().getJoel() + this.joel;
		if(joel>1||joel<-1) return false;
        for(Eff e: requirements){
            if(!Village.getInventory().isEffectValid(e)) return false;
        }
		return true;
	}
	
	public int getGoodness(){
        return -(int)(Math.signum(joel));
    }

	public void action() {
		GameScreen.get().resetWisps();
        Village.get().addJoel(joel);
        Village.get().activate(effects, true, false);
		GameScreen.get().showWisps();
	}

	public String toString(){
	    return title+":"+joel;
    }

    public boolean isStory() {
	    return story;
    }

    public void eff(Eff eff) {
	    effects.add(eff);
    }

    public void effR(Eff eff) {
	    effects.add(eff);
	    requirements.add(eff);
    }

    public void joel(float joel) {
	    if(Math.abs(joel)>2){
            System.err.println("too much joel: "+this);
        }
	    this.joel=joel;
    }

    public void chance(float chance){
	    chance(chance,-1);
    }

    public void chance(float chance, int amount){
        this.chance=chance; this.uses=amount;
    }

    public void turn(int min, int max){
        this.minTurn = min; this.maxTurn = max;
    }

    public void storyTurn(int turn) {
        this.turn=turn;
        this.story=true;
        this.joel=0;
    }

    public void addOutcome(String description) {
        addOutcome(description,0);
    }

    public void addOutcome(String description, int fateCost) {
        Outcome o = new Outcome(description, effects, fateCost);
        effects = new Array<>();
        outcomes.add(o);
    }

    public void validate(){
        if(joel<-1||joel>1) System.err.println("fate left > fate right for "+this);
        if(turn==-1 && isStory()) System.err.println("story with no turn for "+this);
        if(title==null || description==null) System.err.println("no title or desc for "+this);
        if(chance<=0 && !isStory()) System.err.println("no chance for "+this);
        if(uses<=0 && !isStory()) System.err.println("no uses for "+this);
    }
}
