package tann.village.screens.gameScreen.panels.review;

import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.EffectPanel;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;
import tann.village.screens.gameScreen.panels.inventory.Inventory;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class StarvationPanel extends InfoPanel{
	
	private static float WIDTH=400, HEIGHT=500;
	
	public StarvationPanel(int amountMissing) {
		TextBox ranOut = new TextBox("You ran out of food!", Fonts.font, WIDTH, Align.center);
		TextBox missing = new TextBox("Missing "+amountMissing+" food", Fonts.font, WIDTH, Align.center);
		TextBox morale = new TextBox("You lose 2 morale plus 1 for each food missing.", Fonts.font, WIDTH, Align.center);
		Effect moraleLoss =new Effect(EffectType.Morale, -3, EffectSource.Upkeep);
		EffectPanel moralePanel=new EffectPanel( moraleLoss );
		Inventory.get().activate(moraleLoss);
		setSize(WIDTH, HEIGHT);
		Layoo l = new Layoo(this);
		
		
		l.row(1);
		l.actor(ranOut);
		l.row(1);
		l.actor(missing);
		l.row(1);
		l.actor(morale);
		l.row(1);
		l.actor(moralePanel);
		l.row(1);
		
		l.layoo();
	}

}
