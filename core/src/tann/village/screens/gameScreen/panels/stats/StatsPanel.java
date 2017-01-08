package tann.village.screens.gameScreen.panels.stats;

import com.badlogic.gdx.scenes.scene2d.Group;

import tann.village.Main;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Button;
import tann.village.util.Colours;

public class StatsPanel extends Group{

	static final int buttonSize = 100;
	static final int gap = 10;
	
	Button buildingButton;
	
	public StatsPanel() {
		buildingButton = new Button(buttonSize, buttonSize, gap, .8f, Main.atlas.findRegion("hammer"), Colours.dark, new Runnable() {
			public void run() {
				GameScreen.get().openBuildingPanel();
			}
		});
		addActor(buildingButton);
		setSize(buttonSize, buttonSize);
	}
	
}
