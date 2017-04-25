package tann.village.screens.mapScreen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

import tann.village.Images;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.islands.TutorialIsland;

public class Map {

	
	public Map() {
		addIsland(new TutorialIsland(Images.island0, Gdx.graphics.getWidth()/2, 150));
		addIsland(new TutorialIsland(Images.island1, Gdx.graphics.getWidth()/3, 350));
	}
	List<Island> islands = new ArrayList<>();
	public void addIsland(Island i){
		islands.add(i);
	}
}
