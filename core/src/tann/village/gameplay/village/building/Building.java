package tann.village.gameplay.village.building;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Inventory;
import tann.village.gameplay.village.Village;

public class Building {

	public String name;
	public String description;
	public int level;
	public Cost cost;
	public Array<Eff> effects = new Array<>();
	public TextureRegion image = Main.atlas.findRegion("building/hut");

    public Building(String name) {
        this(name,"");
    }

	public Building(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void onBuild() {
        Village.get().activate(effects, true, false);
        Village.getInventory().resetWisps();
        Village.getInventory().showWisps();
	}

    public void setCost(int wood) {
        setCost(wood,0);
    }

    public void setCost(int wood, int food) {
        this.cost = new Cost().food(food).wood(wood);
    }

    public void addEffect(Eff e) {
	    this.effects.add(e);
    }
}
