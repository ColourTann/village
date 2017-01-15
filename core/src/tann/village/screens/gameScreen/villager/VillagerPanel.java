package tann.village.screens.gameScreen.villager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.villager.die.DiePanel;
import tann.village.util.Button;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class VillagerPanel extends Group{
	Villager villager;
	private static final int MAX_WIDTH = 350, HEIGHT = 270;
	TextBox name;
	TextBox profession;
	public VillagerPanel(final Villager villager) {
		setSize(MAX_WIDTH, HEIGHT);
		name = new TextBox(villager.firstName+" "+villager.lastName, Fonts.font, MAX_WIDTH, Align.center);
		profession = new TextBox(villager.type+" "+villager.xp+"/10 xp",Fonts.fontSmall, MAX_WIDTH, Align.center);
		DiePanel panel = new DiePanel(villager.die, MAX_WIDTH);
		
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(name);
		l.row(1);
		l.actor(profession);
		l.row(1);
		l.actor(panel);
		l.row(1);
		l.layoo();
		
		
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				GameScreen.get().pop();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.blue_dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		
		super.draw(batch, parentAlpha);
	}
	

}
