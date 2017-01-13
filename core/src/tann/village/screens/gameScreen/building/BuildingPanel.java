package tann.village.screens.gameScreen.building;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.effect.CostPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.ImageGap;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class BuildingPanel extends Group{

	Building building;
	
	static final float WIDTH = 150, HEIGHT=240;;
	public static final float IMAGE_SIZE= 50;
	public BuildingPanel(Building building) {
		setSize(WIDTH, HEIGHT);
		this.building=building;
		Layoo l = new Layoo(this);
		l.absRow(10);
		l.actor(new TextBox(building.name, Fonts.fontSmall, WIDTH, Align.center));
		l.absRow(10);
		l.gap(1);
		ImageGap image = new ImageGap(building.image, IMAGE_SIZE, IMAGE_SIZE);
		image.setBackground(Colours.dark);
		l.actor(image);
		l.gap(1);
		l.actor(new CostPanel(building.cost));
		l.gap(1);
		for(int i=0;i<building.buildingEffects.size;i++){
			l.row(1);
			BuildingEffectPanel bep =new BuildingEffectPanel(building.buildingEffects.get(building.buildingEffects.size-i-1));
			l.actor(bep);
		}
		l.row(1);
		l.layoo();
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		int border = 2;
		batch.setColor(Colours.brown_light);
		Draw.fillRectangle(batch, getX()-border, getY()-border, getWidth()+border*2, getHeight()+border*2);
		batch.setColor(Colours.brown_dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}
	
}
