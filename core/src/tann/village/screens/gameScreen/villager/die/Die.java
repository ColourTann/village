package tann.village.screens.gameScreen.villager.die;

import java.util.HashMap;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;

import tann.village.Images;
import tann.village.bullet.BulletStuff;
import tann.village.bullet.CollisionObject;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.villager.Villager;
import tann.village.screens.gameScreen.villager.Villager.VillagerType;
import tann.village.util.Colours;
import tann.village.util.Maths;
import tann.village.util.Particle;

public class Die {
	
	private static final float MAX_AIRTIME = 3f;
	private static HashMap<Long, Die> identityMap = new HashMap<>();
	public Villager villager;
	public VillagerType type;
	public Die(Villager villager) {
		this.villager=villager;
		setup(villager.type);
		construct();
	}
	
	public Die(VillagerType type){
		setup(type);
		this.type=type;
		construct();
	}
	
	public void setup(VillagerType type){
		switch(type){
		
			// level 0
		case Villager:
			addSide(Side.food1);
			addSide(Side.food1);
			addSide(Side.wood1);
			addSide(Side.wood1);
			addSide(Side.brain);
			addSide(Side.skull);
			break;
			
			// level 1
//		case Fisher:
//			addSide(Side.food1);
//			addSide(Side.food1);
//			addSide(Side.food2);
//			addSide(Side.food2);
//			addSide(Side.wood1);
//			addSide(Side.brain);
//			break;
//		case Musician:
//			addSide(Side.morale1);
//			addSide(Side.food1);
//			addSide(Side.wood1);
//			addSide(Side.food2);
//			addSide(Side.wood1);
//			addSide(Side.brain);
//			break;
//		case Mystic:
//			addSide(Side.fateForFood);
//			addSide(Side.fateForWood);
//			addSide(Side.wood1);
//			addSide(Side.food1);
//			addSide(Side.skull);
//			addSide(Side.brain);
//			break;
//		case Chopper:
//			addSide(Side.food1);
//			addSide(Side.wood1);
//			addSide(Side.wood2);
//			addSide(Side.wood3);
//			addSide(Side.skull);
//			addSide(Side.brain);
//			break;
//		case Gatherer:
//			addSide(Side.food1);
//			addSide(Side.food1);
//			addSide(Side.food2);
//			addSide(Side.wood1);
//			addSide(Side.wood2);
//			addSide(Side.brain);
//			break;
//			
//		// level 2
//		case Builder:
//			addSide(Side.food1);
//			addSide(Side.food2);
//			addSide(Side.wood2);
//			addSide(Side.wood3);
//			addSide(Side.wood3);
//			addSide(Side.brain);
//			break;
//		case Explorer:
//			addSide(Side.food3);
//			addSide(Side.wood3);
//			addSide(Side.morale1);
//			addSide(Side.food1wood1);
//			addSide(Side.brain);
//			addSide(Side.skull);
//			break;
//		case Farmer:
//			addSide(Side.food1wood1);
//			addSide(Side.food1wood1);
//			addSide(Side.food2);
//			addSide(Side.food3);
//			addSide(Side.food3);
//			addSide(Side.brain);
//			break;
//		case FateWeaver:
//			addSide(Side.fate1);
//			addSide(Side.fate1);
//			addSide(Side.fate2ForWoodAndFood);
//			addSide(Side.food2);
//			addSide(Side.skull);
//			addSide(Side.brain);
//			break;
//		case Leader:
//			addSide(Side.morale2);
//			addSide(Side.morale2);
//			addSide(Side.food2);
//			addSide(Side.food2);
//			addSide(Side.wood2);
//			addSide(Side.brain);
//			break;
		default:
			break;
			
		}
	}

	public static Die getDie(long userValue){
		return identityMap.get(userValue);
	}
	
	public CollisionObject physical;
	
	
	public int getSide(){
		if(lockedSide >=0) return lockedSide;
		if(!isStopped()) return -1;
		physical.update();
		physical.updateBounds();
		Quaternion rot = new Quaternion();
		physical.transform.getRotation(rot);
		Vector3 direction = new Vector3();
		
		direction.set(Vector3.Z);
		direction.mul(rot);
		float dot = Vector3.Y.dot(direction);
		
		if(dot>.9f){
			return 1;
		}
		else if (dot<-.9f){
			return 0;
		}
		direction.set(Vector3.X);
		direction.mul(rot);
		dot = Vector3.Y.dot(direction);
		
		if(dot>.9f){
			return 5;
		}
		else if (dot<-.9f){
			return 4;
		}
		
		direction.set(Vector3.Y);
		direction.mul(rot);
		dot = Vector3.Y.dot(direction);
		
		if(dot>.9f){
			return 3;
		}
		else if (dot<-.9f){
			return 2;
		}
		
		return -1;
	}
	
	public Array<Side> sides = new Array<>();
	
	public void addSide(Side side){
		Side copy = side.copy();
		sides.add(copy);
		for(Effect e:copy.effects) e.sourceDie=this;
	}
	
	static int die = 0;
	
