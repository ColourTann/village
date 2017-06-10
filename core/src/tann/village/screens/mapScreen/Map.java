package tann.village.screens.mapScreen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

import tann.village.Images;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.islands.levels.GemIsland;
import tann.village.gameplay.island.islands.levels.TutorialIsland;
import tann.village.gameplay.island.islands.levels.WeatherIsland;

public class Map {

	
	public Map() {
		addIsland(new TutorialIsland(Images.island0, Gdx.graphics.getWidth()/2, 250));
        addIsland(new WeatherIsland(Images.island1, Gdx.graphics.getWidth()/3, 450));
        addIsland(new GemIsland(Images.island2, Gdx.graphics.getWidth()/3*2, 600));
	}
	List<Island> islands = new ArrayList<>();
	public void addIsland(Island i){
		islands.add(i);
	}
}
