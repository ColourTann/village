package tann.village.screens.gameScreen.panels.buildingStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.Array;
import tann.village.Main;
import tann.village.gameplay.effect.Eff;
import tann.village.screens.gameScreen.panels.eventStuff.EffectPanel;
import tann.village.util.*;

public class BuildingEffectPanel extends Lay{

	public static final float WIDTH = (EffectPanel.staticWidth());
	public static final float HEIGHT = 120;

	Array<Eff> effects;
	Layoo l;
	public BuildingEffectPanel(Array<Eff> effects) {
		
		
		this.effects=effects;

		layout();
		
	}

	@Override
	public void layout() {
		l = new Layoo(this);
		addEffectPanels();
        l.layoo();
		
	}
	
	private void addEffectPanels() {
	    Eff previous=null;
		for(int i=0;i<effects.size;i++){
			l.row(1);
			Eff e =effects.get(i);
			if(previous==null || !e.effAct.equiv(previous.effAct)){
			 TextBox typeBox = new TextBox(e.effAct.toString(), Fonts.fontSmall, -1, Align.center);
			 l.actor(typeBox);
			 l.absRow(8);
            }
			EffectPanel item = new EffectPanel(e);
			l.actor(item);
			previous = e;
		}
		l.row(1);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
	}
}
