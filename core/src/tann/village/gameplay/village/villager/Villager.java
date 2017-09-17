package tann.village.gameplay.village.villager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.utils.Array;
import tann.village.Images;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.village.AddSub;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.phase.LevelupPhase;
import tann.village.gameplay.village.phase.Phase;
import tann.village.gameplay.village.villager.die.Side;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.gameplay.village.villager.die.Die;
import tann.village.screens.gameScreen.panels.villagerStuff.VillagerIcon;
import tann.village.util.Colours;

import java.util.Map;

public class Villager {

	public static final int MAX_LEVEL = 2;
    public static Array<VillagerType> basicVillagerTypes = new Array<>(new VillagerType[]{
            VillagerType.Villager,
            VillagerType.Fisher,
            VillagerType.SongKeeper,
            VillagerType.Mystic,
            VillagerType.Gatherer,
            VillagerType.Chopper,
            VillagerType.Farmer,
            VillagerType.Leader,
            VillagerType.FateWeaver,
            VillagerType.Explorer,
            VillagerType.Builder
    });

    Color col;
	public String firstName, lastName;
	public int xp;
	public static final int xpToLevelUp = 3;
	public VillagerType type;
	public Die die;

	private static final Color[] colours = new Color[]{(Colours.blue_light), (Colours.fate_light), (Colours.green_light), (Colours.brown_light), (Colours.red)};
	
	public Villager(int index) {
		this.type=VillagerType.Villager;
//		this.type=VillagerType.values()[(int)(Math.random()*VillagerType.values().length)];
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
        getIcon().layout();
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
            Village.get().pushPhase(new LevelupPhase(this));
		}
        this.potentialXp=0;
        getIcon().layout();
	}

	public int potentialXp;


	private static Array<String> firstNames;
	private static Array<String> lastNames;
	
	public static String generateName(boolean first){
		if(firstNames==null||firstNames.size==0) {
            firstNames = new Array<>(Gdx.files.internal("names/first.txt").readString().replaceAll("\r", "").split("\n"));
        }
        if(lastNames==null||lastNames.size==0){
			lastNames = new Array<>(Gdx.files.internal("names/last.txt").readString().replaceAll("\r", "").split("\n"));
		}
		if(first){
            String result = firstNames.random();
            firstNames.removeValue(result, true);
            return result;
		}
		else{
            String result = lastNames.random();
            lastNames.removeValue(result, true);
            return result;
		}
	}

	public Color getColour() {
		return col;
	}

	VillagerIcon icon;
    public VillagerIcon getIcon() {
        if(icon==null)icon = new VillagerIcon(this);
        return icon;
    }

    public void setDelta(Map<Object, AddSub> deltaMap) {
        AddSub as = deltaMap.get(this);
        potentialXp=0;
        if(as!=null){
            potentialXp=as.getTotal();
        }
        getIcon().layout();
    }


    public enum VillagerType{
//		Villager(0,"no description maybe?",                     Images.lapel,    Side.food2, Side.food1, Side.wood1, Side.wood1, Side.brain, Side.skull),
        Villager(0,"no description maybe?",                     Images.lapel,    Side.skull, Side.food1, Side.food1, Side.wood1, Side.wood1, Side.brain),
        // 1
		Fisher(1, "Catch lots of fish!.. on a good day",        Images.lapel1,   Side.food3, Side.food2, Side.food1, Side.brain, Side.skull, Side.skull),
        SongKeeper(1, "Remember the ancients",                  Images.lapel1,   Side.morale1, Side.morale1, Side.food1, Side.food1, Side.wood1, Side.brain),
        Mystic(1, "Rituals of the stone",                       Images.lapel1,   Side.fate1, Side.fate1, Side.food1, Side.brain, Side.skull, Side.brain),
        Gatherer(1, "Be careful whilst foraging!",              Images.lapel1,   Side.food2, Side.wood2, Side.food2, Side.food1, Side.skull, Side.brain),
        Chopper(1, "Tok tok tok",                               Images.lapel1,   Side.wood2, Side.wood2, Side.wood1, Side.wood1, Side.food1, Side.brain),
        Digger(1, "Have to find the gems",                      Images.lapel2,   Side.gem1, Side.skull, Side.skull, Side.skull, Side.skull, Side.brain),
        // 2
		Farmer(2, "Grow crops to keep the village fed",         Images.lapel2,   Side.food3, Side.food3, Side.food2, Side.wood2, Side.food1, Side.brain),
        Leader(2, "Strong leader listens to all",               Images.lapel2,   Side.morale2, Side.morale1, Side.morale1, Side.wood2, Side.brain, Side.brain),
        FateWeaver(2, "Appease the gods for a better future",   Images.lapel2,   Side.fate2, Side.fate2, Side.fate1, Side.skull, Side.skull, Side.brain),
        Explorer(2, "Search the island for opportunity!",       Images.lapel2,   Side.fate1, Side.morale1, Side.wood2, Side.food2, Side.food3, Side.brain),
        ShineEye(2, "The gems call to me",                      Images.lapel2,   Side.gem1, Side.gem1, Side.skull, Side.skull, Side.skull, Side.brain),
        Builder(2, "Construct a strong village",                Images.lapel2,   Side.wood3, Side.wood3, Side.wood2, Side.wood2, Side.wood1, Side.brain);
		public int level;
		public String description;
		public Side[] sides;
        public TextureRegion lapel;

        VillagerType(int level, String description, TextureRegion lapel, Side... sides){
		    if(sides.length!=6){
		        System.err.println("side error making "+this);
            }
            this.lapel = lapel;
		    this.level=level;
            this.description=description;
            this.sides=sides;
        }
	}
}
