package tann.village.screens.gameScreen.review;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.Effect;
import tann.village.screens.gameScreen.Effect.EffectType;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.TextBox;

public class ReviewItem extends Group{
	
	
	public static int width = 220, height=120, innerGap = 4, gap=14, border=4, imageSize=height-gap*2-border*2-innerGap*2;
	int value;
	Effect effect;
	public ReviewItem(Effect effect) {
		this.effect=effect;
		setSize(width, height);
		changeValue(effect.value);
		
	}
	
	public void changeValue(int change){
		this.value+=change;
	}
	
	boolean finalised;
	public void finalise(){
		finalised=true;
		TextBox source = new TextBox(effect.source.toString(), Fonts.fontSmall, innerGap, width-imageSize, Align.center);
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
		Draw.drawSize(batch, effect.type.region, getX()+gap+border+innerGap, getY()+gap+border+innerGap, imageSize, imageSize);
		super.draw(batch, parentAlpha);
	}

}
