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
        EventCreator.makeTutorialEvents();
	    addEvents(EventCreator.getEvents(), false);
	}

	@Override
	protected void setupStory() {
        EventCreator.makeTutorialIslandEvents();
        addEvents(EventCreator.getEvents(), true);
	}

}
