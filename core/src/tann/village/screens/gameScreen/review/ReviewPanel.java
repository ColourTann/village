package tann.village.screens.gameScreen.review;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.EffectPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.TextBox;

public class ReviewPanel extends Group{
	
	static final int items_per_row=3;
	static final int gap = 10;
	static final int itemWidth = (int)EffectPanel.WIDTH+gap*2;
	static final int itemHeight = (int)EffectPanel.HEIGHT+gap*2;
	static final int width = itemWidth*items_per_row;
	int day;
	
	TextBox title;
	
	TextBox diceTitle;
	TextBox buildingsTitle;
	TextBox upkeepTitle;
	public ReviewPanel(int day) {
		this.day=day;
		title = new TextBox("Day "+day+" review", Fonts.font, gap, width-gap*2, Align.center);
		addActor(title);
		
		
	}
	
	Array<EffectPanel> diceEffects = new Array<>();
	Array<EffectPanel> buildingEffects = new Array<>();
	Array<EffectPanel> upkeepEffects = new Array<>();
	
	public void addItem(Effect effect){
		if(effect.value==0) return;
		Array<EffectPanel> array=null;
		switch(effect.source){
		case Building:
			array = buildingEffects;
			break;
		case Dice:
			array = diceEffects;
			break;
		case Upkeep:
			array = upkeepEffects;
			break;
		default:
			return;
		}
		for(EffectPanel ep:array){
			if(ep.effect.type==effect.type && ep.effect.source==effect.source){
				ep.changeValue(effect.value);
				return;
			}
		}
		array.add(new EffectPanel(effect, gap));
	}
	
	public void build(){
		float y = 0;
		if(upkeepEffects.size>0){
			y=addItems("Upkeep", upkeepEffects, y);
		}
		if(buildingEffects.size>0){
			y = addItems("Buildings", buildingEffects, y);
		}
		if(diceEffects.size>0){
			y = addItems("Dice", diceEffects, y);
		}
		title.setPosition(width/2-title.getWidth()/2, (y));
		y+= title.getHeight();
		setSize(width, y);
	}

	private float addItems(String title, Array<EffectPanel> items, float currentY){
		
		
		for(int i=items.size-1;i>=0;i--){
			EffectPanel item = items.get(i);
			addActor(item);
			
			int remaining= items.size-i-1; //0
			int indexThisRow=i%items_per_row; //2
			int totalThisRow=remaining+indexThisRow+1; //3
			if(totalThisRow>3)totalThisRow=3;
			
			float minigap = (width-items_per_row*item.getWidth())/(items_per_row+1);

			float startX = minigap+(width-totalThisRow*item.getWidth()-(totalThisRow+1)*minigap)/2;
			
			item.setPosition(startX+indexThisRow*(item.getWidth()+minigap), currentY);
			if(i%3==0){
				currentY += itemHeight;
			}
		}
		TextBox tb = new TextBox(title, Fonts.fontSmall, 5, width, Align.center);
		addActor(tb);
		tb.setPosition(width/2-tb.getWidth()/2, currentY);
		currentY+=tb.getHeight();
		return currentY;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}
}
