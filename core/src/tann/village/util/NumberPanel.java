package tann.village.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.GameScreen;

public class NumberPanel extends Group{
	static final int WIDTH=70, HEIGHT=40;
	private int value;
	public int max;
	public NumberPanel(int max) {
		setSize(WIDTH, HEIGHT);
		this.max=max;
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
	
	private void setup(){
		clear();
		TextBox amount = new TextBox(String.valueOf(getValue()), Fonts.font, WIDTH, Align.center);
		TextBox outOf = new TextBox(max>0?"/"+max:"", Fonts.fontSmall, WIDTH, Align.center);
		outOf.setTextColour(Colours.brown_light);
		Layoo l = new Layoo(this);
		l.add(1, amount, 0, outOf, 1);
		l.layoo();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
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
