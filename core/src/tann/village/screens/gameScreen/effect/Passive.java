package tann.village.screens.gameScreen.effect;

public class Passive {
	public enum PassiveType{
		ReRoll
	}
	
	public PassiveType type;
	public int value;
	
	public Passive(PassiveType type){
		this(type, 0);
	}
	
	public Passive(PassiveType type, int value){
		this.type=type;
		this.value=value;
	}
	
}
