package tann.village.gameplay.village;

import java.util.ArrayList;
import java.util.List;

import tann.village.gameplay.village.building.Building;

public class Village {
	
	private List<Building> buildings  = new ArrayList<>();
	private static Village self;
	
	public static Village get(){
		if(self==null){
			self = new Village();
		}
		return self;
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