	public void construct(){
		ModelBuilder mb = new ModelBuilder();
		float amt = .5f;
		mb.begin();
		
		
		
		int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
				| VertexAttributes.Usage.TextureCoordinates|VertexAttributes.Usage.ColorPacked;
		mb.node().id = "die";
		
		
		VertexAttribute va = new VertexAttribute(Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE);
		
		
		
		Material m =new Material(TextureAttribute.createDiffuse(sides.get(0).tr[0].getTexture()));
		sides.get(0).tr[0].getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		MeshPartBuilder mpb = mb.part("die", GL20.GL_TRIANGLES, attr,m);
		float normalX = 0;
		float normalY = 0;
//		float[] f = new float[]{getFloat(5,6),getFloat(4,5),getFloat(3, 4),getFloat(2,3),getFloat(1, 2),getFloat(0,0)};
		float[] f = new float[]{getFloat(3,4)};
		float inner = f[(int)(Math.random()*f.length)];
		for(int i=0;i<6;i++){
			normalX=i;
			Side side = sides.get(i);
			TextureRegion base = side.tr[0];
			TextureRegion highlight = side.tr[1];
			mpb.setColor(getFloat(base), getFloat(highlight), inner, die/5f+0.1f);
			
			switch(i){
				case 0: mpb.rect(-amt, -amt, -amt, -amt, amt, -amt, amt, amt, -amt, amt, -amt, -amt, normalX, normalY, -1); break;
				case 1: mpb.rect(-amt, amt, amt, -amt, -amt, amt, amt, -amt, amt, amt, amt, amt, normalX, normalY, 1); break;
				case 2: mpb.rect(-amt, -amt, amt, -amt, -amt, -amt, amt, -amt, -amt, amt, -amt, amt, normalX, normalY, 0); break;
				case 3: mpb.rect(-amt, amt, -amt, -amt, amt, amt, amt, amt, amt, amt, amt, -amt, normalX, normalY, 0); break;
				case 4: mpb.rect(-amt, -amt, amt, -amt, amt, amt, -amt, amt, -amt, -amt, -amt, -amt, normalX, normalY, 0); break;
				case 5: mpb.rect(amt, -amt, -amt, amt, amt, -amt, amt, amt, amt, amt, -amt, amt, normalX, normalY, 0); break;
			}
			
		}
		Model model = mb.end(); 
		
		
		
		
		
		model.getNode("die").parts.get(0).setRenderable(BulletStuff.renderable);
		
		
		CollisionObject co = null;
		co = new CollisionObject(model, "die", new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f)), BulletStuff.mass);
		co.transform.trn(MathUtils.random(-2.5f, 2.5f), 1.5f, MathUtils.random(-2.5f, 2.5f));
		co.body.setWorldTransform(co.transform);
		co.body.setUserValue(BulletStuff.instances.size);
		co.body.setCollisionFlags(	
				co.body.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
		physical=co;
		physical.body.setActivationState(4);
		die = die+1;
		
		co.userData=this;
	}
	
	private float getFloat(TextureRegion tr){
		return getFloat(tr.getRegionX()/128, tr.getRegionY()/128);
	}
	
	private float getFloat(int x, int y){
		int num = x+16*(y);
		return num/255f+0.002f;
	}
	
	public boolean isMoving(){
		return physical.isMoving();
	}
	
	static int count;
	
	float timeInAir;
	public void roll() {
		timeInAir=0;
		unlock();
		randomise(12, 0, 6, 0, .7f, .7f);
	}
	
	public void jiggle(){
		timeInAir=0;
		randomise(5, 0, 2, 0, 1, 0);
	}

	private void unlock(){
		locked=false;
		lockedSide=-1;
		physical.body.setDamping(0, 0);
	}
	
	private void randomise(float up, float upRand, float side, float sideRand, float rot, float rotRand){
		float x = (float)(side + Maths.factor(sideRand))*Maths.mult();
		float y = (float)(up + Maths.factor(upRand));
		float z = (float)(side + Maths.factor(sideRand))*Maths.mult();
		float r1 = (float)(rot + Maths.factor(rotRand))*Maths.mult();
		float r2 = (float)(rot + Maths.factor(rotRand))*Maths.mult();
		float r3 = (float)(rot + Maths.factor(rotRand))*Maths.mult();
		applyForces(x, y, z, r1, r2, r3);
	}
	
	
	
	private void applyForces(float x, float y, float z, float r1, float r2, float r3){
		physical.body.applyCentralImpulse(new Vector3(x, y, z));
		physical.body.applyTorqueImpulse(new Vector3(r1, r2, r3));
	}
	
	public void activate() {
		for(Effect e:sides.get(getSide()).effects) e.activate();
	}

	public void addToScreen() {
		BulletStuff.instances.add(physical);
		BulletStuff.dynamicsWorld.addRigidBody(physical.body, BulletStuff.OBJECT_FLAG, BulletStuff.ALL_FLAG);
		
	}

	public void removeFromScreen() {
		BulletStuff.instances.removeValue(physical, true);
		BulletStuff.dynamicsWorld.removeRigidBody(physical.body);
	}

	public void destroy() {
		removeFromScreen();
	}
	
	Vector3 position = new Vector3();
	public boolean isStopped(){
		physical.transform.getTranslation(position);
		return !isMoving() && position.y<1;
	}
	
	int lockedSide=-1;
	private float glow=0;
	public void update(float delta){
		if(locked){
			glow = Math.max(0, glow-delta*1.1f);
		}
		else if(isStopped()){
			lock();
		}
		else{
			timeInAir+=delta;
			if(timeInAir > MAX_AIRTIME){
				jiggle();
			}
		}
	}
	
	boolean locked;
	public void lock(){
		if (locked) return;
		lockedSide = getSide();
		locked = true;
		physical.body.setDamping(2, 50);
		glow = 1;
	}
	
	public float getGlow(){
		return glow;
	}
}
