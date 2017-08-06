package tann.village.gameplay.village.building;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Village;

public class Building {

	public String name;
	public String description;
	public int level;
	public Cost cost;
	public Array<Eff> effects;
	public TextureRegion image = Main.atlas.findRegion("building/hut");

	public Building(){this.effects = new Array<>();}

	public Building(String name, String description, int level, Cost cost, Array<Eff> effects) {
		this.name=name;
		this.description=description;
		this.level=level;
		this.cost=cost;
		this.effects = effects;
	}
	
	public void onBuild() {
        Village.getInventory().resetWisps();
        Village.getInventory().showWisps();
	}

	public void upkeep() {
		for(Eff e:effects){
		    switch(e.effAct.type){

                case NOW:
                    break;
                case IN_TURNS:
                    break;
                case FOR_TURNS:
                    e.activateAsNow();
                    break;
                case UPKEEP:
                    break;
                case PASSIVE:
                    break;
            }
		}
	}
}
