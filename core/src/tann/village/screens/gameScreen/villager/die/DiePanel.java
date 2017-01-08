package tann.village.screens.gameScreen.villager.die;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import tann.village.util.Button;
import tann.village.util.Colours;
import tann.village.util.Draw;

public class DiePanel extends Group{
	
	Die die;
	float width, gap;
	private final float internalGap;
	public DiePanel(Die die, float width, float gap) {
		internalGap = width/100f;
		this.die=die;
		this.gap=gap;
		this.width=width;
		float uWidth = width-gap*2-internalGap*2;
		Array<Button> dieFaces = new Array<>();
		for(int i=0;i<6;i++){
			final Side side = die.sides.get(i);
			Runnable r = new Runnable() {
				public void run() {
					System.out.println(side.effect.type);
				}
			};
			Button butt =new Button((uWidth)/3, (uWidth)/3, 0, side.tr, Colours.transparent, r); 
			butt.setPosition(gap+(butt.getWidth()+internalGap)*(i/2), getHeight()+gap-(butt.getHeight()+internalGap)*(i%2-1));
			dieFaces.add(butt);
			addActor(butt);
		}
		
		setSize(width, dieFaces.get(0).getHeight()*2+internalGap+gap*2);
		
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
//		batch.setColor(Colours.dark);
//		Draw.fillRectangle(batch, getX()+gap, getY()+gap, getWidth()-gap*2, getHeight()-gap*2);
		super.draw(batch, parentAlpha);
	}

}
