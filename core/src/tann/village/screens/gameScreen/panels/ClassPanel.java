package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import tann.village.gameplay.village.villager.Villager;
import tann.village.gameplay.village.villager.Villager.VillagerType;
import tann.village.gameplay.village.villager.die.DiePanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class ClassPanel extends Group{
	
	private static final int BORDER = 3;
	public ClassPanel(VillagerType type, float WIDTH) {
		TextBox className = new TextBox(type.toString(), Fonts.fontSmall, WIDTH-BORDER*2, Align.center);
		DiePanel panel = new DiePanel(new Die(type), WIDTH-BORDER*2);
		setSize(WIDTH, panel.getHeight()+className.getHeight()+BORDER*2);
		
		Layoo l = new Layoo(this);
		l.actor(className);
		l.row(1);
		l.actor(panel);
		l.layoo();
		
		addListener(new ClickListener(){
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				borderColour=Colours.fate_light;
				super.enter(event, x, y, pointer, fromActor);
			}
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				borderColour=Colours.transparent;
				super.exit(event, x, y, pointer, toActor);
			}
		});
	}
	
	
	public Color borderColour = Colours.transparent;
	@Override
	public void draw(Batch batch, float parentAlpha) {
//		batch.setColor(Colours.dark);
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(borderColour);
//		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), BORDER);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}
	

}