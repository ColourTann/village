package tann.village.screens.gameScreen.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.Cost;
import tann.village.screens.gameScreen.building.BuildingPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.ImageGap;
import tann.village.util.TextBox;

public class CostPanel extends Group{
	
	public Cost cost;
	private static final int  GAP=2, HEIGHT=(int) BuildingPanel.IMAGE_SIZE+0;
	private static final float IMAGE_SIZE=HEIGHT/2;
	
	static final float WIDTH=IMAGE_SIZE*2;
	
	public CostPanel(Cost cost) {
		this.cost=cost;
		setSize(WIDTH+10, HEIGHT);
		BitmapFont font = Fonts.fontSmall;
		float gap = (getHeight()-IMAGE_SIZE*cost.effects.size)/(cost.effects.size+1) ;
		for(int i=0;i<cost.effects.size;i++){
			Effect e = cost.effects.get(i);
			TextBox tb = new TextBox(e.value+"", font, GAP, WIDTH, Align.center);
			tb.setTextColour(Colours.brown_light);
			ImageGap img = new ImageGap(e.type.region, IMAGE_SIZE, IMAGE_SIZE, GAP);
			img.setPosition(0, gap+(img.getHeight()+gap)*i);
			tb.setPosition(img.getWidth()+(getWidth()-img.getWidth())/2-tb.getWidth()/2, img.getY()+img.getHeight()/2-tb.getHeight()/2);
			addActor(tb);
			addActor(img);
		}
		
		
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
