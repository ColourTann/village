package tann.village.gameplay.island.islands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import tann.village.Images;
import tann.village.Main;
import tann.village.util.Colours;
import tann.village.util.Draw;

public class IslandActor extends Actor{
	
	Island island;
	int maskSize = 180;
	public IslandActor(final Island island) {
		setSize(island.tr.getRegionWidth(), island.tr.getRegionHeight());
		this.island=island;
		setPosition(island.x-getWidth()/2, island.y-getHeight()/2);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.self.travelTo(island);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	public void draw(Batch batch, float parentAlpha){
		batch.setColor(Colours.z_white);
		Draw.draw(batch, island.tr, getX(), getY());
	}
	
}
