package tann.village.gameplay.village;

import tann.village.bullet.BulletStuff;
import tann.village.screens.gameScreen.panels.ConfirmPanel;
import tann.village.screens.gameScreen.panels.RerollPanel;

public class RollManager {
   private static int maxRolls, rolls;
    public static void setMaximumRolls(int max){
        RollManager.maxRolls=max;
        updateRolls();
    }

    public static void refreshRolls(){
        RollManager.rolls=maxRolls;
        updateRolls();
    }

    public static void spendRoll(){
        RollManager.rolls--;
        updateRolls();
    }

    public static boolean hasRoll(){
        return RollManager.rolls>0;
    }

    public static void updateRolls(){
        getRollPanel().setRolls(RollManager.rolls, RollManager.maxRolls);
        getRollPanel().setDiceSelected(BulletStuff.numSelectedDice());
        getRollPanel().setAllDiceLocks(BulletStuff.isFinishedRolling());
        getConfirmPanel().setRolling(!BulletStuff.isFinishedRolling());
    }

    private static RerollPanel panel;
    public static RerollPanel getRollPanel(){
        if(panel==null){
            panel = new RerollPanel();
        }
        return panel;
    }

    private static ConfirmPanel confirmPanel;
    public static ConfirmPanel getConfirmPanel(){
        if(confirmPanel==null){
            confirmPanel = new ConfirmPanel();
        }
        return confirmPanel;
    }
}
