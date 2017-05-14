package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import tann.village.Images;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.village.villager.Villager;
import tann.village.gameplay.village.villager.Villager.VillagerType;
import tann.village.gameplay.village.villager.die.*;
import tann.village.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassPanel extends Group{
	
	private static final int BORDER =10;
	Die d;
	public ClassPanel(VillagerType type, Villager villager, float WIDTH, boolean pickable) {
	    d = new Die(type, villager);
		TextBox className = new TextBox(type.toString(), Fonts.fontSmall, WIDTH-BORDER*2, Align.center);
        setSize(WIDTH, 200);


        float leftSize = WIDTH/5*3;
        float rightSize = getWidth()-leftSize;
        SpinnerPanel panel = new SpinnerPanel(d, leftSize-40);



        float groupHeight = 150;
        Group rightGroup = new Group();
        rightGroup.setSize(rightSize, groupHeight);

		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(className);
		l.row(1);
		l.add(1,panel,1,rightGroup,1);
        l.row(1);
        l.layoo();


        List<TextureRegion> fx = new ArrayList<>();
        for(Side s:d.sides){
            for(Effect e:s.effects){
                for(int i=0;i<e.value;i++){
                    if(e.type== Effect.EffectType.Skull) continue;
                    fx.add(e.type.region);
                }
            }
        }

        Collections.sort(fx, new Comparator<TextureRegion>() {
            @Override
            public int compare(TextureRegion o1, TextureRegion o2) {
                if(o1==o2) return 0;
                Effect.EffectType[] values = Effect.EffectType.values();
                for(int i=0;i<values.length;i++){
                    if(o1==values[i].region){
                        return -1;
                    }
                    if(o2 == values[i].region){
                        return 1;
                    }
                }
                return 0;
            }
        });

        l= new Layoo(rightGroup);
        l.row(1);
        int x=0;
        int y=0;
        int maxAcross = 3;
        float iconSize = 40;
        l.gap(1);
        for(TextureRegion tr:fx){
            ImageActor actor = new ImageActor(tr, iconSize, iconSize);
            if(actor.tr== Images.fate){
                actor.setColor(Colours.blue_light);
            }
            l.actor(actor);
            x++;
            l.gap(1);
            if(x>=maxAcross){
                y++;
                x=0;
                l.gap(1);
                l.row(1);
                l.gap(1);
            }
        }


        l.gap(1);
        l.row(1);

		l.layoo();

		if(pickable) {
            addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    borderColour = selected;
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    borderColour = unselected;
                    super.exit(event, x, y, pointer, toActor);
                }
            });
        }
	}
	
	private static Color unselected = Colours.fate_darkest;
    private static Color selected = Colours.green_dark;
	public Color borderColour = unselected;
	@Override
	public void draw(Batch batch, float parentAlpha) {
//		batch.setColor(Colours.dark);
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(borderColour);
//		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), BORDER);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}

	static class DieSidesPanel extends Actor{
	    List<Effect> effects = new ArrayList<>();
        public DieSidesPanel(Die d) {
            for(Side s:d.sides){
                for(Effect e:s.effects){
                    effects.add(e);
                }
            }

        }
    }
	

}