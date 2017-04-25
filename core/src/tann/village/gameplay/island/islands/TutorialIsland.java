package tann.village.gameplay.island.islands;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.event.EventCreator.EventType;

public class TutorialIsland extends Island{

	public TutorialIsland(TextureRegion tr, int x, int y) {
		super(tr, x, y);
	}

	@Override
	void setupRandomPool() {
		addEvents(EventCreator.getEvents(EventType.Tutorial));
	}

	@Override
	protected void setupStory() {
		turn=0;
		title="Stranded!";
		description="A sudden storm causes your ship to crash upon an island. It looks like there are only 5 survivors. You manage to salvage some supplies before they're carried away by the waves.";
		e = new Effect(EffectType.Food, 2, EffectSource.Event);
		e1 = new Effect(EffectType.Wood, 2, EffectSource.Event);
		makeStory();
		
		turn=4;
		title="Hunger";
		description="The reduced rations you decided on are not enough keep you alive on the island. Upkeep increased by two.";
		e = new Effect(EffectType.Food, -2, EffectSource.Upkeep);
		makeStory();
		
		turn=6;
		title="You Must Build A Boat";
		description="The weather is not getting better, to stand a chance at survival you have to escape!";
		e = new Effect(EffectType.Boat, ev);
		makeStory();
		
		turn=9;
		title="Cold";
		description="The nights are getting colder, you need fuel to keep warm.";
		e = new Effect(EffectType.Wood, -1, EffectSource.Upkeep);
		makeStory();
	}

}
