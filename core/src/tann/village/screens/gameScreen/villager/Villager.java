package tann.village.screens.gameScreen.villager;

import com.badlogic.gdx.Gdx;

import tann.village.screens.gameScreen.GameScreen;

public class Villager {

	public enum VillagerType{Villager, Fisher}
	
	String firstName;
	String lastName;
	int xp;
	VillagerType type;
	public Die die;
	
	
	public Villager() {
		this.type=Math.random()>.5?VillagerType.Villager:VillagerType.Fisher;
		setupDie();
		firstName=generateName(true);
		lastName=generateName(false);
	}

	private void setupDie() {
		this.die= new Die(this, type);
	}

	public void dieRightClicked(){
		GameScreen.get().addVillagerPanel(this);
	}
	
	public void gainXP(int amount){
		this.xp+=amount;
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
