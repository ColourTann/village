package tann.village.screens.gameScreen.building;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.effect.CostPanel;
import tann.village.screens.gameScreen.effect.EffectPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.ImageGap;
import tann.village.util.TextBox;

public class BuildingPanel extends Group{

	Building building;
	
	static final int GAP = 10;
	static final float WIDTH = EffectPanel.WIDTH+GAP*2+30;
	public static final float IMAGE_SIZE= 50;
	private int outerGap;
	public BuildingPanel(Building building, int gap) {
		outerGap = gap;
		this.building=building;
		TextBox title = new TextBox(building.name, Fonts.fontSmall, GAP/2, WIDTH, Align.center);
		ImageGap image = new ImageGap(building.image, IMAGE_SIZE, IMAGE_SIZE, GAP);
		CostPanel cp = new CostPanel(building.cost);
		addActor(cp);
		image.setBackground(Colours.dark);
		
		for(int i=0;i<building.buildingEffects.size;i++){
			BuildingEffectPanel bep =new BuildingEffectPanel(building.buildingEffects.get(building.buildingEffects.size-i-1));
			addActor(bep);
			float y = 0;
			if(building.buildingEffects.size==1){
				y = bep.getHeight()/2;
			}
			else{
				y=i*bep.getHeight();
			}
			bep.setPosition(outerGap + WIDTH/2-bep.getWidth()/2, outerGap +y);
		}

		addActor(title);
		addActor(image);
		addActor(cp);
		
		setSize(WIDTH+outerGap*2, title.getHeight()+image.getHeight()+BuildingEffectPanel.HEIGHT*2+outerGap*2);
		
		float gapp = (WIDTH-image.getWidth()-cp.getWidth())/3;
		image.setPosition(outerGap+gapp, outerGap+BuildingEffectPanel.HEIGHT*2);
		cp.setPosition(outerGap+gapp*2+image.getWidth(), image.getY()+image.getHeight()/2-cp.getHeight()/2);
		title.setPosition(outerGap+WIDTH/2-title.getWidth()/2, outerGap+BuildingEffectPanel.HEIGHT*2+image.getHeight());
		
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		int border = 2;
		batch.setColor(Colours.brown_light);
		Draw.fillRectangle(batch, getX()+outerGap-border, getY()+outerGap-border, getWidth()-outerGap*2+border*2, getHeight()-outerGap*2+border*2);
		batch.setColor(Colours.brown_dark);
		Draw.fillRectangle(batch, getX()+outerGap, getY()+outerGap, getWidth()-outerGap*2, getHeight()-outerGap*2);
		super.draw(batch, parentAlpha);
	}
	
}
