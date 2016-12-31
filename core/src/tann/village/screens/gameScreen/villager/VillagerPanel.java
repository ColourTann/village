package tann.village.screens.gameScreen.villager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.util.Button;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.TextBox;

public class VillagerPanel extends Group{
	Villager villager;
	private static final int GAP = 16;
	private static final int MAX_WIDTH = 450;
	TextBox name;
	TextBox profession;
	public VillagerPanel(final Villager villager) {
		name = new TextBox(villager.firstName+" "+villager.lastName, Fonts.font, GAP, MAX_WIDTH, Align.center);
		addActor(name);
		
		profession = new TextBox(villager.type+" "+villager.xp+"/10 xp",Fonts.fontSmall, GAP, MAX_WIDTH, Align.center);
		addActor(profession);
		
		
	
		Array<Button> dieFaces = new Array<>();
		for(int i=0;i<6;i++){
			final Side side = villager.die.sides.get(i);
			Runnable r = new Runnable() {
				public void run() {
					System.out.println(side.effect.type);
				}
			};
			Button butt =new Button((MAX_WIDTH)/3, (MAX_WIDTH)/3, GAP, side.tr, Colours.transparent, r); 
			dieFaces.add(butt);
			addActor(butt);
		}
		
		setSize(MAX_WIDTH, name.getHeight()+profession.getHeight()+dieFaces.get(0).getHeight()*2);
		
		name.setPosition(getWidth()/2-name.getWidth()/2, getHeight()-name.getHeight());
		profession.setPosition(getWidth()/2-profession.getWidth()/2, name.getY()-profession.getHeight());
		for(int i=0;i<6;i++){
			Button butt = dieFaces.get(i);
			butt.setPosition(butt.getWidth()*(i/2), butt.getHeight()*((i+1)%2));
		}
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
