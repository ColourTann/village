package tann.village.screens.gameScreen.panels.roll;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import tann.village.Images;
import tann.village.Main;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.inventory.InventoryPanel;
import tann.village.util.Button;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Layoo;
import tann.village.util.NumberPanel;

public class RollPanel extends Group{
	
	static final int buttonSize = 100;
	static final int BOTTOM_GAP=30;
	static final int WIDTH=300, HEIGHT=buttonSize+BOTTOM_GAP;
	public NumberPanel rollsLeft;
	
	
	public RollPanel() {
		setSize(WIDTH, HEIGHT);
		Button rollButton = new Button(buttonSize, buttonSize, .8f, Images.roll, Colours.dark, new Runnable() {
			@Override
			public void run() {
				GameScreen.get().roll();
			}
		});
		rollButton.setColor(Colours.brown_light);
		Button acceptButton = new Button(buttonSize, buttonSize, .8f, Images.tick, Colours.dark, new Runnable() {
			@Override
			public void run() {
				GameScreen.get().proceed();
			}
		});
		acceptButton.setColor(Colours.green_light);
		
		Layoo l = new Layoo(this);
		rollsLeft = new NumberPanel();
		
		l.actor(rollsLeft);
		l.actor(rollButton);
		l.gap(1);
		l.actor(acceptButton);
		l.layoo();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	
	

}
