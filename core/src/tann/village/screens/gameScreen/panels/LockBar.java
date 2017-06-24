package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import tann.village.Main;
import tann.village.gameplay.village.villager.die.Die;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Lay;

public class LockBar extends Lay{

    private static LockBar self;
    public static LockBar get(){
        if(self==null) self = new LockBar();
        return self;
    }

    private LockBar() {
        layout();
    }

    Die[] dice = new Die[GameScreen.STARTING_VILLAGERS];

    public void reset(){
        for(int i=0;i<dice.length;i++){
            dice[i]=null;
        }
    }

    public int addDie(Die d){
        for(int i=0;i<dice.length;i++){
            if(dice[i]==null){
                dice[i]=d;
                return i;
            }
        }
        return -1;
    }

    public void removeDie(Die d) {
        for (int i = 0; i < dice.length; i++) {
            if (dice[i] == d) {
                dice[i] = null;
                break;
            }
        }
    }

    @Override
    public void layout() {
        setSize(Main.h(50), Main.h(15));
    }

    public void moveIn() {
        addAction(Actions.moveTo(getX(), Main.height-getHeight(), .5f, Interpolation.pow2Out));
    }

    public void moveAway(){
        addAction(Actions.moveTo(getX(), Main.height, .5f, Interpolation.pow2Out));
    }

    public void render(Batch batch) {
        batch.setColor(Colours.grey);
        Draw.fillActor(batch,this);
        Draw.fillEllipse(batch, getX()-getHeight(), getY(), getHeight()*2, getHeight()*2);
        Draw.fillEllipse(batch, getX()+getWidth()-getHeight(), getY(), getHeight()*2, getHeight()*2);
    }
}
