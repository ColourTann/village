package tann.village.screens.gameScreen.villager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;
import tann.village.screens.gameScreen.villager.die.Die;

public class Villager {

	public enum VillagerType{
		Villager(0), 
		Fisher(1), Musician(1), Mystic(1), Gatherer(1), Chopper(1),
		Farmer(2), Leader(2), FateWeaver(2), Explorer(2), Builder(2);
		public int level;
		VillagerType(int level){
			this.level=level;
		}
	}

	public static final int MAX_LEVEL = 2;

	
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
	
	String firstName;
	String lastName;
	int xp;
	int xpToLevelUp = 10;
	public VillagerType type;
	public Die die;
	
	
	public Villager() {
//		this.type=VillagerType.Villager;
		this.type=VillagerType.values()[(int)(Math.random()*VillagerType.values().length)];
		setupDie();
		firstName=generateName(true);
		lastName=generateName(false);
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
			GameScreen.get().addEffect(new Effect(EffectType.LevelUp, 1, EffectSource.Dice, die));
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

	
}
