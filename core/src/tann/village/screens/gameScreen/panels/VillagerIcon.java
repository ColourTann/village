package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import tann.village.Images;
import tann.village.Main;
import tann.village.gameplay.village.villager.Villager;
import tann.village.util.*;

public class VillagerIcon extends Lay {
    Villager v;
    public VillagerIcon(Villager v){
        this.v=v;
        layout();
    }

    public static float width(){
        return Main.h(22);
    }

    public static float height(){
        return Main.h(17);
    }

    @Override
    public void layout() {
        setSize(width(), height());
        clearChildren();
        TextBox name = new TextBox(v.firstName, Fonts.fontSmall, 999, Align.center);
        TextBox prof = new TextBox(v.type.toString(), Fonts.fontSmall, 999, Align.center);
        XPDisplay xpd = new XPDisplay(v);
        Layoo l = new Layoo(this);
        l.row(2);
        l.actor(name);
        l.row(1);
        l.actor(prof);
        l.row(1);
        l.actor(xpd);
        l.row(2);
        l.layoo();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float border = Main.h(.6f);
        batch.setColor(v.getColour());
        Draw.fillActor(batch,this);
        batch.setColor(Colours.dark);
        Draw.fillRectangle(batch, getX()+border, getY()+border, getWidth()-border*2, getHeight()-border*2);
        super.draw(batch, parentAlpha);
    }

    static class XPDisplay extends Lay{
        Villager v;
        public XPDisplay(Villager v) {
            this.v=v;
            layout();
        }

        ImageActor[] brains = new ImageActor[Villager.xpToLevelUp];

        @Override
        public void layout() {
            clearChildren();
            setSize(width(), Main.h(5));
            TextureRegion full = Images.brain;
            TextureRegion empty = Images.brainempty;
            for(int i=0;i<Villager.xpToLevelUp;i++){
                float size = getWidth()/5;
                TextureRegion tr;
                if(v.xp>i){
                    tr = Images.brain;
                }
                else if(v.xp<=i && v.xp+v.potentialXp>i){
                    tr = Images.brainFilling;
                }
                else{
                    tr = Images.brainempty;
                }
                brains[i]= new ImageActor(tr, size, size);

            }
            Layoo l = new Layoo(this);
            l.gap(1);
            for(ImageActor ia:brains){
                l.actor(ia);
                l.gap(1);
            }
            l.layoo();
        }

    }
}
