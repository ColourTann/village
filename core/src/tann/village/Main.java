package tann.village;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btConeShape;
import com.badlogic.gdx.physics.bullet.collision.btCylinderShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import tann.village.screens.pause.InputBlocker;
import tann.village.screens.pause.PauseScreen;
import tann.village.screens.testScreens.GameScreen;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Screen;
import tann.village.util.Sounds;
import tann.village.util.TannFont;

public class Main extends ApplicationAdapter {
	public static int width = 320, height = 220;
	SpriteBatch batch;
	Stage stage;
	OrthographicCamera orthoCam;
	public static TextureAtlas atlas;
	public static Main self;
	public static int scale = 3;
	public static boolean debug = true;
	Screen currentScreen;
	Screen previousScreen;
	FrameBuffer buffer;
	public static float ticks;
	public static int coloursUnlocked = 2;

	public enum MainState {
		Normal, Paused
	}

	public static final int version = 4;

	@Override
	public void create() {
		self = this;

		

		Sounds.setup();
		buffer = new FrameBuffer(Format.RGBA8888, Main.width, Main.height, true);
		atlas = new TextureAtlas(Gdx.files.internal("atlas_image.atlas"));
		stage = new Stage(new FitViewport(Main.width, Main.height));
		orthoCam = (OrthographicCamera) stage.getCamera();
		batch = (SpriteBatch) stage.getBatch();
		Gdx.input.setInputProcessor(stage);
		stage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {

				switch (keycode) {
				case Keys.ESCAPE:
					toggleMenu();
					return false;
				}
				currentScreen.keyPress(keycode);
				return true;
			}

		});

		setScale(scale);

		setScreen(GameScreen.get());
		setup3D();

	}

	PerspectiveCamera perspectiveCam;
	CameraInputController camController;
	ModelBatch modelBatch;
	public static Array<CollisionObject> instances;
	Environment environment;

	Model model;
	CollisionObject ground;

	btCollisionShape groundShape;
	btCollisionShape ballShape;

	btBroadphaseInterface broadphase;
	btCollisionConfiguration collisionConfig;
	btDispatcher dispatcher;
	MyContactListener contactListener;

	btDynamicsWorld dynamicsWorld;
	btConstraintSolver constraintSolver;

	  final static short GROUND_FLAG = 1<<8;
	    final static short OBJECT_FLAG = 1<<9;
	    final static short ALL_FLAG = -1;

	private void setup3D() {
		Bullet.init();

		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		perspectiveCam = new PerspectiveCamera(67, Main.width, Main.height);
		perspectiveCam.position.set(3f, 7f, 0f);
		perspectiveCam.lookAt(0, 0, 0);
		perspectiveCam.update();
		camController = new CameraInputController(perspectiveCam);
		Gdx.input.setInputProcessor(camController);
		int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
		ModelBuilder mb = new ModelBuilder();
		float amt = .5f;
		mb.begin();
		mb.node().id = "die";
		MeshPartBuilder mpb = mb.part("die", GL20.GL_TRIANGLES, attr, new Material(TextureAttribute.createDiffuse(atlas.findRegion("die0").getTexture())));
		mpb.setUVRange(atlas.findRegion("die0"));
		mpb.rect(-amt,-amt,-amt, -amt,amt,-amt,  amt,amt,-amt, amt,-amt,-amt, 0,0,-1);
		mpb.setUVRange(atlas.findRegion("die1"));
		mpb.rect(-amt,amt,amt, -amt,-amt,amt,  amt,-amt,amt, amt,amt,amt, 0,0,1);
		mpb.setUVRange(atlas.findRegion("die2"));
		mpb.rect(-amt,-amt,amt, -amt,-amt,-amt,  amt,-amt,-amt, amt,-amt,amt, 0,-1,0);
		mpb.setUVRange(atlas.findRegion("die3"));
		mpb.rect(-amt,amt,-amt, -amt,amt,amt,  amt,amt,amt, amt,amt,-amt, 0,1,0);
		mpb.setUVRange(atlas.findRegion("die4"));
		mpb.rect(-amt,-amt,amt, -amt,amt,amt,  -amt,amt,-amt, -amt,-amt,-amt, -1,0,0);
		mpb.setUVRange(atlas.findRegion("die5"));
		mpb.rect(amt,-amt,-amt, amt,amt,-amt,  amt,amt,amt, amt,-amt,amt, 1,0,0);
		mb.node().id = "ground";
		mb.part("ground", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Colours.red))).box(10f, 1f, 10f);
		mb.node().id = "sphere";
		mb.part("sphere", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Colours.light))).sphere(1f, 1f, 1f, 10, 10);
		mb.node().id = "box";
		mb.part("box", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Colours.light))).box(1f, 1f, 1f);
		mb.node().id = "cone";
		mb.part("cone", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Colours.dark))).cone(1f, 2f, 1f, 10);
		mb.node().id = "capsule";
		mb.part("capsule", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Colours.blue))).capsule(0.5f, 2f, 10);
		mb.node().id = "cylinder";
		mb.part("cylinder", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Colours.blue))).cylinder(1f, 2f, 1f, 10);
		model = mb.end();

		float mass = 1;

		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		constraintSolver = new btSequentialImpulseConstraintSolver();
		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		dynamicsWorld.setGravity(new Vector3(0, -10f, 0));
		contactListener = new MyContactListener();

		instances = new Array<>();
		ground = new CollisionObject(model, "ground", new btBoxShape(new Vector3(5f, 0.5f, 5f)), 0);
		instances.add(ground);
		dynamicsWorld.addRigidBody(ground.body, GROUND_FLAG, ALL_FLAG);
		
		

	}

	public void setScale(int scale) {
		Main.scale = scale;
		int newWidth = width * scale;
		int newHeight = height * scale;

		Gdx.graphics.setWindowedMode(newWidth, newHeight);
		stage.getViewport().update(newWidth, newHeight);
	}

	public void toggleMenu() {
		if (state != MainState.Paused) {
			stage.addActor(InputBlocker.get());
			stage.addActor(PauseScreen.get());
			setState(MainState.Paused);
		} else {
			InputBlocker.get().remove();
			PauseScreen.get().remove();
			setState(MainState.Normal);
		}

	}

	public void spawn() {
		int i = (int) (Math.random() * 5);
		i=5;
		float mass = 1;
		CollisionObject co = null;
		switch (i) {
		case 0:
			co = new CollisionObject(model, "cylinder", new btCylinderShape(new Vector3(.5f, 1f, .5f)), mass);
			break;
		case 1:
			co = new CollisionObject(model, "sphere", new btSphereShape(0.5f), mass);
			break;
		case 2:
			co = new CollisionObject(model, "box", new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f)), mass);
			break;
		case 3:
			co = new CollisionObject(model, "cone", new btConeShape(0.5f, 2f), mass);
			break;
		case 4:
			co = new CollisionObject(model, "capsule", new btCapsuleShape(.5f, 1f), mass); break;
		case 5: co = new CollisionObject(model, "die",new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f)), mass); 			break;
		}
		 co.transform.setFromEulerAngles(MathUtils.random(360f), MathUtils.random(360f), MathUtils.random(360f));
	        co.transform.trn(MathUtils.random(-2.5f, 2.5f), 9f, MathUtils.random(-2.5f, 2.5f));
	        co.body.setWorldTransform(co.transform);
	        co.body.setUserValue(instances.size);
	        co.body.setCollisionFlags(co.body.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
	        instances.add(co);
	        dynamicsWorld.addRigidBody(co.body, OBJECT_FLAG, ALL_FLAG);
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
	};

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
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		buffer.begin();
		// batch.begin();
		// batch.setColor(Colours.dark);
		// batch.setProjectionMatrix(orthoCam.combined);
		// Draw.fillRectangle(batch, 0, 0, Main.width, Main.height);
		// batch.end();
		

		if (Main.debug) {
			batch.begin();
			drawFPS(batch);
			batch.end();
		}

		buffer.end();
		batch.begin();
		batch.setColor(1, 1, 1, 1);
		buffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		Draw.drawRotatedScaledFlipped(batch, buffer.getColorBufferTexture(), 0, 0, 1, 1, 0, false, true);
		batch.end();
		
		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();

		modelBatch.begin(perspectiveCam);
		modelBatch.render(instances, environment);
		modelBatch.end();

	}

	public void drawFPS(Batch batch) {
		batch.setColor(Colours.light);
		TannFont.font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, Main.height);
	}

	public void update(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			spawn();
		}
		float physicsDelta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());
		dynamicsWorld.stepSimulation(physicsDelta, 5, 1f / 60f);
		
		for (CollisionObject co : instances) {
			co.update();
			// if (co.moving) {
			// co.transform.trn(0f, -delta*10, 0f);
			// co.body.setWorldTransform(co.transform);
			// }
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			delta /= 10f;
		}
		ticks += delta;
		Sounds.tickFaders(delta);
		stage.act(delta);
	}

	boolean checkCollision(btCollisionObject obj0, btCollisionObject obj1) {
		CollisionObjectWrapper co0 = new CollisionObjectWrapper(obj0);
		CollisionObjectWrapper co1 = new CollisionObjectWrapper(obj1);

		btCollisionAlgorithm algorithm = dispatcher.findAlgorithm(co0.wrapper, co1.wrapper);

		btDispatcherInfo info = new btDispatcherInfo();
		btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);

		algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);

		boolean r = result.getPersistentManifold().getNumContacts() > 0;

		dispatcher.freeCollisionAlgorithm(algorithm.getCPointer());
		result.dispose();
		info.dispose();
		co1.dispose();
		co0.dispose();

		return r;
	}

}
