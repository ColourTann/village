package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;

public class Event {


    final public String title;
    final public String description;
    final public Array<Outcome> outcomes;
    final public Array<Eff> effects;
    final public Array<Eff> requirements;
    final public int fateDelta;
    final public float chance;
	final int fateLeft, fateRight;
	final boolean story;
	public final int turn;
	public int uses;
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
		    //TODO THIS!!!
//			GameScreen.get().increaseUpkeepEffect(e);
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
}
