package tann.village.screens.gameScreen.panels.eventStuff;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.Array;
import tann.village.Images;
import tann.village.Main;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.EffAct;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.Outcome;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.UpkeepPanel;
import tann.village.util.*;

public class EventPanel extends Lay{

    public Event e;
    int dayNumber;
    TextBox day, eventTitle, description;
    public static float WIDTH;
    private static final int items_per_row = 3;
    private static final int GAP = 10;
    public static final int BORDER = 10;
    Color border = Colours.grey;
	public EventPanel(Event e, int dayNumber) {
	    this.e=e;
        this.dayNumber=dayNumber;
        layout();
	}

    @Override
    public void layout() {
	    clearChildren();
	    float hGap = Main.w(5);
	    WIDTH = EffectPanel.staticWidth() * 2 + hGap*3;
        if(e.isStory()){
            border = Colours.blue_dark;
        }
        else{
            border = Colours.brown_light;
        }
        int goodness = e.getGoodness();

        int height=0;

        this.e=e;
        day = new TextBox("Event", Fonts.font, WIDTH-GAP, Align.center);
        height += day.getHeight();
        eventTitle = new TextBox(e.title, Fonts.fontBig, -1, Align.center);

        float width = Math.max(WIDTH, eventTitle.getWidth()+30);
        if(e.outcomes.size>0){
            width = Math.max(width,e.outcomes.get(0).getPanel().getWidth()*e.outcomes.size + GAP*(e.outcomes.size+1));
        }

        height += eventTitle.getHeight();
        description = new TextBox(e.description, Fonts.fontSmall, width-20, Align.left);
        height += description.getHeight();

        Layoo l = new Layoo(this);
        l.row(1);
        l.actor(eventTitle);
        l.row(1);
        l.actor(description);
        int count =0;
        for(int i=0;i<e.effects.size;i++){
            Eff effect = e.effects.get(i);
            if(count%items_per_row==0) {
                l.row(1);
            }
            Lay pann = null;
            if(effect.effAct.type== EffAct.ActivationType.UPKEEP){
                UpkeepPanel upkeepShow = new UpkeepPanel();
                Array<Eff> effects = new Array<>();
                effects.add(effect);
                upkeepShow.setEffects(effects);
                pann = upkeepShow;

            }
             else {
                pann = new EffectPanel(effect, true);

            }
            if(count%items_per_row==0) height += pann.getHeight();
            l.actor(pann);

            if(count%items_per_row!=2){
                l.abs(10);
            }
            count++;

        }
        l.row(1);

        if(e.outcomes.size>0){
            TextWriter tw = new TextWriter("[frill-left] Choose One [frill-right]", Fonts.fontSmall);
            l.actor(tw);
            l.row(1);
            l.gap(1);

            float biggestHeight=0;
            for(int i=0;i<e.outcomes.size;i++){
                final Outcome o = e.outcomes.get(i);
                final OutcomePanel op = o.getPanel();
                l.actor(op);
                l.gap(1);
                float ocHeight = op.getHeight()+(op.o.cost!=null?CostTab.height():0);
                if(ocHeight>biggestHeight) biggestHeight=ocHeight;
                op.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                      selectOutcome(o);
                      return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        event.cancel();
                        return;
//                        System.out.println("tup");
//                        super.touchUp(event, x, y, pointer, button);
                    }
                });
            }
            l.row(1);
            height+=biggestHeight;
        }


        height += Main.h(10);
        setSize(width, height);
        l.layoo();
    }

    public void selectOutcome(Outcome chosen){
        for(Outcome o:e.outcomes){
            if(o.getPanel().locked){
                Sounds.playSound(Sounds.error, 1, 1);
                return;
            }
        }
        if(chosen.isValid()){
            for(Outcome o:e.outcomes){
                o.getPanel().deselect();
            }
            chosen.getPanel().select();
        }
        else{
            Sounds.playSound(Sounds.error, 1, 1);
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

    static class SideFatePanel extends Lay{
	    int gap = 5;
	    int iconSize = 30;
	    int fateDelta;
        public SideFatePanel(int fateDelta) {
            this.fateDelta=fateDelta;
            layout();
        }

        @Override
        public void layout() {
            clearChildren();
            gap = (int)(Main.h(1));
            iconSize = (int)(Main.h(6));
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
