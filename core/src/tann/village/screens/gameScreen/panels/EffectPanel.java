package tann.village.screens.gameScreen.panels;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;

public class EffectPanel extends Group {

	final static float imageSize = .7f;
	public static final float heightMult = .6666667f;
	public static final float WIDTH = 110;
	public static final float HEIGHT = 45;
	HashMap<EffectType, Integer> effectAmounts = new HashMap<>();
	public Effect effect;
	public int value;

	public EffectPanel(Effect effect) {
		this.effect = effect;
		setSize(WIDTH, HEIGHT);
		this.value = effect.value;
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
		float border = 3;

		// batch.setColor(new Color(1,0,1,.3f));
		// Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());

		batch.setColor(Colours.light);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());

		switch (effect.source) {
		case Building:
		case Dice:
		case Event:
		case Upkeep:
			batch.setColor(Colours.dark);
			break;
		default:
			break;

		}

		Draw.fillRectangle(batch, getX() + border, getY() + border, getWidth() - border * 2, getHeight() - border * 2);

		batch.setColor(Colours.z_white);
		if(effect.type==EffectType.Fate){
            batch.setColor(effect.value>0?Colours.blue_light:Colours.red);
        }
		Draw.drawSize(batch, effect.type.region, getX() + imageGap, getY() + imageGap, (getHeight() - imageGap * 2),
				(getHeight() - imageGap * 2));
		BitmapFont font = Fonts.font;

		if (effect.type != EffectType.BuildTown) {
			float textStart = getHeight() - imageGap;
			float textWidth = getWidth() - textStart - imageGap;
			Color col = Colours.light;
			 if (value < 0) {
				col = Colours.red;
			}
			String s= (value > 0 ? "+" : "") + value;
			 int fiddle = 2;
			Fonts.draw(batch, s, Fonts.font, col, getX()+textStart, getY()+fiddle, getWidth()-textStart, getHeight());
//			font.draw(batch, , getX() + textStart,
//					getY() + getHeight() / 2f + font.getXHeight() * .35f, textWidth, Align.center, false);
		}

		super.draw(batch, parentAlpha);
	}

}
