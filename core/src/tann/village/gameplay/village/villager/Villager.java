package tann.village.gameplay.village.villager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import tann.village.Images;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.village.villager.die.Die;
import tann.village.util.Colours;

public class Villager {

	public static final int MAX_LEVEL = 2;

	Color col;
	public String firstName, lastName;
	public int xp, xpToLevelUp = 10;
	public VillagerType type;
	public Die die;
	public TextureRegion lapel;
	private static final Color[] colours = new Color[]{(Colours.blue_light), (Colours.fate_light), (Colours.green_light), (Colours.brown_light), (Colours.red)};
	
	public Villager(int index) {
		this.type=VillagerType.Villager;
//		this.type=VillagerType.values()[(int)(Math.random()*VillagerType.values().length)];
		this.lapel = Images.lapel;
		setupDie();
		firstName=generateName(true);
		lastName=generateName(false);
		this.col = colours[index%colours.length];
	}

	public void setDie(Die die) {
		if(this.die!=null){
			this.die.destroy();
		}
		this.die=die;
		this.die.villager=this;
		this.type=die.type;
		xpToLevelUp = (type.level+1)*10;
	}
	
	private void setupDie() {
		this.die= new Die(this);
	}

	public void dieRightClicked(){
		GameScreen.get().addVillagerPanel(this);
	}
	
	public void gainXP(int amount){
		this.xp+=amount;
		while(xp>=xpToLevelUp){
			xp-=xpToLevelUp;
			GameScreen.get().addEffect(new Effect(EffectType.LevelUp, 1, die));
			GameScreen.get().villagersToLevelUp.add(this);
		}
	}
	
	private static String[] firstNames;
	private static String[] lastNames;
	
	public static String generateName(boolean first){
		if(firstNames==null){
			firstNames = Gdx.files.internal("names/first.txt").readString().replaceAll("\r", "").split("\n");
			lastNames = Gdx.files.internal("names/last.txt").readString().replaceAll("\r", "").split("\n");
		}
		if(first){
			return firstNames[(int)(Math.random()*firstNames.length)];
		}
		else{
			return lastNames[(int)(Math.random()*lastNames.length)];
		}
	}

	public Color getColour() {
		return col;
	}

	public static VillagerType[] getRandomVillagerTypes(int level, int amount){
		VillagerType[] results = new VillagerType[amount];
		List<VillagerType> availables = new ArrayList<>();
		for(VillagerType t:VillagerType.values()){
			if(t.level==level){
				availables.add(t);
			}
		}
		if(availables.size()<amount) return null;
		Collections.shuffle(availables);
		for(int i=0;i<amount;i++){
			results[i]=availables.remove(0);
		}
		return results;
	}
	
	public enum VillagerType{
		Villager(0), 
		Fisher(1), Musician(1), Mystic(1), Gatherer(1), Chopper(1),
		Farmer(2), Leader(2), FateWeaver(2), Explorer(2), Builder(2);
		public int level;
		VillagerType(int level){
			this.level=level;
		}
	}
}
