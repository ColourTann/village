package tann.village.screens.gameScreen.building;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.effect.EffectPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.ImageGap;
import tann.village.util.TextBox;

public class BuildingPanel extends Group{

	Building building;
	
	static final int GAP = 10;
	static final float WIDTH = EffectPanel.WIDTH*2+GAP*4;
	public BuildingPanel(Building building) {
		this.building=building;
		TextBox title = new TextBox(building.name, Fonts.font, GAP, WIDTH, Align.center);
		ImageGap image = new ImageGap(building.image, WIDTH/2, WIDTH/2, GAP);
		BuildingPanel bPanl = new BuildingPanel(building);
		
		addActor(bPanl);
		addActor(title);
		addActor(image);
		
		setSize(WIDTH, title.getHeight()+image.getHeight()+bPanl.getHeight());
		
		bPanl.setPosition(WIDTH/2-bPanl.getWidth()/2, 0);
		
		image.setPosition(WIDTH/2-image.getWidth()/2, bPanl.getHeight());
		
		title.setPosition(WIDTH/2-title.getWidth()/2, bPanl.getHeight()+image.getHeight());
		
		
		
		
		
		
		
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.blue_dark);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}
	
}
