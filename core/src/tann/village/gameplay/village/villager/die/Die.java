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
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;

import tann.village.bullet.BulletStuff;
import tann.village.bullet.CollisionObject;
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.gameplay.village.villager.Villager.VillagerType;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.rollStuff.LockBar;
import tann.village.util.Colours;
import tann.village.util.Maths;
import tann.village.util.Sounds;

import static tann.village.gameplay.village.villager.die.Die.DieState.*;

public class Die {

    public enum DieState{Rolling, Stopped, Locked, Locking, Unlocking}

	public Villager villager;
    public VillagerType type;
    public CollisionObject physical;
    public Array<Side> sides = new Array<>();
    private static final float MAX_AIRTIME = 2.4f;
    private static final float INTERP_SPEED = .4f;

    // gameplay stuff

    private Vector3 startPos = new Vector3();
    private Vector3 targetPos;
    private Quaternion startQuat = new Quaternion();
    private Quaternion targetQuat = new Quaternion();

    private DieState state = DieState.Stopped;
    int lockedSide=-1;

    private float glow=0;
    float dist = 0;
    public void update(float delta){

        switch(state){
            case Stopped:
                glow = Math.max(0, glow-delta*1.0f);
                break;
            case Locking:
            case Unlocking:
                dist += delta/INTERP_SPEED;
                if(dist >= 1){
                    dist = 1;
                    if(state==Unlocking){
                        addToPhysics();
                        setState(Stopped);
                    }
                    else if(state== Locking){
                        setState(Locked);
                    }
                }
                dist = Math.min(1,dist);
                float interp = Interpolation.pow2Out.apply(dist);
                physical.transform.setToRotation(0,0,0,0);
                Vector3 thisFrame =startPos.cpy().lerp(targetPos, interp);
                physical.transform.setToTranslation(thisFrame);
                physical.transform.rotate(startQuat.cpy().slerp(targetQuat, interp));
                physical.body.setWorldTransform(physical.transform);
                break;
            case Locked:
                break;
            case Rolling:
                if(isStopped()){
                    setState(Stopped);
                    this.lockedSide=getSide();
                    glow = 1;
                    Village.get().activate(sides.get(lockedSide).effects, false, false);
                }
                else{
                    timeInAir+=delta;
                    if(timeInAir > MAX_AIRTIME){
                        jiggle();
                    }
                }
                break;
        }
    }

    public void click(){
        switch(state){
            case Rolling:
                break;
            case Stopped:
                Sounds.playSound(Sounds.shake,.3f,1);
                moveToTop();
                break;
            case Locked:
                for(Die d:BulletStuff.dice){
                    if(d.getState()==Rolling) return;
                }
                setState(Unlocking);
                removeFromPhysics();
                moveToBot();
                Sounds.playSound(Sounds.unshake,.3f,1);
                break;
            case Locking:
                break;
            case Unlocking:
                break;
        }
    }

    public DieState getState(){
        return state;
    }

    private void setState(DieState state) {
        this.state=state;
        switch(state){
            case Rolling:
                break;
            case Stopped:
                damp();
                break;
            case Locked:
                break;
            case Locking:
                break;
            case Unlocking:
                break;
        }
    }

    private void damp() {
        physical.body.setDamping(2, 50);
    }

    private void undamp(){
        physical.body.setDamping(0, 0);
    }

    public boolean isMoving(){
        return physical.isMoving();
    }

    Vector3 temp = new Vector3();
    Vector3 temp2 = new Vector3();
    Quaternion originalRotation = new Quaternion();

    private void moveToBot() {
        LockBar.get().removeDie(this);
        Vector3 best = getBestSpot();
        moveTo(best, originalRotation);
        undamp();
    }

    public void moveToTop() {
        glow=0;
        if(getState()==Stopped) physical.transform.getRotation(originalRotation);
        int index = LockBar.get().addDie(this);
        float width = 5;
        float x = -(width/(Village.STARTING_VILLAGERS-1)*index - width/2);
        moveTo(new Vector3(x, 0f, 6.55f), d6QuatsWithLean[lockedSide]);
        setState(Locking);
        removeFromPhysics();
    }

    private void moveTo(Vector3 position, Quaternion rotation){
        dist=0;
        startPos = physical.transform.getTranslation(startPos);
        targetPos = position;
        physical.transform.getRotation(startQuat);
        targetQuat = rotation;
    }

