package tann.village.screens.gameScreen.villager;

import com.badlogic.gdx.Gdx;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;

public class Villager {

	public enum VillagerType{Villager, Fisher, Leader, FateWeaver}
	
	String firstName;
	String lastName;
	int xp;
	int xpToLevelUp = 10;
	VillagerType type;
	public Die die;
	
	
	public Villager() {
		
		this.type=VillagerType.Villager;
		
		if(true){
//			this.type=VillagerType.values()[(int)(Math.random()*VillagerType.values().length)];
		}
		
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
			System.out.println("levelup!");
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
