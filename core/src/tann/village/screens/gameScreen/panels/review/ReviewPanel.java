package tann.village.screens.gameScreen.panels.review;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.gameplay.effect.Effect;
import tann.village.screens.gameScreen.panels.eventStuff.EffectPanel;
import tann.village.screens.gameScreen.panels.eventStuff.EventPanel;
import tann.village.util.*;

public class ReviewPanel extends Lay{
	
	static final int items_per_row=3;
	static final int itemWidth = (int)EffectPanel.staticWidth();
	static final int itemHeight = (int)EffectPanel.staticHeight();
	public static final float SMALL_GAP=10;
	int day;
	
	TextBox title;
	
	TextBox diceTitle;
	TextBox buildingsTitle;
	TextBox upkeepTitle;
	public ReviewPanel(int day) {
		this.day=day;
	}

    @Override
    public void layout() {
        build();
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
		array.add(new EffectPanel(effect));
	}
	Layoo l;
	public void build(){
	    clearChildren();
	    float width = EffectPanel.staticWidth() * 3 + Main.w(10);
		float height = 0;
		l = new Layoo(this);
		l.row(2);
		title = new TextBox("Day "+day+" review", Fonts.font, width, Align.center);
		height += title.getHeight();
		l.actor(title);
		if(upkeepEffects.size>0){
			addItems("Upkeep", upkeepEffects);
			height += ((upkeepEffects.size+2)/3)*EffectPanel.staticHeight() + Fonts.fontSmall.getLineHeight() + 20;
		}
		if(buildingEffects.size>0){
			addItems("Buildings", buildingEffects);
			height += ((buildingEffects.size+2)/3)*EffectPanel.staticHeight() + Fonts.fontSmall.getLineHeight() + 20;
		}
		if(diceEffects.size>0){
			addItems("Dice", diceEffects);
			height += ((diceEffects.size+2)/3)*EffectPanel.staticHeight() + Fonts.fontSmall.getLineHeight() + 20;
		}
		l.row(1);
		setSize(width, height + Main.h(10));
		l.layoo();
	}

	private void addItems(String title, Array<EffectPanel> items){
		
		l.row(3);
		TextBox tb = new TextBox(title, Fonts.fontSmall, 999, Align.center);
		l.actor(tb);

		
		for(int i=0;i<items.size;i++){
			if(i%3==0) l.absRow(10);
			EffectPanel item = items.get(i);
			l.actor(item);
			if(i<items.size-1 && i%3!=2){
				l.abs(SMALL_GAP);
			}
		}
	}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.brown_dark);
        Draw.fillRectangle(batch, getX()- EventPanel.BORDER, getY()-EventPanel.BORDER, getWidth()+EventPanel.BORDER*2, getHeight()+EventPanel.BORDER*2);
        batch.setColor(Colours.dark);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }

}
