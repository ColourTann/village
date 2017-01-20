package tann.village.screens.gameScreen.panels.review;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.EffectPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class ReviewPanel extends Group{
	
	static final int items_per_row=3;
	static final int itemWidth = (int)EffectPanel.WIDTH;
	static final int itemHeight = (int)EffectPanel.HEIGHT;
	static final int WIDTH = 380;
	static final int HEIGHT = 290;
	int day;
	
	TextBox title;
	
	TextBox diceTitle;
	TextBox buildingsTitle;
	TextBox upkeepTitle;
	public ReviewPanel(int day) {
		this.day=day;
		setSize(WIDTH, HEIGHT);
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
		l = new Layoo(this);
		l.row(1);
		title = new TextBox("Day "+day+" review", Fonts.font, WIDTH, Align.center);
		l.actor(title);
		if(upkeepEffects.size>0){
			addItems("Upkeep", upkeepEffects);
		}
		if(buildingEffects.size>0){
			addItems("Buildings", buildingEffects);
		}
		if(diceEffects.size>0){
			addItems("Dice", diceEffects);
		}
		l.row(3);
		l.layoo();
	}

	private void addItems(String title, Array<EffectPanel> items){
		
		l.row(3);
		TextBox tb = new TextBox(title, Fonts.fontSmall, WIDTH, Align.center);
		l.actor(tb);

		
		for(int i=0;i<items.size;i++){
			if(i%3==0) l.absRow(10);
			EffectPanel item = items.get(i);
			l.actor(item);
			if(i<items.size-1 && i%3!=2){
				l.abs(10);
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}
}
