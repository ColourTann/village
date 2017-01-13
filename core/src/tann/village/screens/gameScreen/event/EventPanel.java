package tann.village.screens.gameScreen.event;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.effect.EffectPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class EventPanel extends Group{
	
	Event e;
	int dayNumber;
	
	TextBox day, event, description;
	
	public static final int WIDTH = 370;
	public static final int HEIGHT = 320;
	private static final int items_per_row = 3;
	private static final int GAP = 10;
	public EventPanel(Event e, int dayNumber) {
		setSize(WIDTH, HEIGHT);
		this.dayNumber=dayNumber;
		this.e=e;
		day = new TextBox("Day "+dayNumber+" event", Fonts.font, WIDTH-GAP, Align.center);
		event = new TextBox(e.title, Fonts.fontBig, WIDTH-GAP, Align.center);
		description = new TextBox(e.description, Fonts.fontSmall, WIDTH-GAP, Align.left);
		
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(day);
		l.row(1);
		l.actor(event);
		l.row(1);
		l.actor(description);
		
		for(int i=0;i<e.effects.size;i++){
			if(i%items_per_row==0) l.row(1); 
			
			EffectPanel item = new EffectPanel(e.effects.get(i));
			l.actor(item);
			if(i%items_per_row!=2){
				l.abs(10);
			}
			
		}
		l.row(1);
		l.layoo();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}
	

}
