package tann.village.screens.gameScreen.panels.buildingStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.Array;
import tann.village.Main;
import tann.village.gameplay.effect.Eff;
import tann.village.screens.gameScreen.panels.eventStuff.EffectPanel;
import tann.village.util.Fonts;
import tann.village.util.Lay;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class BuildingEffectPanel extends Lay{

	public static final float WIDTH = (EffectPanel.staticWidth())*2;
	public static final float HEIGHT = EffectPanel.staticHeight();
	
	TextBox bonusType;
	Array<Eff> effects;
	Layoo l;
	public BuildingEffectPanel(Array<Eff> effects) {
		
		
		this.effects=effects;

		layout();
		
	}

	@Override
	public void layout() {
		l = new Layoo(this);
		bonusType = new TextBox(effects.get(0).effAct.toString(), Fonts.fontSmall, WIDTH, Align.center);
		setSize(WIDTH, EffectPanel.staticHeight()*effects.size+ Main.h(.5f)*(effects.size+1)+bonusType.getHeight());
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
