package tann.village.screens.gameScreen.review;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.Effect.EffectType;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.TextBox;

public class ReviewPanel extends Group{
	
	static final int gap = 14;
	static final int items_per_row=3;
	static final int width = ReviewItem.width*items_per_row+gap*(items_per_row+1);
	int day;
	Array<ReviewItem> items = new Array<>();
	
	TextBox title;
	public ReviewPanel(int day) {
		this.day=day;
		title = new TextBox("Day "+day+" review", Fonts.font, gap, width-gap*2, Align.center);
		addActor(title);
		
		
	}
	
	public void addItem(EffectType type, int value){
		for(ReviewItem i:items){
			if(i.type==type){
				i.changeValue(value);
				return;
			}
		}
		items.add(new ReviewItem(type, value));
	}
	
	public void build(){
		setSize(width, (float) (Math.ceil(items.size/(float)items_per_row)*ReviewItem.height+gap*(items_per_row)+title.getHeight()));
		for(int i=0;i<items.size;i++){
			ReviewItem item = items.get(i);
			addActor(item);
			
			int weird = items.size%3;
			int remaining= items.size-i-1;
			int mod = i%items_per_row;
			int x= 0;
			if(remaining+mod+1<items_per_row){
				int start =(width - ReviewItem.width*weird + gap *(weird-1))/2;
				x = start + mod*(ReviewItem.width+gap);
			}
			else{
				x=gap+(i%items_per_row)*(ReviewItem.width+gap);
			}
			item.setPosition(x, getHeight()-title.getHeight()-(i/items_per_row+1)*(ReviewItem.height+gap));
		}
		title.setPosition(getWidth()/2-title.getWidth()/2, getHeight()-title.getHeight());
	}

	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}
}
