package tann.village.screens.gameScreen.panels.villagerStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.gameplay.village.villager.Villager.VillagerType;
import tann.village.gameplay.village.villager.die.*;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.*;

public class LevelupPanel extends InfoPanel{
	
	
    private static final int CLASS_WIDTH = 270;
    private static final int WIDTH = 850;
    private static final int HEIGHT = 600;
    ClassPanel top;
    Array<ClassPanel> choices = new Array<>();
	public LevelupPanel(final Villager villager, Array<VillagerType> options) {
		
		setSize(WIDTH, HEIGHT);


		Layoo mainLayoo = new Layoo(this);
        TextBox levelup = new TextBox("Level up!", Fonts.fontBig, WIDTH, Align.center);
        TextBox nameBox = new TextBox(villager.firstName+" "+villager.lastName, Fonts.fontSmall, WIDTH, Align.center);

        mainLayoo.row(1);
        mainLayoo.actor(levelup);
        mainLayoo.row(1);
        mainLayoo.actor(nameBox);
        mainLayoo.row(1);

        top = new ClassPanel(villager.type, villager, CLASS_WIDTH, false);

        mainLayoo.actor(top);

        mainLayoo.row(5);


		for(final VillagerType type: options){
		    ClassPanel cp = new ClassPanel(type, villager, CLASS_WIDTH, true);
		    cp.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					villager.setDie(new Die(type));
					removeThis();
                    Sounds.playSound(Sounds.marimba_single_high,1,1);
                    return super.touchDown(event, x, y, pointer, button);
				}
			});
            choices.add(cp);
            mainLayoo.gap(1);
            mainLayoo.actor(cp);
        }
        mainLayoo.gap(1);
        mainLayoo.row(1);
        mainLayoo.layoo();
    }
	
	public void removeThis(){
		remove();
        Village.get().popPhase();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(Colours.green_light);
        int width = 5;
        for(ClassPanel cp:choices){
            Draw.drawArrow(batch, getX()+cp.getX(Align.center), getY()+cp.getY(Align.top)+40, getX()+cp.getX(Align.center), getY()+cp.getY(Align.top)+5, width);
        }
	}

}
