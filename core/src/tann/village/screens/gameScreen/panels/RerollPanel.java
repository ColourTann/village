package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import tann.village.Images;
import tann.village.gameplay.village.RollManager;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.InventoryItemPanel;
import tann.village.util.*;

public class RerollPanel extends Group{
	
	static final int WIDTH = 120, HEIGHT = 140, TICK_SIZE=80;

	public RerollPanel() {
	    setTouchable(Touchable.disabled);
		setSize(WIDTH, HEIGHT);
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
	    float rollSize = 80;
        if(diceSelected==0){
            batch.setColor(Colours.grey);
        }
        else{
            batch.setColor(Colours.z_white);
        }
        if(!locked){
            batch.setColor(Colours.grey);
        }
        Draw.drawSize(batch, Images.roll, getX()+getWidth()/2-rollSize/2, getY() + getHeight()-rollSize, rollSize, rollSize);

        if(rollsLeft > 0 ){
            Fonts.font.setColor(Colours.light);
        }
        else{
            Fonts.font.setColor(Colours.grey);
        }
        Fonts.font.draw(batch, rollsLeft+"/"+maximumRolls, getX()+getWidth()/4, getY()+40, getWidth()/2, Align.center, false);
    }
}
