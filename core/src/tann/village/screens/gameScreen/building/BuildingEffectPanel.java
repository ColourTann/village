package tann.village.screens.gameScreen.building;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.panels.EffectPanel;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class BuildingEffectPanel extends Group{

	public static final float WIDTH = (EffectPanel.WIDTH)*2;
	public static final float HEIGHT = 63;
	
	TextBox bonusType;
	BuildingEffect buildingEffect;
	Layoo l;
	public BuildingEffectPanel(BuildingEffect buildingEffect) {
		
		
		this.buildingEffect=buildingEffect;
		setSize(WIDTH, HEIGHT);
		l = new Layoo(this);
		bonusType = new TextBox(buildingEffect.effectType.toString(), Fonts.fontSmall, WIDTH, Align.center);
		l.actor(bonusType);
		switch(buildingEffect.effectType){
		case EveryTurn:
		case Now:
			addEffectPanels();
			break;
		case Passive:
			break;
		case PermanentBonus:
			break;
		default:
			break;
		
		}
		l.layoo();
		
	}
	
	private void addEffectPanels() {
		int totalThisRow = buildingEffect.effects.length;
		for(int i=0;i<totalThisRow;i++){
			l.row(1);
			Effect e =buildingEffect.effects[i];
			EffectPanel item = new EffectPanel(e);
			l.actor(item);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	
}
