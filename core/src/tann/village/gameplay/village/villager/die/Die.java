package tann.village.gameplay.village.villager.die;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;

import tann.village.Images;
import tann.village.bullet.BulletStuff;
import tann.village.bullet.CollisionObject;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.village.RollManager;
import tann.village.gameplay.village.villager.Villager;
import tann.village.gameplay.village.villager.Villager.VillagerType;
import tann.village.gameplay.village.villager.die.Side;
import tann.village.util.Colours;
import tann.village.util.Maths;
import tann.village.util.Sounds;

public class Die {
	
	private static final float MAX_AIRTIME = 3f;
	public Villager villager;
	public VillagerType type;
	public CollisionObject physical;
	public TextureRegion lapel;
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

    // special weird die
    public Die(VillagerType type, Villager villager){
        this.villager=villager;
        this.type=type;
        setup(type);
        construct();
        glowOverride = true;
    }

    boolean glowOverride;

	public void setup(VillagerType type){
	    this.type=type;
        this.lapel = type.lapel;
	    for(Side s:type.sides){
	        addSide(s);
        }
	}
	
	public int getSide(){
	    if(glowOverride) return -1;
		if(rerolling) return 50;
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
	private static final float DIE_SIZE = 0.5f;
	private static final int ATTRIBUTES = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates|VertexAttributes.Usage.ColorPacked;
	private static Material MATERIAL;
	public void construct(){
		ModelBuilder mb = new ModelBuilder();
		mb.begin();
		mb.node().id = "die";
		
		if(MATERIAL==null){
			MATERIAL =new Material(TextureAttribute.createDiffuse(sides.get(0).tr[0].getTexture()));
		}
		
		MeshPartBuilder mpb = mb.part("die", GL20.GL_TRIANGLES, ATTRIBUTES, MATERIAL);
		float normalX = 0; // normalX stores the side number for flashing/fading
		float normalY = 0; // currently unused
		float[] f = new float[]{getFloat(4,5)}; // the lapels 
		float inner = f[(int)(Math.random()*f.length)];
		for(int i=0;i<6;i++){
			normalX=i;
			Side side = sides.get(i);
			TextureRegion base = side.tr[0];
			TextureRegion highlight = side.tr[1];
			mpb.setColor(getFloat(base), getFloat(highlight), inner, die/5f+0.1f);
			
			switch(i){
				case 0: mpb.rect(-DIE_SIZE, -DIE_SIZE, -DIE_SIZE, -DIE_SIZE, DIE_SIZE, -DIE_SIZE, DIE_SIZE, DIE_SIZE, -DIE_SIZE, DIE_SIZE, -DIE_SIZE, -DIE_SIZE, normalX, normalY, -1); break;
				case 1: mpb.rect(-DIE_SIZE, DIE_SIZE, DIE_SIZE, -DIE_SIZE, -DIE_SIZE, DIE_SIZE, DIE_SIZE, -DIE_SIZE, DIE_SIZE, DIE_SIZE, DIE_SIZE, DIE_SIZE, normalX, normalY, 1); break;
				case 2: mpb.rect(-DIE_SIZE, -DIE_SIZE, DIE_SIZE, -DIE_SIZE, -DIE_SIZE, -DIE_SIZE, DIE_SIZE, -DIE_SIZE, -DIE_SIZE, DIE_SIZE, -DIE_SIZE, DIE_SIZE, normalX, normalY, 0); break;
				case 3: mpb.rect(-DIE_SIZE, DIE_SIZE, -DIE_SIZE, -DIE_SIZE, DIE_SIZE, DIE_SIZE, DIE_SIZE, DIE_SIZE, DIE_SIZE, DIE_SIZE, DIE_SIZE, -DIE_SIZE, normalX, normalY, 0); break;
				case 4: mpb.rect(-DIE_SIZE, -DIE_SIZE, DIE_SIZE, -DIE_SIZE, DIE_SIZE, DIE_SIZE, -DIE_SIZE, DIE_SIZE, -DIE_SIZE, -DIE_SIZE, -DIE_SIZE, -DIE_SIZE, normalX, normalY, 0); break;
				case 5: mpb.rect(DIE_SIZE, -DIE_SIZE, -DIE_SIZE, DIE_SIZE, DIE_SIZE, -DIE_SIZE, DIE_SIZE, DIE_SIZE, DIE_SIZE, DIE_SIZE, -DIE_SIZE, DIE_SIZE, normalX, normalY, 0); break;
			}
			
		}
		Model model = mb.end();

		CollisionObject co = new CollisionObject(model, "die", new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f)),
				BulletStuff.mass);
		physical = co;
		float positionRand = 3.5f;
		co.transform.trn(MathUtils.random(-positionRand, positionRand), 1, MathUtils.random(-positionRand, positionRand)); // starting position
		co.body.setWorldTransform(co.transform);
		co.body.setCollisionFlags(
				co.body.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
		physical.body.setActivationState(4);
		co.body.setCollisionFlags(BulletStuff.OBJECT_FLAG);
		co.body.setContactCallbackFlag(BulletStuff.OBJECT_FLAG);
		co.body.setContactCallbackFilter(BulletStuff.OBJECT_FLAG);
		co.body.setActivationState(Collision.DISABLE_DEACTIVATION);
		die = die + 1;
		co.body.userData=this;
		physical.userData = this;
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
	
	public boolean rerolling;
	public void prepareToReroll(){
        if(!isStopped()) return;
		rerolling = !rerolling;
		if(rerolling){
            Sounds.playSound(Sounds.shake,.3f,1);
        }
        else{
            Sounds.playSound(Sounds.unshake,.3f,1);
        }
        RollManager.updateRolls();
    }
	
	float timeInAir;
	public void roll() {
		timeInAir=0;
		rerolling=false;
		unlock();
        physical.body.clearForces();
		randomise(12, 0, 7, 0, .7f, .7f);
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
		rerolling=false;
		for(Effect e:sides.get(getSide()).effects) e.activate(true);
	}

	public void addToScreen() {
		BulletStuff.instances.add(physical);
        physical.body.setLinearVelocity(new Vector3());
        physical.body.setAngularVelocity(new Vector3());
		BulletStuff.dynamicsWorld.addRigidBody(physical.body, BulletStuff.OBJECT_FLAG, BulletStuff.ALL_FLAG);
		physical.body.setContactCallbackFlag(BulletStuff.OBJECT_FLAG);
		physical.body.setContactCallbackFilter(0);
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
		return !isMoving() && position.y<1.01f;
	}
	
	int lockedSide=-1;
	private float glow=0;
	public void update(float delta){
		if(locked){
			glow = Math.max(0, glow-delta*1.0f);
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

	public Color getColour() {
	    if(villager==null) return Colours.dark;
		return villager.getColour(); 
	}
	

	private float[] texLocs = null;
	public float[] getTexLocs() {
		if(texLocs != null) return texLocs;
		texLocs = new float[26];
		float width = sides.get(0).tr[0].getTexture().getWidth();
		float height = sides.get(0).tr[0].getTexture().getHeight();
		for(int i=0;i<sides.size;i++){
			Side s = sides.get(i);
			texLocs[4*i] = s.tr[0].getRegionX()/width;
			texLocs[4*i+1] = s.tr[0].getRegionY()/height;
			texLocs[4*i+2] = s.tr[1].getRegionX()/width;
			texLocs[4*i+3] = s.tr[1].getRegionY()/height;
		}
		texLocs[24]=lapel.getRegionX()/width;
		texLocs[25]=lapel.getRegionY()/height;
		
		return texLocs;
	}

	boolean disposed;
    public void dispose() {
        if(disposed) System.err.println("WARNING: TRYING TO DISPOSE DIE AGAIN");
        disposed=true;
        removeFromScreen();
//        physical.dispose();
    }
}
