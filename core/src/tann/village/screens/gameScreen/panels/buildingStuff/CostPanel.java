package tann.village.screens.gameScreen.panels.buildingStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.gameplay.effect.Cost;
import tann.village.gameplay.effect.Eff;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.ImageActor;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class CostPanel extends Group{
	
	public Cost cost;
	private static final int HEIGHT=(int) tann.village.screens.gameScreen.panels.buildingStuff.BuildingPanel.IMAGE_SIZE+0;
	private static final float IMAGE_SIZE=HEIGHT/3;	
	
	static final float WIDTH=IMAGE_SIZE*2.5f;
	
	public CostPanel(Cost cost) {
		this.cost=cost;
		setSize(WIDTH, HEIGHT);
		BitmapFont font = Fonts.fontSmall;
		Layoo l = new Layoo(this);
		for(int i=0;i<cost.effects.size;i++){
			
			Eff e = cost.effects.get(i);
			TextBox tb = new TextBox(e.value+"", font,  WIDTH, Align.center);
			
			tb.setTextColour(Colours.brown_light);
			ImageActor img = new ImageActor(e.type.region, IMAGE_SIZE, IMAGE_SIZE);
			
			l.row(1);
			l.gap(1);
			l.actor(img);
			l.gap(1);
			l.actor(tb);
			l.gap(1);
			
		}
		l.row(1);
		l.layoo();
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		float g = 0;
		batch.setColor(Colours.light);
		Draw.fillActor(batch, this);
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX()+g, getY()+g, getWidth()-g*2, getHeight()-g*2);
		super.draw(batch, parentAlpha);
	}

}