    private Vector3 getBestSpot() {
        float dist =0;
        float angle = 0;
        while(true){
            temp2.set((float)Math.sin(angle)*dist,1,(float)Math.cos(angle)*dist);
            boolean good = true;
            for(Die d:BulletStuff.dice){
                d.getPosition(temp);
                float xDiff = temp.x-temp2.x;
                float zDiff = temp.z-temp2.z;
                float dieDist = (float) Math.sqrt(xDiff*xDiff+zDiff*zDiff);
                if(dieDist < DIE_SIZE*2.8f){
                    good=false;
                    break;
                }
            }
            if(good) return temp2;
            dist+=.05f;
            angle += 5;
        }
    }


    float timeInAir;
    public void roll(boolean reroll) {
        if(!reroll){
            resetForRoll();
        }
        if(reroll && lockedSide>=0){
            Village.get().activate(sides.get(lockedSide).effects, false, true);
        }
        this.lockedSide=-1;
        setState(Rolling);
        undamp();

        timeInAir=0;
        physical.body.clearForces();
        randomise(13, 0, 7, 0, .9f, .7f);
    }

    private void resetForRoll() {
        randomiseStart();
        removeFromPhysics();
        addToPhysics();
        undamp();
    }

    public void jiggle(){
        timeInAir=0;
        randomise(4, 0, 3.5f, 0, 1, 0);
    }

    boolean glowOverride;

    // boring calculations

    static final float pitchAdd = -40;
    static final Quaternion[] d6QuatsWithLean = new Quaternion[]{
            new Quaternion().setEulerAngles(0,90+pitchAdd,90), // maybe wrong!
            new Quaternion().setEulerAngles(0,270+pitchAdd,270),
            new Quaternion().setEulerAngles(90,0,180+pitchAdd),
            new Quaternion().setEulerAngles(270,0,0-pitchAdd),
            new Quaternion().setEulerAngles(180,0-pitchAdd,270),  // maybe wrong!
            new Quaternion().setEulerAngles(0,0+pitchAdd,90)
    }; ;
    static final Quaternion[] d6Quats = new Quaternion[]{
            new Quaternion().setEulerAngles(0,90,90), // maybe wrong!
            new Quaternion().setEulerAngles(0,270,270),
            new Quaternion().setEulerAngles(90,0,180),
            new Quaternion().setEulerAngles(270,0,0),
            new Quaternion().setEulerAngles(180,0,270),  // maybe wrong!
            new Quaternion().setEulerAngles(0,0,90)
    };

	public int getSide(){
	    switch(state) {
            case Rolling:
                return -1;
            case Locked:
            case Locking:
            case Unlocking:
                return lockedSide;
            case Stopped:
                if (glowOverride) return -1;
                if (lockedSide >= 0) return lockedSide;
                if (!isStopped()) return -1;
                physical.update();
                physical.updateBounds();
                Quaternion rot = new Quaternion();
                physical.transform.getRotation(rot);
                Vector3 direction = new Vector3();
                direction.set(Vector3.Z);
                direction.mul(rot);
                float dot = Vector3.Y.dot(direction);
                if (dot > .9f) {
                    return 1;
                } else if (dot < -.9f) {
                    return 0;
                }
                direction.set(Vector3.X);
                direction.mul(rot);
                dot = Vector3.Y.dot(direction);
                if (dot > .9f) {
                    return 5;
                } else if (dot < -.9f) {
                    return 4;
                }
                direction.set(Vector3.Y);
                direction.mul(rot);
                dot = Vector3.Y.dot(direction);
                if (dot > .9f) {
                    return 3;
                } else if (dot < -.9f) {
                    return 2;
                }
                return -1;
        }
        return -1;
	}
	
	private float getFloat(TextureRegion tr){
		return getFloat(tr.getRegionX()/128, tr.getRegionY()/128);
	}
	
