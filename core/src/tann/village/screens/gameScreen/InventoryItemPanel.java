package tann.village.screens.gameScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.util.*;

public class InventoryItemPanel extends Group{
	public static final int WIDTH=150, HEIGHT=100;
	static final int TEXTURESIZE = 80;
	private int value;
	public int max;
	TextureRegion tr;
	public InventoryItemPanel(TextureRegion tr, int value, int max) {
		setSize(WIDTH, HEIGHT);
		this.value=value;
		this.max=max;
		this.tr=tr;
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
	
	int wispAmount=0;
	public void clearWisp(){
		wispAmount=0;
	}
	
	public void wisp(){
		if(wispAmount==0)return;
		TextWisp tWisp = new TextWisp((wispAmount>0?"+":"")+wispAmount, getWidth()/2, getHeight()+Fonts.font.getCapHeight());
		if(wispAmount<0){
			tWisp.setColor(Colours.red);
		}
		addActor(tWisp);
	}
	
	public void changeValue(int delta){
		this.value+=delta;
		wispAmount+=delta;
		setup();
	}
	
	public int getValue(){
		return value;
	}

    TextBox amount;
	TextBox outOf;
	ImageActor imageActor;
	private void setup(){
		clear();
		if(amount==null)amount = new TextBox(String.valueOf(getValue()), Fonts.font, WIDTH, Align.center);
		if(outOf==null) outOf = new TextBox(max!=Integer.MAX_VALUE?"/"+max:"", Fonts.fontSmall, WIDTH, Align.center);
        amount.setup(String.valueOf(getValue()));
		outOf.setTextColour(Colours.brown_light);
		Layoo l = new Layoo(this);
		l.add(1,imageActor,1, amount, 0, outOf, 1);
		l.layoo();
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
