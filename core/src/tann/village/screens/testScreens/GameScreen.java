package tann.village.screens.testScreens;

import com.badlogic.gdx.graphics.g2d.Batch;

import tann.village.Main;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Noise;
import tann.village.util.Screen;

public class GameScreen extends Screen{
	
	private static GameScreen self;
	public static GameScreen get(){
		if(self==null) self= new GameScreen();
		return self;
	}
	
	public GameScreen() {
		setSize(Main.width, Main.height);
		
		
	}
	
	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.light);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		float frequency = .01f;
		for(int x=0;x<Main.width;x++){
			for(int y=0;y<Main.height;y++){
				double n = Noise.noise((x)*frequency, (y)*frequency, Main.ticks/5);
				n += Noise.noise((x)*frequency*2, (y)*frequency*2, Main.ticks/5)*.5f;
				n += Noise.noise((x)*frequency*4, (y)*frequency*4, Main.ticks/5)*.25f;
				batch.setColor(Colours.shiftedTowards(Colours.dark, Colours.red, (float)n));
				Draw.fillRectangle(batch, x, y, 1, 1);
			}
		}
	}
	
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
		Main.self.spawn();
	}

	


	
}
