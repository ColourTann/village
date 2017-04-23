package tann.village.gameplay.village.villager.die;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.panels.Die;
import tann.village.util.Button;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Layoo;

public class DiePanel extends Group{
	
	Die die;
	static final float WIDTH=300, HEIGHT = 180;
	static final float BUTTON_SIZE=80;
	static final float BUTTON_GAP= 10;
	public DiePanel(Die die, float width) {
		setSize(WIDTH, HEIGHT);
		Layoo l = new Layoo(this);
		this.die=die;
		Array<Button> dieFaces = new Array<>();
		for(int i=0;i<6;i++){
			if(i==3){
				l.absRow(BUTTON_GAP);
			}
			
			final Side side = die.sides.get(i);
			Runnable r = new Runnable() {
				public void run() {
					System.out.println("magpie");
				}
			};
			Button butt =new Button(BUTTON_SIZE, BUTTON_SIZE, side.tr[0], Colours.transparent, r); 
			dieFaces.add(butt);
			l.actor(butt);
			if(i%3!=2){
				l.abs(BUTTON_GAP);
			}
		}
		l.layoo();
		
		
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
