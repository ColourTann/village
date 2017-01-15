package tann.village.screens.gameScreen.villager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.villager.Villager.VillagerType;
import tann.village.screens.gameScreen.villager.die.Die;
import tann.village.screens.gameScreen.villager.die.DiePanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class LevelupPanel extends Group{
	
	
	private static final int LEFT_WIDTH = 400;
	private static final int RIGHT_WIDTH = 300;
	private static final int WIDTH = LEFT_WIDTH+RIGHT_WIDTH;
	private static final int HEIGHT = 650;
	public LevelupPanel(final Villager villager) {
		
		setSize(WIDTH, HEIGHT);
		
		Group leftGroup = new Group();
		leftGroup.setSize(LEFT_WIDTH, HEIGHT);
		Layoo left = new Layoo(leftGroup);
		addActor(leftGroup);
		
		TextBox levelup = new TextBox("Level up!", Fonts.fontBig, LEFT_WIDTH, Align.center);
		TextBox nameBox = new TextBox(villager.firstName+" "+villager.lastName, Fonts.font, LEFT_WIDTH, Align.center);
		TextBox professionBox = new TextBox(villager.type.toString(), Fonts.font, LEFT_WIDTH, Align.center);
		DiePanel mainPanel = new DiePanel(villager.die, LEFT_WIDTH*.9f);
		left.row(1);
		left.actor(levelup);
		left.row(1);
		left.actor(nameBox);
		left.row(1);
		left.actor(professionBox);
		left.row(1);
		left.actor(mainPanel);
		left.row(1);
		left.layoo();
		
		
		Group rightGroup = new Group();
		rightGroup.setSize(RIGHT_WIDTH, HEIGHT);
		Layoo right = new Layoo(rightGroup);
		addActor(rightGroup);
		rightGroup.setPosition(LEFT_WIDTH, 0);
		
		
		for(int i=0;i<3;i++){
			final VillagerType type =VillagerType.values()[1+i]; 
			ClassPanel panel = new ClassPanel(type, RIGHT_WIDTH-20);
			panel.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					villager.setDie(new Die(type));
					removeThis();
					return super.touchDown(event, x, y, pointer, button);
				}
			});
			right.row(1);
			right.actor(panel);
		}
		right.row(1);
		right.layoo();
	}
	
	public void removeThis(){
		remove();
		GameScreen.get().finishedLevellingUp();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.fate_darkest);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}

}
