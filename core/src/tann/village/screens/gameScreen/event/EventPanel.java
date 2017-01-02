package tann.village.screens.gameScreen.event;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.review.ReviewItem;
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
	private static final int items_per_row = 2;
	public EventPanel(Event e, int dayNumber) {
		this.dayNumber=dayNumber;
		this.e=e;
		day = new TextBox("Day "+dayNumber, Fonts.fontBig, GAP, MAX_WIDTH, Align.center);
		event = new TextBox(e.title, Fonts.fontBig, GAP, MAX_WIDTH, Align.center);
		description = new TextBox(e.description, Fonts.fontSmall, GAP, MAX_WIDTH, Align.left);
		
		setSize(
				Math.max(day.getWidth(), Math.max(event.getWidth(), description.getWidth())), 
				day.getHeight()+event.getHeight()+description.getHeight()+(e.effects.size+1)/items_per_row*(ReviewItem.height+GAP));
		
		day.setPosition(getWidth()/2-day.getWidth()/2, getHeight()-day.getHeight());
		event.setPosition(getWidth()/2-event.getWidth()/2, getHeight()-day.getHeight()-event.getHeight());
		description.setPosition(getWidth()/2-description.getWidth()/2, getHeight()-day.getHeight()-event.getHeight()-description.getHeight());
		
		
		
		for(int i=0;i<e.effects.size;i++){
			ReviewItem item = new ReviewItem(e.effects.get(i));
			addActor(item);
			
			int weird = e.effects.size%items_per_row;
			int remaining= e.effects.size-i-1;
			int mod = i%items_per_row;
			int x= 0;
			if(remaining+mod+1<items_per_row){
				int start =GAP+(int) ((getWidth() - ReviewItem.width*weird + GAP *(weird-1))/2);
				x = start + mod*(ReviewItem.width+GAP);
			}
			else{
				x=GAP+(i%items_per_row)*(ReviewItem.width+GAP);
			}
			item.setPosition(x, getHeight()-day.getHeight()-event.getHeight()-description.getHeight()-(i/items_per_row+1)*(ReviewItem.height+GAP));
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
