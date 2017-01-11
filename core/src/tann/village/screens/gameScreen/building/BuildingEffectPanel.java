package tann.village.screens.gameScreen.building;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.EffectPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.TextBox;

public class BuildingEffectPanel extends Group{

	public static final int GAP = 4;
	public static final float WIDTH = (EffectPanel.WIDTH+GAP*2)*2;
	public static final float HEIGHT = TextBox.fontHeights.get(Fonts.fontSmall) + EffectPanel.HEIGHT+GAP*3;
	
	TextBox bonusType;
	BuildingEffect buildingEffect;
	public BuildingEffectPanel(BuildingEffect buildingEffect) {
		this.buildingEffect=buildingEffect;
		setSize(WIDTH, HEIGHT);
		bonusType = new TextBox(buildingEffect.effectType.toString(), Fonts.fontSmall, GAP, WIDTH, Align.center);
		addActor(bonusType);
		bonusType.setPosition(getWidth()/2-bonusType.getWidth()/2, getHeight()-bonusType.getHeight());
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
		
	}
	
	private void addEffectPanels() {
		int items_per_row = 2;
		int totalThisRow = buildingEffect.effects.length;
		for(int i=0;i<totalThisRow;i++){
			Effect e =buildingEffect.effects[i];
			EffectPanel item = new EffectPanel(e, GAP);
			System.out.println(totalThisRow);
			float minigap = (WIDTH-totalThisRow*item.getWidth())/(totalThisRow+1);
			float startX = minigap;
			addActor(item);
			item.setPosition(startX + (minigap+item.getWidth())*i, 0);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	
}
