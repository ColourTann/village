package tann.village.screens.gameScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import tann.village.Main;
import tann.village.util.Button;
import tann.village.util.Colours;

public class ProceedButton extends Button{

	public ProceedButton() {
		super(200, 60, .8f, Main.atlas.findRegion("arrow"), Colours.dark);
		setRunnable(new Runnable() {
			@Override
			public void run() {
				remove(); 
				linkedActor.remove();
				GameScreen.get().proceed();
			}
		});
	}
	
	Actor linkedActor;
	public void setLinkedActor(Actor linkedActor){
		this.linkedActor= linkedActor;
	}
	
	

}
