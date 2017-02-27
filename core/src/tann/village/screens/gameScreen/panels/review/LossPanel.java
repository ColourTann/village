package tann.village.screens.gameScreen.panels.review;

import com.badlogic.gdx.utils.Align;

import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class LossPanel extends InfoPanel{
	private static float WIDTH=500, HEIGHT=500;
	public enum LossReason{Morale, Other}
	public LossPanel(LossReason reason, int turnsSurvived) {
		setSize(WIDTH, HEIGHT);
		String lossText="";
		switch(reason){
		case Morale:
			lossText="The village is starving and something something bad stuff you lose";
			break;
		default:
			break;
		}
		TextBox loss = new TextBox(lossText, Fonts.font, WIDTH, Align.center);
		TextBox turns = new TextBox("You survived "+turnsSurvived+" turns", Fonts.font, WIDTH, Align.center);
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(loss);
		l.row(1);
		l.actor(turns);
		l.row(1);
		l.layoo();
	}
}
