package tann.village.screens.gameScreen.review;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.Effect.EffectType;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.TextBox;

public class ReviewItem extends Group{
	
	
	public static int width = 220, height=120, innerGap = 4, gap=14, border=4, imageSize=height-gap*2-border*2-innerGap*2;
	String text;
	int value;
	EffectType type;
	public ReviewItem(EffectType type, int value) {
		this.type=type;
		setSize(width, height);
		changeValue(value);
		
	}
	
	public void changeValue(int change){
		this.value+=change;
		this.text=(value>0?"+":"")+value;
	}
	
	boolean finalised;
	public void finalise(){
		finalised=true;
		TextBox source = new TextBox(type.text, Fonts.fontSmall, innerGap, width-imageSize, Align.center);
		addActor(source);
		TextBox effect = new TextBox((value>=0?"+":"")+value, Fonts.fontSmall, border, width-imageSize, Align.center);
		addActor(effect);
		source.setPosition((getWidth()-imageSize)/2 + imageSize - source.getWidth()/2, (getHeight()-gap*2-border*2-innerGap*2)/4*3+gap+border+innerGap-effect.getHeight()/2);
		effect.setPosition((getWidth()-imageSize)/2 + imageSize - effect.getWidth()/2, (getHeight()-gap*2-border*2-innerGap*2)/4+gap+border+innerGap-source.getHeight()/2);
		if(value<0) effect.setTextColour(Colours.red);
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(!finalised) finalise();
		batch.setColor(Colours.light);
		Draw.drawRectangle(batch, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2, border);
		batch.setColor(Colours.z_white);
		Draw.drawSize(batch, type.region, getX()+gap+border+innerGap, getY()+gap+border+innerGap, imageSize, imageSize);
		super.draw(batch, parentAlpha);
//		Fonts.fontSmall.setColor(Colours.light);
//		Fonts.fontSmall.draw(batch, type.text, getX()+imageSize+gap+border+innerGap*2, getY()+getHeight()-border-gap-innerGap, getWidth()-imageSize-gap*2-border*2-innerGap*3, Align.center, false);
//		Fonts.font.setColor(value>0?Colours.green_light:Colours.red);
//		Fonts.font.draw(batch, text, getX()+imageSize+gap*2+border, getY()+getHeight()-Fonts.fontSmall.getCapHeight() - Fonts.font.getCapHeight(), getWidth()-imageSize-gap*2+border*2, Align.center, true);
	}

}
