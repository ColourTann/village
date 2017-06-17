package tann.village.screens.gameScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import tann.village.Main;
import tann.village.util.Button;
import tann.village.util.Colours;

public class ProceedButton extends Button{

	public ProceedButton() {
		super(200, 60, .7f, Main.atlas.findRegion("arrow"), Colours.dark);
		setRunnable(new Runnable() {
			@Override
			public void run() {
				remove(); 
				linkedActor.remove();
				GameScreen.get().proceed();
			}
		});
	}

    @Override
    public void layout() {
        setSize(Main.h(35), Main.h(8));
        refreshPosition();
    }

    Actor linkedActor;
	public void setLinkedActor(Actor linkedActor){
		this.linkedActor= linkedActor;
		refreshPosition();
	}

	public void refreshPosition(){
        setPosition(Main.width/2-getWidth()/2, linkedActor.getY()-getHeight()-Main.h(5));
    }

	
	

}
