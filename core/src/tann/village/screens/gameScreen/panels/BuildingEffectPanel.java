package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.village.building.BuildingEffect;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class BuildingEffectPanel extends Group{

	public static final float WIDTH = (EffectPanel.staticWidth())*2;
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
        addEffectPanels();
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
