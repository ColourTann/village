package tann.village.screens.gameScreen.effect;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.effect.Effect.EffectType;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;

public class EffectPanel extends Group{
	
	final static float imageSize=.7f; 
	public static final float heightMult = .6666667f;
	public static final float WIDTH = 110;
	public static final float HEIGHT = 45;
	HashMap<EffectType, Integer> effectAmounts = new HashMap<>();
	public Effect effect;
	public int value;
	float gap;
	public EffectPanel(Effect effect, float gap) {
		this.effect=effect;
		this.gap=gap;
		setSize(WIDTH+gap*2, HEIGHT+gap*2);
		this.value=effect.value;
	}
	
	public void changeValue(int value) {
		this.value+=value;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		float imageGap =getHeight()*(1-imageSize)/2f;
		float border = 3;
		
//		batch.setColor(new Color(1,0,1,.3f));
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		
		batch.setColor(Colours.light);
		Draw.fillRectangle(batch, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX()+gap+border, getY()+gap+border, getWidth()-border*2-gap*2, getHeight()-border*2-gap*2);
		
		batch.setColor(Colours.z_white);
		Draw.drawSize(batch, effect.type.region, getX()+gap+imageGap, getY()+gap+imageGap, (getHeight()-gap*2-imageGap*2), (getHeight()-gap*2-imageGap*2));
		BitmapFont font = Fonts.font;
		
		
		float textStart = getHeight()-imageGap;
		float textWidth = getWidth()-textStart-imageGap-gap*2;
		if(value>0){
			Fonts.font.setColor(Colours.light);
		}
		else if (value<0){
			Fonts.font.setColor(Colours.red);
		}
		font.draw(batch, (value>0?"+":"")+value, getX()+textStart, getY()+getHeight()/2f+font.getXHeight()*.75f, textWidth, Align.center, false);
		
		
		super.draw(batch, parentAlpha);
	}

}
