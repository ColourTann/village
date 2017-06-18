package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import tann.village.Images;
import tann.village.Main;
import tann.village.gameplay.village.RollManager;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.InventoryItemPanel;
import tann.village.util.*;

public class RerollPanel extends Lay{
	
	static final int TICK_SIZE=80;

	public RerollPanel() {
	    setTouchable(Touchable.disabled);
	    layout();
	}

    @Override
    public void layout() {
        setSize(Main.h(20), Main.h(20));
    }

	static int rollsLeft,maximumRolls, diceSelected;
	static boolean locked;

	public void setRolls(int rollsLeft, int maximumRolls){
	    this.rollsLeft=rollsLeft; this.maximumRolls=maximumRolls;
    }

    public void setDiceSelected(int num){
	    diceSelected = num;
    }

    public void setAllDiceLocks(boolean lockedd){
        locked=lockedd;
    }

	@Override
	public void draw(Batch batch, float parentAlpha) {

	    float rollSize = Main.h(15);
        if(diceSelected==0){
            batch.setColor(Colours.grey);
        }
        else{
            batch.setColor(Colours.light);
            if(rollsLeft <= 0 ){
                batch.setColor(Colours.red);
            }
        }
        if(!locked){
            batch.setColor(Colours.grey);
            Draw.drawLoadingAnimation(batch, getX()+getWidth()/2, getY()+getHeight()/3*2+Main.h(3), Main.h(3), Main.h(2), 2, 3);
        }
        else{

            Draw.drawSize(batch, Images.roll, getX()+getWidth()/2-rollSize/2, getY()+Main.h(7), rollSize, rollSize);
        }

        if(rollsLeft > 0 ){
            Fonts.font.setColor(Colours.light);
        }
        else{
            Fonts.font.setColor(Colours.grey);
        }
        Fonts.font.draw(batch, rollsLeft+"/"+maximumRolls, getX()+getWidth()/4, getY()+Main.h(6), getWidth()/2, Align.center, false);
    }

}
