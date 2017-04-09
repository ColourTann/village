package tann.village.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.screens.gameScreen.villager.Villager;
import tann.village.screens.gameScreen.villager.die.Die;
import tann.village.util.Colours;

public class BulletStuff {

	public static Renderable renderable = new Renderable();
	public static ShaderProgram shaderProgram;
	
	static PerspectiveCamera cam;
	static CameraInputController camController;
	static ModelBatch modelBatch;
	public static Array<ModelInstance> instances;
	public static Array<Die> dice = new Array<>();
	public static Array<Die> lockedDice = new Array<>();
	static Environment environment;

	static Environment locked;

	static Model model;
	static CollisionObject ground;

	static btCollisionShape groundShape;
	static btCollisionShape ballShape;

	static btBroadphaseInterface broadphase;
	static btCollisionConfiguration collisionConfig;
	static btDispatcher dispatcher;
	static MyContactListener contactListener;

	public static btDynamicsWorld dynamicsWorld;
	static btConstraintSolver constraintSolver;

	final static short GROUND_FLAG = 1 << 8;
	public final static short OBJECT_FLAG = 1 << 9;
	public final static short ALL_FLAG = -1;

	public static Array<AtlasRegion> diceTextures = new Array<>();
	
	static Shader shader;

	public static void init(){
		for(int i=0;i<6;i++){
			diceTextures.add(Main.atlas.findRegion("dice/die"+i));
		}

		Bullet.init();
		modelBatch = new ModelBatch();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 7f, -2f);
		cam.lookAt(0, 0, 0);
		cam.update();

		camController = new CameraInputController(cam);
		ModelBuilder mb = new ModelBuilder();
		mb.begin();
		mb.node().id = "ground";
		mb.part("ground", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,new Material(ColorAttribute.createDiffuse(Colours.green_light))).box(10f, 1f, 10f);
		model = mb.end();


		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		constraintSolver = new btSequentialImpulseConstraintSolver();
		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		dynamicsWorld.setGravity(new Vector3(0, -30f, 0));
		contactListener = new MyContactListener();

		instances = new Array<>();
		ground = new CollisionObject(model, "ground", new btBoxShape(new Vector3(5f, 0.5f, 5f)), 0);
		dynamicsWorld.addRigidBody(ground.body, GROUND_FLAG, ALL_FLAG);

		float wallSize = 3.8f;
		
		for (int i = 0; i < 5; i++) {
			CollisionObject wall = new CollisionObject(model, "ground", new btBoxShape(new Vector3(wallSize, 0.5f, wallSize)), 0);
			switch (i) {
			case 0:
				wall.transform.rotate(1, 0, 0, 90);
				wall.transform.trn(0, wallSize, wallSize);
				break;
			case 1:
				wall.transform.rotate(1, 0, 0, 90);
				wall.transform.trn(0, wallSize, -wallSize);
				break;
			case 2:
				wall.transform.rotate(0, 0, 1, 90);
				wall.transform.trn(wallSize, wallSize, 0);
				break;
			case 3:
				wall.transform.rotate(0, 0, 1, 90);
				wall.transform.trn(-wallSize, wallSize, 0);
				break;
			case 4:
				wall.transform.trn(0, 10, 0);
				break;
			}
			wall.initialUpdate();
			dynamicsWorld.addRigidBody(wall.body, OBJECT_FLAG, ALL_FLAG);
		}
		
		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20, new Material(),Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		
		shader = new DieShader();
	    shader.init();

	   	modelBuilder = new ModelBuilder();
		model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20, new Material(),Usage.Position | Usage.Normal | Usage.TextureCoordinates);

	}
	
	
	public static void refresh(Array<Villager> villagers) {
		clearDice();
		dice.clear();
		for(Villager v:villagers){
			dice.add(v.die);
		}
	}

	
	public static void clearDice(){
		lockedDice.clear();
	}

	public static final int mass = 1;

	public static void render() {
		 camController.update();
		    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		    modelBatch.begin(cam);
		    for (ModelInstance instance : instances){
		    	Die d = (Die)instance.userData;
//		    	shaderProgram.setUniformi(location, value);
//		    	modelBatch. setd.getSide()
		    	modelBatch.render(instance, shader);
		    }
		    modelBatch.end();
	}

	public static void update(float delta){
		float physicsDelta = Math.min(1f / 30f, delta);
		dynamicsWorld.stepSimulation(physicsDelta, 5, 1f / 60f);
		for (ModelInstance mi : instances) {
			if(mi instanceof CollisionObject){
				((CollisionObject)mi).update();
			}
		}
	}

	static boolean checkCollision(btCollisionObject obj0, btCollisionObject obj1) {
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

	private static Vector3 position = new Vector3();

	public static Die getObject (int screenX, int screenY) {
		Ray ray = cam.getPickRay(screenX, screenY);

		Die result = null;
		float distance = -1;

		for (Die d:dice) {
			final CollisionObject instance = d.physical;
			instance.updateBounds();

			instance.transform.getTranslation(position);
			position.add(instance.center);

			final float len = ray.direction.dot(position.x-ray.origin.x, position.y-ray.origin.y, position.z-ray.origin.z);
			if (len < 0f)
				continue;

			float dist2 = position.dst2(ray.origin.x+ray.direction.x*len, ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);
			if (distance >= 0f && dist2 > distance) 
				continue;

			if (dist2 <= instance.radius * instance.radius) {
				result = d;
				distance = dist2;
			}
		}
		if(result==null) return null;
		return result;
	}

	public static boolean finishedRolling(){
		for (Die d : BulletStuff.dice) {
			if (d.isMoving()) return false;
		}
		return true;
	}
	

	public static void click(float x, float y, int button) {
		Die d = getObject((int) x, Gdx.graphics.getHeight() - (int) y);
		if (d != null) {

			if (button == 0) {
				if (lockedDice.contains(d, true)) {
					lockedDice.removeValue(d, true);
				} else {
					lockedDice.add(d);
				}
			}
			if (button == 1) {
				d.villager.dieRightClicked();
			}

		}

	}

}