	private float getFloat(int x, int y){
		int num = x+16*(y);
		return num/255f+0.002f;
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

	private void getPosition(Vector3 out){
	    if(getState()==Locking || getState() == Unlocking){
	        out.set(targetPos);
        }
        else{
	        physical.transform.getTranslation(out);
        }
    }

	public void destroy() {
		removeFromScreen();
	}
	
	private boolean isStopped(){
		physical.transform.getTranslation(temp);
		return !isMoving() && temp.y<1.01f;
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
		texLocs[24]=villager.type.lapel.getRegionX()/width;
		texLocs[25]=villager.type.lapel.getRegionY()/height;
		
		return texLocs;
	}

	boolean disposed;
    public void dispose() {
        if(disposed) System.err.println("WARNING: TRYING TO DISPOSE DIE AGAIN");
        removeFromScreen();
        disposed=true;
    }

    public void removeFromScreen() {
        setState(Stopped);
        lockedSide=-1;
        BulletStuff.instances.removeValue(physical, true);
        BulletStuff.dynamicsWorld.removeRigidBody(physical.body);
        BulletStuff.dynamicsWorld.removeCollisionObject(physical.body);
    }

    public void removeFromPhysics(){
		BulletStuff.dynamicsWorld.removeRigidBody(physical.body);
		BulletStuff.dynamicsWorld.removeCollisionObject(physical.body);
	}

	private void addToPhysics() {
        removeFromPhysics();
		BulletStuff.dynamicsWorld.addRigidBody(physical.body, BulletStuff.OBJECT_FLAG, BulletStuff.ALL_FLAG);
	}

    public void addToScreen() {
        lockedSide=-1;
        BulletStuff.instances.add(physical);
        resetSpeeed();
        addToPhysics();
        physical.body.setContactCallbackFlag(BulletStuff.OBJECT_FLAG);
        physical.body.setContactCallbackFilter(0);
    }

    private void resetSpeeed() {
        physical.body.setLinearVelocity(new Vector3());
        physical.body.setAngularVelocity(new Vector3());
    }

    // setup stuff

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

    public Die(VillagerType type, Villager villager){
        this.villager=villager;
        this.type=type;
        setup(type);
        construct();
        glowOverride = true;
    }

    public void setup(VillagerType type){
        this.type=type;
        for(Side s:type.sides){
            addSide(s);
        }
    }

    public void addSide(Side side){
        Side copy = side.copy();
        sides.add(copy);
        for(Eff e:copy.effects) e.sourceDie=this;
    }

    static int dieIndex = 0;
    private static final float DIE_SIZE = 0.5f;
    private static final int ATTRIBUTES = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates|VertexAttributes.Usage.ColorPacked;
    private static Material MATERIAL;
    public void construct(){
        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        mb.node().id = "dieIndex";

        if(MATERIAL==null){
            MATERIAL =new Material(TextureAttribute.createDiffuse(sides.get(0).tr[0].getTexture()));
        }

        MeshPartBuilder mpb = mb.part("dieIndex", GL20.GL_TRIANGLES, ATTRIBUTES, MATERIAL);
        float normalX = 0; // normalX stores the side number for flashing/fading
        float normalY = 0; // currently unused
        float[] f = new float[]{getFloat(4,5)}; // the lapels
        float inner = f[(int)(Math.random()*f.length)];
        for(int i=0;i<6;i++){
            normalX=i;
            Side side = sides.get(i);
            TextureRegion base = side.tr[0];
            TextureRegion highlight = side.tr[1];
            mpb.setColor(getFloat(base), getFloat(highlight), inner, dieIndex /5f+0.1f);
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

        CollisionObject co = new CollisionObject(model, "dieIndex", new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f)),
                BulletStuff.mass);
        physical = co;
        randomiseStart();
        co.body.setCollisionFlags(
                co.body.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
        physical.body.setActivationState(4);
        co.body.setCollisionFlags(BulletStuff.OBJECT_FLAG);
        co.body.setContactCallbackFlag(BulletStuff.OBJECT_FLAG);
        co.body.setContactCallbackFilter(BulletStuff.OBJECT_FLAG);
        co.body.setActivationState(Collision.DISABLE_DEACTIVATION);
        dieIndex = dieIndex + 1;
        co.body.userData=this;
        physical.userData = this;
    }

    private void randomiseStart() {
        float positionRand = 3.5f;
        physical.transform.setToTranslation(MathUtils.random(-positionRand, positionRand), 1, MathUtils.random(-positionRand, positionRand)); // starting position
        physical.body.setWorldTransform(physical.transform);
        physical.body.setActivationState(4);
    }

}
