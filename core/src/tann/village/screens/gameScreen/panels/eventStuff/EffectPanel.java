package tann.village.screens.gameScreen.panels.eventStuff;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import tann.village.Main;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Lay;

public class EffectPanel extends Lay {

	final static float imageSize = .7f;
	public static final float heightMult = .6666667f;
	HashMap<EffectType, Integer> effectAmounts = new HashMap<>();
	public Eff effect;
	public int value;

	public EffectPanel(Eff effect) {
		this.effect = effect;
		layout();
		this.value = effect.value;
	}

    public static float staticWidth(){
        return Main.h(17);
    }

    public static float staticHeight(){
        return Main.h(7);
    }

    @Override
    public void layout() {
        setSize(staticWidth(), staticHeight());
    }

	public void changeValue(int value) {
		this.value += value;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		float imageGap = getHeight() * (1 - imageSize) / 2f;
		int border = Math.max(1,(int)(Main.h(.3f)));

		// batch.setColor(new Color(1,0,1,.3f));
		// Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());

		batch.setColor(Colours.light);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());


		Draw.fillRectangle(batch, getX() + border, getY() + border, getWidth() - border * 2, getHeight() - border * 2);

		batch.setColor(Colours.z_white);
		if(effect.type==EffectType.Fate){
            batch.setColor(effect.value>0?Colours.blue_light:Colours.red);
        }
		Draw.drawSize(batch, effect.type.region, getX() + imageGap, getY() + imageGap, (getHeight() - imageGap * 2),
				(getHeight() - imageGap * 2));
		BitmapFont font = Fonts.font;

		if (!effect.type.objective) {
			float textStart = getHeight() - imageGap;
			float textWidth = getWidth() - textStart - imageGap;
			Color col = Colours.light;
			 if (value < 0) {
				col = Colours.red;
			}
			String s= (value > 0 ? "+" : "") + value;
			 float fiddle = 2;
			Fonts.draw(batch, s, Fonts.font, col, getX()+textStart, getY()+fiddle, getWidth()-textStart, getHeight());
//			font.draw(batch, , getX() + textStart,
//					getY() + getHeight() / 2f + font.getXHeight() * .35f, textWidth, Align.center, false);
		}

		super.draw(batch, parentAlpha);
	}

}
