package tann.village.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.GameScreen;

public class NumberPanel extends Group{
	static final int size = 40;
	private int value;
	public NumberPanel() {
		setSize(size, size);
	}
	
	public void setValue(int value){
		this.value=value;
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
	}
	
	public int getValue(){
		return value;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		Fonts.font.setColor(Colours.light);
		Fonts.font.draw(batch, String.valueOf(value), getX(), getY()+getHeight()/2 + Fonts.font.getCapHeight()/2, size, Align.center,false);
		super.draw(batch, parentAlpha);
	}
}
