package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;

public class Event {


    public String title;
    public String description;
    public Array<Outcome> outcomes = new Array<>();
    public Array<Eff> effects = new Array<>();
    public Array<Eff> requirements = new Array<>();
    public int fateDelta = 0;
    public float chance = 1;
	int fateLeft, fateRight;
	boolean story;
	public int turn = -1;
	public int uses = 999;
	public int minTurn = -1, maxTurn = -1;


    public Event(String title) {
        this(title, null);
    }

	public Event(String title, String description){
	    this.title=title;
	    this.description=description;
    }
	public Event (String title, String description, Array<Eff> effects, Array<Outcome> outcomes, Array<Eff> requirements, float chance, int fate, int variance, int fateDelta, boolean story, int turn, int uses){
	    this.uses=uses;
	    this.turn=turn;
	    this.requirements=requirements;
	    this.effects=effects;
	    this.fateDelta=fateDelta;
		this.title=title;
		this.description=description;
		this.outcomes=outcomes;
		this.chance=chance;
		this.fateLeft =fate;
		this.fateRight =variance;
		this.story = story;
	}

    public boolean isPotential() {
		int currentFate = Village.getInventory().getResourceAmount(EffectType.Fate);
		if(currentFate<fateLeft || currentFate > fateRight || uses<=0) return false;
        for(Eff e: requirements){
            if(!Village.getInventory().isEffectValid(e)) return false;
        }
		return true;
	}
	
	public int getGoodness(){
        return -(int)(Math.signum(fateDelta));
    }

	public void action() {
		GameScreen.get().resetWisps();
		new Eff(EffectType.Fate, fateDelta).activate();
		for(Eff e:effects){
			e.activate();
		}
		GameScreen.get().showWisps();
	}

	public String toString(){
	    return title+":"+fateDelta;
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

    public void fate(int fateLeft, int fateRight, int fateDelta) {
	    this.fateLeft=fateLeft; this.fateRight=fateRight; this.fateDelta = fateDelta;
    }

    public void chance(float chance){
	    chance(chance,-1);
    }

    public void chance(float change, int amount){
        this.chance=chance; this.uses=amount;
    }

    public void turn(int min, int max){
        this.minTurn = min; this.maxTurn = max;
    }

    public void storyTurn(int turn) {
        this.turn=turn;
        this.story=true;
    }

    public void addOutcome(String description) {
        Outcome o = new Outcome(description, effects);
        effects = new Array<>();
    }

    public void validate(){
        if(fateLeft>fateRight) System.err.println("fate left > fate right for "+this);
        if(turn==-1 && isStory()) System.err.println("story with no turn for "+this);
        if(title==null || description==null) System.err.println("no title or desc for "+this);
        if(chance<=0 && !isStory()) System.err.println("no chance for "+this);
        if(uses<=0 && !isStory()) System.err.println("no uses for "+this);
    }
}
