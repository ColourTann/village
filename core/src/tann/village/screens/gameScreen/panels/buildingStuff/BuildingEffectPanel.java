package tann.village.screens.gameScreen.panels.buildingStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.effect.Eff;
import tann.village.screens.gameScreen.panels.eventStuff.EffectPanel;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class BuildingEffectPanel extends Group{

	public static final float WIDTH = (EffectPanel.staticWidth())*2;
	public static final float HEIGHT = 63;
	
	TextBox bonusType;
	Array<Eff> effects;
	Layoo l;
	public BuildingEffectPanel(Array<Eff> effects) {
		
		
		this.effects=effects;
		setSize(WIDTH, HEIGHT);
		l = new Layoo(this);
		bonusType = new TextBox(effects.get(0).effAct.type.toString(), Fonts.fontSmall, WIDTH, Align.center);
		l.actor(bonusType);
        addEffectPanels();
        l.layoo();
		
	}
	
	private void addEffectPanels() {
		int totalThisRow = effects.size;
		for(int i=0;i<totalThisRow;i++){
			l.row(1);
			Eff e =effects.get(i);
			EffectPanel item = new EffectPanel(e);
			l.actor(item);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	
}
