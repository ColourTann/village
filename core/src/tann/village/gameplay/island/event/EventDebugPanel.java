package tann.village.gameplay.island.event;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import tann.village.Main;
import tann.village.gameplay.effect.Effect;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDebugPanel extends Group {

    Map<Integer, List<SingleEventPanel>> map = new HashMap<>();

    public EventDebugPanel(Array<Event> events) {
        int max = 0;
        setSize(SingleEventPanel.WIDTH * 25, 600);
        for(int i=0;i<events.size;i++){
            Event e = events.get(i);
            SingleEventPanel panel = new SingleEventPanel(e, i);

            for(int level=0;level<15;level++){
                if(map.get(level)==null){
                    map.put(level, new ArrayList<SingleEventPanel>());
                    max++;
                }
                List<SingleEventPanel> list= map.get(level);
                boolean good = true;
                for(SingleEventPanel sep:list){
                    if(sep.collidesWith(panel)){
                        good=false;
                        break;
                    }
                }
                if(good){
                    list.add(panel);
                    panel.setPosition((12+e.fateLeft)*SingleEventPanel.WIDTH, level*SingleEventPanel.HEIGHT+40);
                    break;
                }
            }
            addActor(panel);
        }
        setHeight(60+max*SingleEventPanel.HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.grey);
        Draw.fillRectangle(batch,0,0, Main.width,Main.height);
        batch.setColor(Colours.double_dark);

        Draw.fillActor(batch,this);
        batch.setColor(Colours.light);
        for(int i=0;i<=24;i++){
            if(i<12) {
                batch.setColor(Colours.shiftedTowards(Colours.grey, Colours.red, (12-i+1) / 12f));
            }
            else if (i>12){
                batch.setColor(Colours.shiftedTowards(Colours.grey, Colours.blue_light, (i-12+1) / 12f));
            }
            else{
                batch.setColor(Colours.grey);
            }
            Draw.fillRectangle(batch, getX()+i*SingleEventPanel.WIDTH+1, getY(), SingleEventPanel.WIDTH-1, getHeight());
            Color c;
            if (i<12){
                 c=Colours.red;
            }
            else if(i>12){
                c=Colours.blue_light;
            }
            else{
               c=Colours.grey;
            }
            batch.setColor(Colours.fate_darkest);
            Fonts.fontSmall.setColor(Colours.dark);
            Draw.fillRectangle(batch, getX()+i*SingleEventPanel.WIDTH, getY(), 1, getHeight());
            Fonts.fontSmall.draw(batch, (i-12)+"", getX()+(i+.2f)*SingleEventPanel.WIDTH, getY()+20);
        }
        super.draw(batch, parentAlpha);
    }

    static class SingleEventPanel extends Actor{
        Event e;
        private static int WIDTH = 30, HEIGHT = 30;
        public SingleEventPanel(Event e, int index) {
            this.e=e;
            setSize((e.fateRight - e.fateLeft +1)*WIDTH,HEIGHT);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            float border = 2;
            batch.setColor(Colours.blue_dark);
            Draw.fillActor(batch,this);
            batch.setColor((e.fateRight+e.fateLeft) >0? Colours.blue_light:(e.fateLeft+e.fateRight) <0?Colours.red:Colours.grey);

            Draw.fillRectangle(batch,getX()+border,getY()+border, getWidth()-border*2, getHeight()-border*2);
            String toDraw = e.title;
            int maxLength = 1+Math.abs(e.fateRight-e.fateLeft)*6;
            if(e.title.length()>maxLength){
                toDraw=e.title.substring(0, maxLength);
            }
            Fonts.fontSmall.setColor(Colours.light);
            Fonts.fontSmall.draw(batch, toDraw, getX(), getY()+getHeight()/2+7, getWidth(), Align.center, false);
            int fateDiff=e.fateDelta;
            batch.setColor(fateDiff>0?Colours.blue_light:Colours.red);
            int orbSize = 10;
            for(int i=0;i<Math.abs(fateDiff);i++){
                Draw.fillEllipse(batch, getX()+getWidth()/2-orbSize/2*Math.abs(fateDiff)+i*orbSize, getY()+getHeight()-orbSize, orbSize, orbSize);
            }
            super.draw(batch, parentAlpha);
        }

        public boolean collidesWith(SingleEventPanel panel) {
            Event e1 = panel.e;
            Event e2 = e;
            return ! (e1.fateLeft  >  e2.fateRight || e1.fateRight < e2.fateLeft );
        }
    }
}
