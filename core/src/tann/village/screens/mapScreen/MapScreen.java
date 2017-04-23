package tann.village.screens.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import tann.village.Images;
import tann.village.Main;
import tann.village.gameplay.island.Island;
import tann.village.util.Colours;
import tann.village.util.Draw;
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
	}
	
	@Override
	public void preDraw(Batch batch) {
	}

	FrameBuffer buff = new FrameBuffer(Format.RGBA8888, Main.width, Main.height, false);
	int size = 200;
	@Override
	public void postDraw(Batch batch) {
		batch.end();
		buff.bind();
		buff.begin();
		batch.begin();
		batch.enableBlending();
		batch.setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ZERO);
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, 0, 0, Main.width, Main.height);
		
		for(Island i:map.islands){
			i.drawMask(batch);
		}
//		batch.draw(Images.mask, Gdx.input.getX()-size/2, Gdx.input.getY()-size/2, size, size);
		
		batch.end();
		buff.end();
		
		batch.setBlendFunction(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
		
		batch.begin();
		
		batch.setColor(Colours.blue_dark);
		Draw.fillActor(batch, this);
		for(Island i:map.islands){
			i.draw(batch);
		}
		
		Draw.draw(batch,buff.getColorBufferTexture(),0,0);
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

}
