package tann.village.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
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

import tann.village.Images;
import tann.village.Main;
import tann.village.screens.gameScreen.Effect;
import tann.village.screens.gameScreen.Effect.EffectType;
import tann.village.screens.gameScreen.villager.Die;
import tann.village.screens.gameScreen.villager.Side;
import tann.village.screens.gameScreen.villager.Villager;
import tann.village.screens.gameScreen.villager.Villager.VillagerType;
import tann.village.util.Colours;

public class BulletStuff {

	static PerspectiveCamera perspectiveCam;
	static CameraInputController camController;
	static ModelBatch modelBatch;
	public static Array<CollisionObject> instances;
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

	public static void init(){

		for(int i=0;i<6;i++){
			diceTextures.add(Main.atlas.findRegion("dice/die"+i));
		}

		Bullet.init();

		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		locked= new Environment();
		locked.set(new ColorAttribute(ColorAttribute.Specular, .2f, .4f, .4f, .8f));
		//		locked.set(new ColorAttribute(ColorAttribute.Diffuse, 1, 1, 1, 1f));
		locked.add(new DirectionalLight().set(1, 1, 1, 0, -0.8f, 0));

		perspectiveCam = new PerspectiveCamera(67, Main.width, Main.height);
		perspectiveCam.position.set(0f, 10f, 0f);
		perspectiveCam.lookAt(0, 0, 0);
		perspectiveCam.update();
		camController = new CameraInputController(perspectiveCam);
		int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
				| VertexAttributes.Usage.TextureCoordinates;
		ModelBuilder mb = new ModelBuilder();
		float amt = .5f;
		mb.begin();



		mb.node().id = "ground";
		mb.part("ground", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
				new Material(ColorAttribute.createDiffuse(Colours.green_light))).box(10f, 1f, 10f);
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
		//		instances.add(ground);
		dynamicsWorld.addRigidBody(ground.body, GROUND_FLAG, ALL_FLAG);

		for (int i = 0; i < 5; i++) {
			CollisionObject wall = new CollisionObject(model, "ground", new btBoxShape(new Vector3(5f, 0.5f, 5f)), 0);
			switch (i) {
			case 0:
				wall.transform.rotate(1, 0, 0, 90);
				wall.transform.trn(0, 5, 5);
				break;
			case 1:
				wall.transform.rotate(1, 0, 0, 90);
				wall.transform.trn(0, 5, -5);
				break;
			case 2:
				wall.transform.rotate(0, 0, 1, 90);
				wall.transform.trn(5, 5, 0);
				break;
			case 3:
				wall.transform.rotate(0, 0, 1, 90);
				wall.transform.trn(-5, 5, 0);
				break;
			case 4:
				wall.transform.trn(0, 10, 0);
				break;
			}
			wall.initialUpdate();
			// instances.add(wall);
			dynamicsWorld.addRigidBody(wall.body, OBJECT_FLAG, ALL_FLAG);
		}

	
	
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
		modelBatch.begin(perspectiveCam); 
		modelBatch.render(instances, environment);
		Array<ModelInstance> lockedInstances = new Array<>();
		for(Die d:lockedDice){
			lockedInstances.add(d.physical);
		}
		modelBatch.render(lockedInstances, locked);
		modelBatch.end();
	}

	public static void update(float delta){
		float physicsDelta = Math.min(1f / 30f, delta);
		dynamicsWorld.stepSimulation(physicsDelta, 5, 1f / 60f);

		for (CollisionObject co : instances) {
			co.update();
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
		Ray ray = perspectiveCam.getPickRay(screenX, screenY);

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
		Die d = getObject((int)x, Gdx.graphics.getHeight()-(int)y); 
		if(d!=null){
			
			if(button==0){
				if(lockedDice.contains(d, true)){
					lockedDice.removeValue(d, true);
				}
				else{
					lockedDice.add(d);
				}
			}
			if(button==1){
				d.villager.dieRightClicked();
			}
			
		}


		}

	
}

