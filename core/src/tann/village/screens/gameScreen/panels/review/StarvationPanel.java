package tann.village.screens.gameScreen.panels.review;

import com.badlogic.gdx.utils.Align;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.EffectPanel;
import tann.village.gameplay.village.Inventory;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class StarvationPanel extends InfoPanel{
	

	public StarvationPanel(int foodMissing, int woodMissing) {

        GameScreen.get().resetWisps();
	    TextBox ranOut = new TextBox("You ran out of "+(foodMissing>0?"food":"") + ((foodMissing>0 && woodMissing>0)?" and " :"") +((woodMissing>0)?"wood":"") +"!", Fonts.font, -1, Align.center);

        int amountMissing = foodMissing + woodMissing;

		TextBox missing = new TextBox("Missing "+amountMissing+" resource"+(amountMissing==1?"":"s"), Fonts.fontSmall, -1, Align.center);
		int moraleLoss = 1 + Math.abs(amountMissing/2);
		Effect moraleLossEffect =new Effect(EffectType.Morale, -moraleLoss, EffectSource.Upkeep);
		EffectPanel moralePanel=new EffectPanel(moraleLossEffect);
		Village.getInventory().activate(moraleLossEffect);
		int gap = 20;
		setSize(Math.max(ranOut.getWidth(), missing.getWidth())+gap*2,ranOut.getHeight() + missing.getHeight() +  moralePanel.getHeight() + gap * 4);
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(ranOut);
		l.row(1);
		l.actor(missing);
		l.row(1);
		l.actor(moralePanel);
		l.row(1);
		l.layoo();
		GameScreen.get().showWisps();
	}

}
