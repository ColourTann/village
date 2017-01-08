package tann.village.screens.gameScreen.villager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import tann.village.Main;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.villager.Villager.VillagerType;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.TextBox;

public class LevelupPanel extends Group{
	
	
	private static final int MAIN_WIDTH = 500;
	private static final int SIDE_WIDTH = 300;
	private static final int WIDTH = MAIN_WIDTH+SIDE_WIDTH;
	public LevelupPanel(final Villager villager) {
		TextBox levelup = new TextBox("Level up!", Fonts.fontBig, 25, MAIN_WIDTH, Align.center);
		TextBox nameBox = new TextBox(villager.firstName+" "+villager.lastName, Fonts.font, 10, MAIN_WIDTH, Align.center);
		TextBox professionBox = new TextBox(villager.type.toString(), Fonts.font, 10, MAIN_WIDTH, Align.center);
		DiePanel mainPanel = new DiePanel(villager.die, MAIN_WIDTH*.9f, 20);
		
		float smallPanelHeight=0;
		for(int i=0;i<3;i++){
			final VillagerType type =VillagerType.values()[1+i]; 
			ClassPanel panel = new ClassPanel(type, SIDE_WIDTH*.7f, 1);
			addActor(panel);
			panel.setPosition(MAIN_WIDTH+ (SIDE_WIDTH/2-panel.getWidth()/2), i*(panel.getHeight()));
			smallPanelHeight=panel.getHeight();
			panel.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					villager.setDie(new Die(type));
					removeThis();
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
		setSize(WIDTH, (smallPanelHeight)*3);

		addActor(levelup);
		levelup.setPosition(MAIN_WIDTH/2-levelup.getWidth()/2, getHeight()-levelup.getHeight());
		
		addActor(nameBox);
		nameBox.setPosition(MAIN_WIDTH/2-nameBox.getWidth()/2, getHeight()-nameBox.getHeight()-levelup.getHeight());
		
		addActor(professionBox);
		professionBox.setPosition(MAIN_WIDTH/2-professionBox.getWidth()/2, getHeight()-nameBox.getHeight()-professionBox.getHeight()-levelup.getHeight());
		
		addActor(mainPanel);
		mainPanel.setPosition(MAIN_WIDTH/2-mainPanel.getWidth()/2, getHeight()-nameBox.getHeight()-professionBox.getHeight()-mainPanel.getHeight()-levelup.getHeight());
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
