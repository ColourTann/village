package tann.village.screens.gameScreen.event;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.effect.EffectPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.TextBox;

public class EventPanel extends Group{
	
	Event e;
	int dayNumber;
	
	TextBox day, event, description;
	
	public static final int GAP = 10;
	public static final int MAX_WIDTH = 500;
	private static final int items_per_row = 3;
	public EventPanel(Event e, int dayNumber) {
		this.dayNumber=dayNumber;
		this.e=e;
		day = new TextBox("Day "+dayNumber+" event", Fonts.font, GAP, MAX_WIDTH, Align.center);
		event = new TextBox(e.title, Fonts.fontBig, GAP, MAX_WIDTH, Align.center);
		description = new TextBox(e.description, Fonts.fontSmall, GAP, MAX_WIDTH, Align.left);
		
		
		setSize(
				Math.max(day.getWidth(), Math.max(event.getWidth(), description.getWidth())), 
				day.getHeight()+event.getHeight()+description.getHeight()+(e.effects.size+(items_per_row-1))/items_per_row*(EffectPanel.HEIGHT+GAP*2));
		
		day.setPosition(getWidth()/2-day.getWidth()/2, getHeight()-day.getHeight());
		event.setPosition(getWidth()/2-event.getWidth()/2, getHeight()-day.getHeight()-event.getHeight());
		description.setPosition(getWidth()/2-description.getWidth()/2, getHeight()-day.getHeight()-event.getHeight()-description.getHeight());
		
		
		for(int i=0;i<e.effects.size;i++){
			EffectPanel item = new EffectPanel(e.effects.get(i), GAP);
			addActor(item);
			
			int remaining= e.effects.size-i-1; //0
			int indexThisRow=i%items_per_row; //2
			int totalThisRow=remaining+indexThisRow+1; //3
			if(totalThisRow>3)totalThisRow=3;
			
			float minigap = (getWidth()-items_per_row*item.getWidth())/(items_per_row+1);

			float startX = minigap+(getWidth()-totalThisRow*item.getWidth()-(totalThisRow+1)*minigap)/2;
			
			item.setPosition(startX+indexThisRow*(item.getWidth()+minigap), getHeight()-day.getHeight()-event.getHeight()-description.getHeight()-(i/3+1)*(item.getHeight()));
		}
		
		
		
		
		addActor(day);
		addActor(event);
		addActor(description);
		
		
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}
	

}
