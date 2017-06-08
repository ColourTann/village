package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

import tann.village.gameplay.village.villager.Villager;
import tann.village.gameplay.village.villager.die.Die;
import tann.village.gameplay.village.villager.die.DiePanel;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.Colours;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class VillagerPanel extends InfoPanel{
	Villager villager;
	private static final int MAX_WIDTH = 400, HEIGHT = 250;
	TextBox name;
	TextBox profession;
	public VillagerPanel(final Villager villager) {
		setSize(MAX_WIDTH, HEIGHT);
		setBackground(Colours.grey);
		name = new TextBox(villager.firstName+" "+villager.lastName, Fonts.font, MAX_WIDTH, Align.center);
		Fonts.fontSmall.setColor(Colours.light);
		profession = new TextBox(villager.type+" "+villager.xp+"/"+villager.xpToLevelUp+" xp",Fonts.fontSmall, MAX_WIDTH, Align.center);
		SpinnerPanel panel = new SpinnerPanel(new Die(villager.type, villager), MAX_WIDTH/2);
		
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
				return true;
			}
		});
	}
	
}
