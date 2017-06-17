package tann.village.screens.gameScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.Images;
import tann.village.Main;
import tann.village.screens.gameScreen.panels.InventoryPanel;
import tann.village.util.*;

public class InventoryItemPanel extends Lay{
	private int value;
	public int max;
	TextureRegion tr;
	public InventoryItemPanel(TextureRegion tr, int value, int max) {
		this.value=value;
		this.max=max;
		this.tr=tr;
        layout();
    }

    @Override
    public void layout() {
        setSize(Math.min(Main.h(30), Main.w(15)), InventoryPanel.invItemHeight());
        float TEXTURESIZE =getHeight()*.8f;
        imageActor = new ImageActor(tr, TEXTURESIZE, TEXTURESIZE);
        setup();
    }
	
	public void setValue(int value){
		this.value=value;
		setup();
	}
	
	public void setMax(int max){
		this.max=max;
		setup();
	}
	
	int prevAmount=0;

	public void clearWisp(){
		prevAmount=value;
	}

	TextWisp tWisp;
	public void wisp(){

        int diff = value-prevAmount;
		if(diff==0)return;
		tWisp = new TextWisp((diff>0?"+":"")+diff,  getWidth()/2, 0);
        if(diff<0){
			tWisp.setColor(Colours.red);
		}
		addActor(tWisp);
		clearWisp();
	}
	
	public void changeValue(int delta){
		this.value+=delta;
		setup();
	}
	
	public int getValue(){
		return value;
	}

    TextBox amount;
	TextBox outOf;
	ImageActor imageActor;
	private void setup(){
        clearChildren();
		Color col = null;
        if(tr== Images.fate){
             col = (getValue()>0?Colours.blue_light:getValue()<0?Colours.red:Colours.grey);
        }
		amount = new TextBox(String.valueOf(getValue()), Fonts.font, getWidth(), Align.center);
		outOf = new TextBox(max!=Integer.MAX_VALUE?"/"+max:"", Fonts.fontSmall, getWidth(), Align.center);
		outOf.setup(max!=Integer.MAX_VALUE?"/"+max:"");
        amount.setup(String.valueOf(getValue()));
        outOf.setTextColour(Colours.light);
        Layoo l = new Layoo(this);
        l.add(1,imageActor,1, amount, 0, outOf, 1);

        if(col!=null){
            amount.setTextColour(col);
            imageActor.setColor(col);
        }
        l.layoo();
        if(tWisp!=null){
            addActor(tWisp);
        }
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.z_white);
        super.draw(batch, parentAlpha);
	}

	public void maxOut() {
		setValue(max);
		setup();
	}

	public void addMax(int value) {
		setMax(max+value);
	}


}
