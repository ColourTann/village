package tann.village.screens.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import tann.village.Images;
import tann.village.Main;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.event.EventDebugPanel;
import tann.village.gameplay.island.islands.Island;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Layoo;
import tann.village.util.Screen;

public class MapScreen extends Screen{

	Map map;
	private static MapScreen self;
	public static MapScreen get(){
		if(self == null){
			self = new MapScreen();
		}
		return self;
	}
	
	public MapScreen() {
		map = new Map();
		for(Island i:map.islands){
			addActor(i.getActor());
		}
	}
	
	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.blue_dark);
		Draw.fillActor(batch, this);
	}

	FrameBuffer buff = new FrameBuffer(Format.RGBA8888, Main.width, Main.height, false);
	int size = 200;
	@Override
	public void postDraw(Batch batch) {
	}

	@Override
	public void preTick(float delta) {
	}

	@Override
	public void postTick(float delta) {
	}

	@Override
	public void keyPress(int keycode) {
		switch(keycode){
		case Input.Keys.UP:
			size += 20;
			break;
		case Input.Keys.DOWN:
			size -= 20;
			break;
		}
	}

    @Override
    protected void layout() {

    }
}
