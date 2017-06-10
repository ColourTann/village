package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import tann.village.Images;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.Outcome;
import tann.village.util.*;

import java.util.ArrayList;
import java.util.List;

public class EventPanel extends Group{
	
	Event e;
	int dayNumber;
	TextBox day, eventTitle, description;
	public static final int WIDTH = 370;
	private static final int items_per_row = 3;
	private static final int GAP = 30;
    public static final int BORDER = 10;
	Color border = Colours.grey;
	public EventPanel(Event e, int dayNumber) {
	    if(e.isStory()){
	        border = Colours.blue_dark;
        }
        int goodness = e.getGoodness();
	    if(goodness==1) border = Colours.blue_light;
	    if(goodness==-1) border = Colours.red;
		int height=0;
		
		this.dayNumber=dayNumber;
		this.e=e;
		day = new TextBox("Event", Fonts.font, WIDTH-GAP, Align.center);
		height += day.getHeight();
		eventTitle = new TextBox(e.title, Fonts.fontBig, 99999, Align.center);
		height += eventTitle.getHeight();
		description = new TextBox(e.description, Fonts.fontSmall, WIDTH-GAP, Align.left);
		height += description.getHeight();
		
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(eventTitle);
		l.row(1);
		l.actor(description);
		int count =0;
        for(int i=0;i<e.effects.size;i++){
            Effect effect = e.effects.get(i);
            if(count%items_per_row==0) {
                l.row(1);

            }
			if(effect.source==EffectSource.Upkeep){
				UpkeepPanel upkeepShow = new UpkeepPanel();
                List<Effect> effects = new ArrayList<>();
                effects.add(effect);
				upkeepShow.build(effects);
				if(count%items_per_row==0) height += upkeepShow.getHeight();
				l.actor(upkeepShow);
			}
			else{
				EffectPanel item = new EffectPanel(effect);
				if(count%items_per_row==0) height += item.getHeight();
				l.actor(item);
			}
			if(count%items_per_row!=2){
				l.abs(10);
			}
			count++;
			
		}
		l.row(1);

        if(e.outcomes.size>0){
            l.gap(1);
            for(int i=0;i<e.outcomes.size;i++){
                Outcome o = e.outcomes.get(i);
                l.actor(o.getPanel());
                l.gap(1);
            }
            l.row(1);
            TextBox tb = new TextBox("Choose One", Fonts.fontSmall, WIDTH, Align.center);
            l.actor(tb);
            l.row(1);
        }


        float width = Math.max(WIDTH, eventTitle.getWidth()+30);
        if(e.outcomes.size>0){
            //TODO GC?
            width = e.outcomes.get(0).getPanel().getWidth()*2 + GAP*3;
        }

        setSize(width, height + 80 + e.outcomes.size>0?300:0);
        l.layoo();

		if(e.fateDelta!=0) {
            SideFatePanel panel = new SideFatePanel(e.fateDelta);
            addActor(panel);
            panel.setPosition(getWidth() + BORDER, getHeight() / 2 - panel.getHeight() / 2);
        }

	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
	    batch.setColor(border);
	    Draw.fillRectangle(batch, getX()-BORDER, getY()-BORDER, getWidth()+BORDER*2, getHeight()+BORDER*2);
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());

		super.draw(batch, parentAlpha);
	}

	static class SideFatePanel extends Group{
	    static final int gap = 5;
	    static final int iconSize = 30;
        public SideFatePanel(int fateDelta) {
            setSize(iconSize + gap*2, gap + (1+ Math.abs(fateDelta)) * (gap+iconSize));
            TextBox tb = new TextBox(fateDelta>0?"+":"-", Fonts.fontBig, 50, Align.center);
            tb.setTextColour(fateDelta>0?Colours.blue_light:Colours.red);
            Layoo l = new Layoo(this);
            l.row(1);
            l.actor(tb);
            l.row(1);
            for(int i=0;i<Math.abs(fateDelta);i++){
                ImageActor ia = new ImageActor(Images.fate);
                ia.setSize(iconSize, iconSize);
                ia.setColor(fateDelta>0?Colours.blue_light:Colours.red);
                l.actor(ia);
                l.row(1);
            }
            l.row(1);
            l.layoo();
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setColor(Colours.dark);
            Draw.fillActor(batch, this);
            super.draw(batch, parentAlpha);
        }
    }

}
