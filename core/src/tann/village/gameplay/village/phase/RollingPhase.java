package tann.village.gameplay.village.phase;

import tann.village.bullet.BulletStuff;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.RollManager;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.die.Die;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.rollStuff.LockBar;

public class RollingPhase extends Phase{
    @Override
    public void activate() {
        GameScreen.get().showRollContainer(true);
        Village.get().startOfRoll();
        BulletStuff.refresh(Village.get().villagers);
        RollManager.setMaximumRolls(Village.get().getRerolls());
        RollManager.refreshRolls();
        for(Die d: BulletStuff.dice){
            d.addToScreen();
        }
        GameScreen.get().roll(false);
        Village.get().getUpkeep().activateDelta();
        LockBar.get().moveIn();
        LockBar.get().reset();
        Village.get().pushPhase(new EventPhase());
    }

    @Override
    public void deactivate() {
        GameScreen.get().showRollContainer(false);
        LockBar.get().moveAway();
        Village.get().actionPotential();
        BulletStuff.clearDice();
        int spoiled = Village.getInventory().imposeLimits();
        if(spoiled>0){
            Village.get().pushPhase(new SpoilPhase());
        }
        if(Village.getInventory().getResourceAmount(Eff.EffectType.Food)<0 || Village.getInventory().getResourceAmount(Eff.EffectType.Wood)<0){
            Village.get().pushPhase(new StarvationPhase());
        }
    }

    @Override
    public boolean allowDieClicking() {
        return true;
    }

    @Override
    public boolean allowBuying() {
        return true;
    }
}
