package tann.village.gameplay.village.phase;

import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.eventStuff.EventPanel;
import tann.village.util.Sounds;

public class EventPhase extends Phase {
    @Override
    public void activate() {
        int dayNum = Village.get().getDayNum();
        Event event = Village.island.getEventForTurn(dayNum);
        Village.get().nextDay();
        int goodness = event.getGoodness();
        String[] sound = null;
        if (goodness == -1) sound = Sounds.eventNegBird;
        else if (goodness == 1) sound = Sounds.eventPosBird;
        else sound = Sounds.eventNeuBird;
        Sounds.playSound(sound, 1, 1);
        EventPanel eventPanel = new EventPanel(event, dayNum);
        event.initialAction();
        GameScreen.get().addWithProceedButton(eventPanel, true);
    }

    @Override
    public void deactivate() {
        Village.get().pushPhase(new RollingPhase());
    }
}
