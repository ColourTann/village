package tann.village.screens.gameScreen.panels.review;

import com.badlogic.gdx.utils.Align;

import tann.village.Images;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.panels.eventStuff.EffectPanel;
import tann.village.util.*;

public class StarvationPanel extends InfoPanel{
	

	public StarvationPanel(int foodMissing, int woodMissing) {
	    TextBox ranOut = new TextBox("You ran out of "+(foodMissing>0?"food":"") + ((foodMissing>0 && woodMissing>0)?" and " :"") +((woodMissing>0)?"wood":"") +"!", Fonts.font, -1, Align.center);
        int amountMissing = foodMissing + woodMissing;
		TextBox missing = new TextBox("Missing "+amountMissing+" resource"+(amountMissing==1?"":"s"), Fonts.fontSmall, -1, Align.center);
		int moraleLoss = 1 + Math.abs(amountMissing/2);
		Eff moraleLossEffect =new Eff(EffectType.Morale, -moraleLoss);
		EffectPanel moralePanel=new EffectPanel(moraleLossEffect, true);
		Village.get().activate(moraleLossEffect, true);
		float skullSize = 50;
		int gap = 20;
		setSize(Math.max(ranOut.getWidth(), missing.getWidth())+gap*2,ranOut.getHeight() + missing.getHeight() +  moralePanel.getHeight() +skullSize + gap * 5);
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(ranOut);
		l.row(1);
		l.actor(missing);
		l.row(1);
		l.actor(moralePanel);
		l.row(1);
		l.gap(1);
        for(int i = 0; i<Village.MAX_STARVATION; i++){
            ImageActor ia = new ImageActor(Images.starvation, skullSize, skullSize);
            ia.setColor(Village.get().starvation>i? Colours.red:Colours.light);
            l.actor(ia);
            l.gap(1);
        }
		l.row(1);
		l.layoo();
	}

}
