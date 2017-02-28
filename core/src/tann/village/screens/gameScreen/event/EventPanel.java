package tann.village.screens.gameScreen.event;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.panels.EffectPanel;
import tann.village.screens.gameScreen.panels.inventory.UpkeepPanel;
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
	private static final int items_per_row = 3;
	private static final int GAP = 30;
	public EventPanel(Event e, int dayNumber) {
		int height=0;
		
		this.dayNumber=dayNumber;
		this.e=e;
		day = new TextBox("Event", Fonts.font, WIDTH-GAP, Align.center);
		height += day.getHeight();
		event = new TextBox(e.title, Fonts.fontBig, WIDTH-GAP, Align.center);
		height += event.getHeight();
		description = new TextBox(e.description, Fonts.fontSmall, WIDTH-GAP, Align.left);
		height += description.getHeight();
		
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(day);
		
		l.row(1);
		l.actor(event);
		l.row(1);
		l.actor(description);
		
		for(int i=0;i<e.effects.size;i++){
			if(i%items_per_row==0) {
				l.row(1);
				
			}
			Effect effect = e.effects.get(i);
			if(effect.source==EffectSource.Upkeep){
				UpkeepPanel upkeepShow = new UpkeepPanel();
				upkeepShow.addEffect(effect);
				upkeepShow.build();
				if(i%items_per_row==0) height += upkeepShow.getHeight();
				l.actor(upkeepShow);
			}
			else{
				EffectPanel item = new EffectPanel(effect);
				if(i%items_per_row==0) height += item.getHeight();
				l.actor(item);
			}
			if(i%items_per_row!=2){
				l.abs(10);
			}
			
		}
		l.row(1);
		setSize(WIDTH, height + 80);
		l.layoo();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}
}
