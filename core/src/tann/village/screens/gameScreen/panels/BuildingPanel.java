package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.gameplay.village.building.Building;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.ImageActor;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class BuildingPanel extends Group{

	public Building building;
	
	public static final float WIDTH = 150, HEIGHT=240;
	public static final float IMAGE_SIZE= 60;
	private static final int border = 3;
	public BuildingPanel(Building building) {
		setSize(WIDTH, HEIGHT);
		setBuilding(building);
	}
	
	public BuildingPanel() {
		setSize(WIDTH, HEIGHT);
	}
	
	public void clearBuilding(){
		clearChildren();
		this.building=null;
	}
	
	public void setBuilding(Building building){
		clearBuilding();
		this.building=building;
		Layoo l = new Layoo(this);
		l.absRow(10);
		l.actor(new TextBox(building.name, Fonts.fontSmall, WIDTH, Align.center));
		l.absRow(10);
		l.gap(1);
		ImageActor image = new ImageActor(building.image, IMAGE_SIZE, IMAGE_SIZE);
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
		
		batch.setColor(highlightColour);
		Draw.fillRectangle(batch, getX()-border, getY()-border, getWidth()+border*2, getHeight()+border*2);
		batch.setColor(Colours.brown_dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}

	Color highlightColour = Colours.brown_light;
	public void highlight(boolean b) {
		highlightColour = b?Colours.light:Colours.brown_light;
	}
	
}
