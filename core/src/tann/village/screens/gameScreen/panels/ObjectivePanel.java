package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.utils.Align;

import tann.village.Images;
import tann.village.gameplay.effect.Cost;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.village.Inventory;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.Button;
import tann.village.util.Colours;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class ObjectivePanel extends InfoPanel{
	
	int currentAmount;
	int amountRequired = 6;
	Cost cost = new Cost(6, 2);
	
	int WIDTH = 270, HEIGHT = 75;
	
	
	public ObjectivePanel() {
		setSize(WIDTH, HEIGHT);
		refresh();
		setBackground(Colours.brown_dark);
	}
	
	private void refresh(){
		clear();
		Layoo l = new Layoo(this);
		CostPanel costPanel = new CostPanel(cost);
		TextBox box = new TextBox(currentAmount+"/"+amountRequired, Fonts.font, WIDTH/2, Align.center);
		
		Runnable r = new Runnable() {public void run() {
			if(!Inventory.get().checkCost(cost)) return;
			Inventory.get().spendCost(cost);
			currentAmount++;
			refresh();
			if(currentAmount==amountRequired){
				GameScreen.get().win();
			}
		}};
		Button butt = new Button(HEIGHT/1.25f, HEIGHT/1.25f, Images.boat_wheel, Colours.brown_light, r);

		
		l.gap(1);
		l.actor(butt);
		l.gap(1);
		l.actor(costPanel);
		l.gap(1);
		l.actor(box);
		l.gap(1);
		l.layoo();
	}
	
	

}
