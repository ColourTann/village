package tann.village.gameplay.village;

import java.util.ArrayList;
import java.util.List;

import tann.village.gameplay.village.building.Building;
import tann.village.screens.gameScreen.panels.RollPanel;

public class Village {
	
	private List<Building> buildings  = new ArrayList<>();
	private static Village self;
	private RollPanel panel;

	public static Village get(){
		if(self==null){
			self = new Village();
			self.setup();
		}
		return self;
	}

	public void setup(){
    }

	public void upkeep(){
		for(Building b:buildings){
			b.upkeep();
		}
	}

	public void addBuilding(Building b) {
		buildings.add(b);
	}
	
}
