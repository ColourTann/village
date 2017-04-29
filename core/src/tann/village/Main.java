package tann.village;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.viewport.FitViewport;

import tann.village.bullet.BulletStuff;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.mapScreen.MapScreen;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Screen;
import tann.village.util.Sounds;

public class Main extends ApplicationAdapter {
	public static int width = 1000, height = 700;
	String version = "0.2";
	SpriteBatch batch;
	Stage stage;
	OrthographicCamera orthoCam;
	public static TextureAtlas atlas;
	public static TextureAtlas atlas_3d;
	public static Main self;
	public static boolean debug = false;
	public static boolean showFPS = true;
	public static boolean chadwick = false;
	Screen currentScreen;
	Screen previousScreen;
	public static float ticks;
	public static int coloursUnlocked = 2;

	public enum MainState {
		Normal, Paused
	}

	private static long previousTime;
	public static void logTime(String id){
		if(!chadwick) return;
		long currentTime = System.currentTimeMillis();
		if(id!=null){
			System.out.println(id+": "+(currentTime-previousTime));
		}
		previousTime = System.currentTimeMillis();
	}
	
	@Override
	public void create() {
		logTime(null);
		logTime("start");
		atlas = new TextureAtlas(Gdx.files.internal("atlas_image.atlas"));
		atlas_3d = new TextureAtlas(Gdx.files.internal("3d/atlas_image.atlas"));
		for(Texture t: atlas_3d.getTextures()){
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		for(Texture t: atlas.getTextures()){
//			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		logTime("textures");
		self = this;
		Draw.setup();
		Fonts.setup();
		Sounds.setup();
		logTime("setup");
		stage = new Stage(new FitViewport(Main.width, Main.height));
		orthoCam = (OrthographicCamera) stage.getCamera();
		batch = (SpriteBatch) stage.getBatch();
		Gdx.input.setInputProcessor(stage);
		stage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				switch (keycode) {
				case Keys.ESCAPE:
					return false;
				}
				currentScreen.keyPress(keycode);
				return true;
			}
		});
		logTime("bits");
		BulletStuff.init();
		logTime("bullet");
//		setScreen(GameScreen.get());
		setScreen(MapScreen.get());
		logTime("screen");
	}




	private MainState state = MainState.Normal;

	public MainState getState() {
		return state;
	}

	public void setState(MainState state) {
		this.state = state;
	}

	public enum TransitionType {
		LEFT, RIGHT
	}

	public void setScreen(final Screen screen, TransitionType type, Interpolation interp, float speed) {
		if (screen == currentScreen)
			return;
		setScreen(screen);
		RunnableAction ra = Actions.run(new Runnable() {
			public void run() {
				screen.setActive(true);
			}
		});
		switch (type) {
		case LEFT:
			screen.setPosition(Main.width, 0);
			screen.addAction(Actions.sequence(Actions.moveTo(0, 0, speed, interp), ra));
			previousScreen.addAction(Actions.moveTo(-Main.width, 0, speed, interp));
			break;
		case RIGHT:
			screen.setPosition(-Main.width, 0);
			screen.addAction(Actions.sequence(Actions.moveTo(0, 0, speed, interp), ra));
			previousScreen.addAction(Actions.moveTo(Main.width, 0, speed, interp));
			break;

		}
		previousScreen.addAction(Actions.after(Actions.removeActor()));
	}

	public void setScreen(Screen screen) {
		if (previousScreen != null) {
			previousScreen.clearActions();
			previousScreen.remove();
		}
		if (currentScreen != null) {
			currentScreen.clearActions();
			previousScreen = currentScreen;
			currentScreen.setActive(false);
		}
		currentScreen = screen;
		stage.addActor(screen);

	}


	@Override
	public void render() {
		
		
		long startTime = System.currentTimeMillis();
		
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();

//		BulletStuff.render();

		drawVersion();
		
		if (Main.showFPS) {
			batch.begin();
			batch.setColor(Colours.light);
			drawFPS(batch);
			batch.end();
		}
		
		if(Main.showFPS){
			updateFPS(System.currentTimeMillis()-startTime);
		}
	}

	private void drawVersion() {
		batch.begin();
		Fonts.fontSmall.setColor(Colours.blue_dark);
		Fonts.fontSmall.draw(batch, version, 0, Fonts.fontSmall.getLineHeight());
		batch.end();
	}

	int sampleSize=60;
	long[] values = new long[sampleSize];
	int currentSample;
	private void updateFPS(long value){
		values[(currentSample++)%sampleSize]=value;
	}
	
	private void drawFPS(Batch batch) {
		Fonts.fontSmall.setColor(Colours.fate_darkest);
		
		
		long average = 0;
		for(long l:values){
			average+=l;
		}
		average/=values.length;
		
		Fonts.fontSmall.draw(batch, String.valueOf(average)+":"+Gdx.graphics.getFramesPerSecond(), 0, 60);
	}


	public void update(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			delta /= 10f;
		}

		BulletStuff.update(delta);

		ticks += delta;
		Sounds.tickFaders(delta);
		stage.act(delta);
	}

	public void travelTo(Island island) {
		island.setup();
		GameScreen.get().init(island, Village.get());
		setScreen(GameScreen.get());
	}

}
