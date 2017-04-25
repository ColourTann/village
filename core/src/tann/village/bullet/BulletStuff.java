package tann.village.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.Array;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.panels.Die;
import tann.village.util.Colours;

public class BulletStuff {
	
	// holy shit tons of boilerplate haha
	public final static short GROUND_FLAG = 1 << 8;
	public final static short OBJECT_FLAG = 1 << 9;
	public final static short ALL_FLAG = -1;
	public static ShaderProgram shaderProgram;
	static PerspectiveCamera cam;
	static CameraInputController camController;
	static ModelBatch modelBatch;
	public static Array<ModelInstance> instances = new Array<>();
	public static Array<ModelInstance> walls = new Array<>();
	public static Array<Die> dice = new Array<>();
	static Model model;
	static CollisionObject ground;
	static btBroadphaseInterface broadphase;
	static btCollisionConfiguration collisionConfig;
	static btDispatcher dispatcher;
	static MyContactListener contactListener;
	public static btDynamicsWorld dynamicsWorld;
	static btConstraintSolver constraintSolver;
	static Shader shader;
	private static Vector3 dieClickPosition = new Vector3();

	public static void init(){
		Bullet.init();
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		constraintSolver = new btSequentialImpulseConstraintSolver();
		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		dynamicsWorld.setGravity(new Vector3(0, -30f, 0));
		contactListener = new MyContactListener();
		modelBatch = new ModelBatch();
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 8f, -2f);
		cam.lookAt(0, 0, 0);
		cam.update();
		camController = new CameraInputController(cam);
		
		ModelBuilder mb = new ModelBuilder();
		mb.begin();
		final float wallSize = 4.2f;
		final float wallThickness = 0.5f;
		mb.node().id = "ground";
		mb.part("ground", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,new Material(ColorAttribute.createDiffuse(Colours.green_light))).box(wallSize*2, wallThickness, wallSize*2);
		model = mb.end();
		
		ground = new CollisionObject(model, "ground", new btBoxShape(new Vector3(wallSize, wallThickness, wallSize)), 0);
		ground.userData=5;
		ground.body.userData=5;
		ground.transform.trn(0, wallSize, 0);
		dynamicsWorld.addRigidBody(ground.body, GROUND_FLAG, ALL_FLAG);

		for (int i = 0; i < 5; i++) {
			CollisionObject wall = new CollisionObject(model, "ground", new btBoxShape(new Vector3(wallSize, wallThickness, wallSize)), 0);
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
				wall.transform.trn(0, wallSize*1.5f, 0);
				break;
			}
			wall.initialUpdate();
			walls.add(wall);
			dynamicsWorld.addRigidBody(wall.body, OBJECT_FLAG, ALL_FLAG);
		}
		
		shader = new DieShader();
	    shader.init();
	}
	
	
	public static void refresh(Array<Villager> villagers) {
		dice.clear();
		for(Villager v:villagers){
			dice.add(v.die);
		}
	}
	
	public static final int mass = 1;

	public static void render() {
		camController.update();
	    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    modelBatch.begin(cam);
	    modelBatch.render(instances, shader);
	    modelBatch.end();
//	    modelBatch.render(walls);
	    
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

	public static Die getClickedDie (int screenX, int screenY) {
		Ray ray = cam.getPickRay(screenX, screenY);
		Die result = null;
		float distance = -1;

		for (Die d:dice) {
			final CollisionObject instance = d.physical;
			instance.updateBounds();

			instance.transform.getTranslation(dieClickPosition);
			dieClickPosition.add(instance.center);

			final float len = ray.direction.dot(dieClickPosition.x-ray.origin.x, dieClickPosition.y-ray.origin.y, dieClickPosition.z-ray.origin.z);
			if (len < 0f)
				continue;

			float dist2 = dieClickPosition.dst2(ray.origin.x+ray.direction.x*len, ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);
			if (distance >= 0f && dist2 > distance) 
				continue;

			if (dist2 <= instance.radius * instance.radius) {
				result = d;
				distance = dist2;
			}
//			Gdx.input.setInputProcessor(camController);
		}
		if(result==null) return null;
		return result;
	}

	public static void click(float x, float y, int button) {
		Die d = getClickedDie((int) x, Gdx.graphics.getHeight() - (int) y);
		if (d != null) {
			if (button == 0) {
				d.prepareToReroll();
			}
			if (button == 1) {
				d.villager.dieRightClicked();
			}

		}

	}
	
	public static boolean isFinishedRolling(){
		for (Die d : BulletStuff.dice) {
			if (!d.isStopped()) return false;
		}
		return true;
	}
}
