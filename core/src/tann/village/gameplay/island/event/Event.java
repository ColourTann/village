package tann.village.gameplay.island.event;

import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.village.Inventory;

public class Event {


    final public String title;
    final public String description;
    final public Array<Effect> effects;
    final public float chance;
	final int fateLeft, fateRight;
	public Event (String title, String description, Array<Effect> effects, float chance, int fate, int variance){
		this.title=title;
		this.description=description;
		this.effects=effects;
		this.chance=chance;
		this.fateLeft =fate;
		this.fateRight =variance;
	}
	
	public boolean isPotential() {
		int currentFate = Inventory.get().getResourceAmount(EffectType.Fate);
		if(currentFate<fateLeft || currentFate > fateRight) return false;
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

	public String toString(){
	    return title;
    }
	
	
	
	
}
