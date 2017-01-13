package tann.village.screens.gameScreen.panels.construction;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.building.Building;
import tann.village.screens.gameScreen.building.BuildingEffect;
import tann.village.screens.gameScreen.building.BuildingEffect.BuildingEffectType;
import tann.village.screens.gameScreen.building.BuildingEffectPanel;
import tann.village.screens.gameScreen.building.BuildingPanel;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.TextBox;

public class ConstructionPanel extends Group{

	TextBox buildingsText;
	public ConstructionPanel() {
		buildingsText = new TextBox("Buildings", Fonts.font,-1, Align.center);
		addActor(buildingsText);
		setSize(buildingsText.getWidth(), buildingsText.getHeight());
		float panWidth=0, panHeight=0;
		for(int y=0;y<2;y++){
		for(int i=0;i<3;i++){
			BuildingPanel bpan = new BuildingPanel(Building.random());
			addActor(bpan);
			bpan.setPosition(bpan.getWidth()*i, y*bpan.getHeight());
			panWidth=bpan.getWidth();
			panHeight=bpan.getHeight();
		}
		}
		setSize(panWidth*3, panHeight*2);
		
		
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}
	
}
