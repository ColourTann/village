package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.village.Inventory;

public class Event {


    final public String title;
    final public String description;
    final public Array<Outcome> outcomes;
    final public Array<Effect> effects;
    final public Array<Effect> requirements;
    final public int fateDelta;
    final public float chance;
	final int fateLeft, fateRight;
	final boolean story;
	public final int turn;
	public Event (String title, String description, Array<Effect> effects, Array<Outcome> outcomes, Array<Effect> requirements, float chance, int fate, int variance, int fateDelta, boolean story, int turn){
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
		if(currentFate<fateLeft || currentFate > fateRight) return false;
        for(Effect e: requirements){
            if(!Village.getInventory().isEffectValid(e)) return false;
        }
		return true;
	}
	
	public int getGoodness(){
        return -(int)(Math.signum(fateDelta));
    }

	public void action() {
		GameScreen.get().resetWisps();
		new Effect(EffectType.Fate, fateDelta, EffectSource.Event).activate();
		for(Effect e:effects){
			if(e.source==EffectSource.Upkeep) GameScreen.get().increaseUpkeepEffect(e);
			else e.activate();
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
