package tann.village.screens.gameScreen.panels.villagerStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import tann.village.Images;
import tann.village.Main;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.panels.inventoryStuff.InventoryItemPanel;
import tann.village.util.*;

public class VillagerIcon extends Lay {
    Villager v;
    public VillagerIcon(final Villager v){
        this.v=v;
        layout();
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Village.getPhase().selectVillager(v);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public static float width(){
        return 500;
    }

    public static float height(){
        return Main.h(11);
    }

    static float brainWidth = 25;
    static float gap = 10;

    @Override
    public void layout() {
        setSize((int)(v.xpToLevelUp*brainWidth + gap * (v.xpToLevelUp+1)+ border*2), Main.h(11));

        clearChildren();

        boolean maxLevel = v.type.level==3;
        if(maxLevel){
            setWidth(Main.w(17));
        }
        String profString = v.type.toString();
        if(maxLevel) profString= "* "+profString+" *";
        TextBox prof = new TextBox(profString, Fonts.fontSmall, -1, Align.center);

        Layoo l = new Layoo(this);
        l.row(2);
        l.actor(prof);
        if(!maxLevel){
            XPDisplay xpd = new XPDisplay(v);
            l.row(1);
            l.actor(xpd);
        }

        l.row(2);
        l.layoo();
    }

    static float border = Main.h(.6f);

    @Override
    public void draw(Batch batch, float parentAlpha) {

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


        ImageActor[] brains;
        @Override
        public void layout() {
            clearChildren();

            setSize(v.xpToLevelUp*brainWidth + gap * (v.xpToLevelUp+1), brainWidth);
            TextureRegion full = Images.brain;
            TextureRegion empty = Images.brainempty;
            brains = new ImageActor[v.xpToLevelUp];
            for(int i=0;i<v.xpToLevelUp;i++){
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
                brains[i]= new ImageActor(tr, brainWidth, brainWidth);

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
