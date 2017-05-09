package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.village.villager.Villager;
import tann.village.gameplay.village.villager.Villager.VillagerType;
import tann.village.gameplay.village.villager.die.*;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class LevelupPanel extends InfoPanel{
	
	
    private static final int CLASS_WIDTH = 270;
    private static final int WIDTH = 850;
    private static final int HEIGHT = 600;
    ClassPanel top;
    ClassPanel[] choices;
	public LevelupPanel(final Villager villager, VillagerType[] options) {
		
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


        choices = new ClassPanel[3];
		for(int i=0;i<3;i++){
			final VillagerType type =options[i]; 
			choices[i] = new ClassPanel(type, villager, CLASS_WIDTH, true);
            choices[i].addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					villager.setDie(new Die(type));
					removeThis();
					return super.touchDown(event, x, y, pointer, button);
				}
			});
            mainLayoo.gap(1);
            mainLayoo.actor(choices[i]);
		}
        mainLayoo.gap(1);
        mainLayoo.row(1);
        mainLayoo.layoo();
    }
	
	public void removeThis(){
		remove();
		GameScreen.get().finishedLevellingUp();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(Colours.green_light);
        int width = 5;
        for(ClassPanel cp:choices){
            Draw.drawArrow(batch, getX()+cp.getX(Align.center), getY()+cp.getY(Align.top)+40, getX()+cp.getX(Align.center), getY()+cp.getY(Align.top)+5, width);
        }

        Draw.drawLine(batch, getX()+top.getX(Align.center), getY()+top.getY(Align.bottom), getX()+top.getX(Align.center), getY()+choices[1].getY(Align.top)+10, width);
        float mid = getY()+choices[1].getY(Align.top)+(top.getY(Align.bottom)-choices[1].getY(Align.top))/2;
        Draw.drawLine(batch, getX()+choices[0].getX(Align.center)-width/2f, mid, getX()+choices[2].getX(Align.center)+width/2f, mid, width);
	}

}
