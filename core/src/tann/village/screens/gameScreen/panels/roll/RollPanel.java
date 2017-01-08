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
import tann.village.util.NumberPanel;

public class RollPanel extends Group{
	
	static final int buttonSize = 100;
	static final int gap = 10;
	
	public NumberPanel rollsLeft;
	
	
	public RollPanel() {
		Button rollButton = new Button(buttonSize, buttonSize, gap, .8f, Images.roll, Colours.dark, new Runnable() {
			@Override
			public void run() {
				GameScreen.get().roll();
			}
		});
		
		Button acceptButton = new Button(buttonSize, buttonSize, gap, .8f, Images.tick, Colours.dark, new Runnable() {
			@Override
			public void run() {
				GameScreen.get().proceed();
			}
		});
		
		addActor(rollButton);
		addActor(acceptButton);
		
		rollButton.setColor(Colours.brown_light);
		rollsLeft = new NumberPanel();
		rollButton.setPosition(rollsLeft.getWidth(), 0);
		addActor(rollsLeft);
		rollsLeft.setPosition(gap, rollButton.getHeight()/2-rollsLeft.getHeight()/2);
		rollsLeft.setValue(3);
		
		acceptButton.setPosition(buttonSize+rollsLeft.getWidth()-gap, 0);
		acceptButton.setColor(Colours.green_light);
		
		setSize(buttonSize*2+rollsLeft.getWidth(), buttonSize);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	
	

}
