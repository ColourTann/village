package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.utils.Align;

import tann.village.Images;
import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.island.objective.Objective;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.village.Inventory;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.Button;
import tann.village.util.Colours;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class ObjectivePanel extends InfoPanel{
	

	int WIDTH = 270, HEIGHT = 75;
	
	Objective obj;
	public ObjectivePanel(Objective obj) {
		setSize(WIDTH, HEIGHT);
		this.obj=obj;
        setBackground(Colours.dark);
        refresh();
	}
	
	public void refresh(){
		clear();
		Layoo l = new Layoo(this);

		TextBox title  = new TextBox("Objective", Fonts.fontSmall, WIDTH/2, Align.center);

        TextBox objText = new TextBox(obj.getTitleString(), Fonts.fontSmall, WIDTH/2, Align.center);
        TextBox progress = new TextBox(obj.getProgressString(), Fonts.fontSmall, WIDTH/2, Align.center);

        l.row(1);
        l.actor(title);
        l.row(1);
        l.add(1,objText, 1, progress, 1);
        l.row(1);
		l.layoo();
	}
	
	

}
