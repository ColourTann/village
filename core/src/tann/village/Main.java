package tann.village;

import com.badlogic.gdx.*;
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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;
import tann.village.bullet.BulletStuff;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.phase.RollingPhase;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.mapScreen.MapScreen;
import tann.village.util.*;
import tann.village.util.Screen;

public class Main extends ApplicationAdapter {
	public static int width = 1000, height = 700;
	public static String version = "0.3.1";
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

	public Main(){};

	public Main(int width, int height){
	    Main.width = width;
	    Main.height=height;
    }

    @Override
    public void resize(int width, int height) {
        Main.width = width;
        Main.height=height;
        orthoCam.setToOrtho(false, width, height);
        stage.getViewport().update(width, height);
        Fonts.setup();
        BulletStuff.resize();
        if(currentScreen!=null){
            currentScreen.layChain();
        }
    }

	@Override
	public void create() {
	    Main.width = Gdx.graphics.getWidth() ;
	    Main.height = Gdx.graphics.getHeight();
        logTime(null);
		logTime("start");
        Sounds.setup();
		atlas = new TextureAtlas(Gdx.files.internal("atlas_image.atlas"));
		for(Texture t:atlas.getTextures()){
		    t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
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
		TextWriter.setup();
		logTime("setup");
//        stage = new Stage(new ScalingViewport(Scaling.none, Main.width, Main.height));
        stage = new Stage(new FitViewport(500, 500));
        stage = new Stage(new ExtendViewport(500, 500));
        stage = new Stage(new FillViewport(500, 500));
        stage = new Stage(new ScreenViewport());
		orthoCam = (OrthographicCamera) stage.getCamera();
		batch = (SpriteBatch) stage.getBatch();



        InputProcessor diceInput = new InputProcessor() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(Village.getPhase().allowDieClicking() &&  GameScreen.get().allowDieClicking()) {
                    return BulletStuff.click(screenX, Main.height-screenY, button);
                }
                return false;
            }
            public boolean keyDown(int keycode) {return false;}
            public boolean keyUp(int keycode) {return false;}
            public boolean keyTyped(char character) {return false;}
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}
            public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}
            public boolean mouseMoved(int screenX, int screenY) {return false;}
            public boolean scrolled(int amount) {return false;}
        };

        Gdx.input.setInputProcessor(new InputMultiplexer(diceInput, stage));

		stage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				currentScreen.keyPress(keycode);
				return true;
			}
		});
		logTime("bits");
		BulletStuff.init();
		logTime("bullet");
//		self.travelTo(new TutorialIsland(null,0,9));
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
			if(previousScreen!=null)previousScreen.addAction(Actions.moveTo(-Main.width, 0, speed, interp));
			break;
		case RIGHT:
			screen.setPosition(-Main.width, 0);
			screen.addAction(Actions.sequence(Actions.moveTo(0, 0, speed, interp), ra));
            if(previousScreen!=null)previousScreen.addAction(Actions.moveTo(Main.width, 0, speed, interp));
			break;

		}
        if(previousScreen!=null){
            previousScreen.removeFromScreen();
		    previousScreen.addAction(Actions.after(Actions.removeActor()));
        }
	}

	public void setScreen(Screen screen) {
		if (previousScreen != null) {
			previousScreen.clearActions();
			previousScreen.removeFromScreen();
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

    public static float tickMult=1;

	public void update(float delta) {
	    tickMult= Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)?0.2f:1;
	    delta *= tickMult;
		BulletStuff.update(delta);

		ticks += delta;
		Sounds.tickFaders(delta);
		stage.act(delta);
	}

	public void travelTo(Island island) {
//	    BulletStuff.reset();
        Island.setIsland(island);
		island.setup();
		Village.get().setup();
		GameScreen.reset();
		GameScreen.get().init(island, Village.get());
		setScreen(GameScreen.get(), TransitionType.LEFT, Interpolation.pow2Out, .5f);
		GameScreen.get().pop();
	}

	public static float w(float factor){
	    return Main.width/100f*factor;
    }

    public static float h(float factor){
	    return Main.height/100f*factor;
    }
}
