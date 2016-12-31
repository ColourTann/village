package tann.village.screens.gameScreen.inventory;

import com.badlogic.gdx.scenes.scene2d.Group;

import tann.village.Images;
import tann.village.Main;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Button;
import tann.village.util.Colours;

public class ButtonPanel extends Group{
	
	static final int buttonSize = 100;
	static final int buttonGap = InventoryPanel.ITEM_GAP;
	static final int gap = InventoryPanel.GAP;
	
	public NumberPanel rollsLeft;
	
	public ButtonPanel() {
		Button rollButton = new Button(buttonSize, buttonSize, 8, Images.roll, Colours.dark, new Runnable() {
			@Override
			public void run() {
				GameScreen.get().roll();
			}
		});
		
		Button acceptButton = new Button(buttonSize, buttonSize, 8, Images.tick, Colours.dark, new Runnable() {
			@Override
			public void run() {
				GameScreen.get().proceed();
			}
		});
		
		addActor(rollButton);
		addActor(acceptButton);
		
		rollButton.setColor(Colours.brown_light);
		rollButton.setPosition(gap, buttonSize+buttonGap);
		rollsLeft = new NumberPanel();
		rollButton.addActor(rollsLeft);
		rollsLeft.setPosition(-rollsLeft.getWidth(), rollButton.getHeight()/2-rollsLeft.getHeight()/2);
		rollsLeft.setValue(3);
		
		acceptButton.setPosition(gap, 0);
		acceptButton.setColor(Colours.green_light);
		
		setSize(buttonSize+gap*2, buttonSize*2 + buttonGap);
	}
	
	
	

}
