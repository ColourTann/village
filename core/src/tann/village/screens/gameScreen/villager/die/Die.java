package tann.village.screens.gameScreen.villager.die;

import java.util.HashMap;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;

import tann.village.bullet.BulletStuff;
import tann.village.bullet.CollisionObject;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.villager.Villager;
import tann.village.screens.gameScreen.villager.Villager.VillagerType;
import tann.village.util.Colours;
import tann.village.util.Particle;

public class Die {
	
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
		case Villager:
			addSide(Side.food1);
			addSide(Side.food1);
			addSide(Side.wood1);
			addSide(Side.wood1);
			addSide(Side.brain);
			addSide(Side.skull);
			break;
			
			// level 2
		case Fisher:
			addSide(Side.food1);
			addSide(Side.food1);
			addSide(Side.food2);
			addSide(Side.food2);
			addSide(Side.wood1);
			addSide(Side.brain);
			break;
		case Musician:
			addSide(Side.morale1);
			addSide(Side.morale1);
			addSide(Side.food1);
			addSide(Side.food1);
			addSide(Side.wood1);
			addSide(Side.brain);
			break;
		case FateWeaver:
			addSide(Side.fateForFood);
			addSide(Side.fateForWood);
			addSide(Side.wood1);
			addSide(Side.food1);
			addSide(Side.food1);
			addSide(Side.brain);
			break;
		}
	}

	public static Die getDie(long userValue){
		return identityMap.get(userValue);
	}
	
	public CollisionObject physical;
	
	
	public int getSide(){
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
		
		return -99;
	}
	
	public Array<Side> sides = new Array<>();
	
	public void addSide(Side side){
		Side copy = side.copy();
		sides.add(copy);
		for(Effect e:copy.effects) e.sourceDie=this;
	}
	
	public void construct(){
		ModelBuilder mb = new ModelBuilder();
		float amt = .5f;
		mb.begin();
		int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
				| VertexAttributes.Usage.TextureCoordinates;
		mb.node().id = "die";
		MeshPartBuilder mpb = mb.part("die", GL20.GL_TRIANGLES, attr,
				new Material(TextureAttribute.createDiffuse(sides.get(0).tr.getTexture())));
		mpb.setUVRange(sides.get(0).tr);
		mpb.rect(-amt, -amt, -amt, -amt, amt, -amt, amt, amt, -amt, amt, -amt, -amt, 0, 0, -1);
		mpb.setUVRange(sides.get(1).tr);
		mpb.rect(-amt, amt, amt, -amt, -amt, amt, amt, -amt, amt, amt, amt, amt, 0, 0, 1);
		mpb.setUVRange(sides.get(2).tr);
		mpb.rect(-amt, -amt, amt, -amt, -amt, -amt, amt, -amt, -amt, amt, -amt, amt, 0, -1, 0);
		mpb.setUVRange(sides.get(3).tr);
		mpb.rect(-amt, amt, -amt, -amt, amt, amt, amt, amt, amt, amt, amt, -amt, 0, 1, 0);
		mpb.setUVRange(sides.get(4).tr);
		mpb.rect(-amt, -amt, amt, -amt, amt, amt, -amt, amt, -amt, -amt, -amt, -amt, -1, 0, 0);
		mpb.setUVRange(sides.get(5).tr);
		mpb.rect(amt, -amt, -amt, amt, amt, -amt, amt, amt, amt, amt, -amt, amt, 1, 0, 0);
		
		Model model = mb.end();
		
		
		CollisionObject co = null;
		co = new CollisionObject(model, "die", new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f)), BulletStuff.mass);
		co.transform.trn(MathUtils.random(-2.5f, 2.5f), 1.5f, MathUtils.random(-2.5f, 2.5f));
		co.body.setWorldTransform(co.transform);
		co.body.setUserValue(BulletStuff.instances.size);
		co.body.setCollisionFlags(
				co.body.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
		physical=co;
		physical.body.setActivationState(4);
		
	}
	
	public boolean isMoving(){
		return physical.isMoving();
	}
	
	static int count;
	public void roll() {
		float sideways = 7;
		float upwards = 12;
		physical.body.applyCentralImpulse(new Vector3(Particle.rand(-sideways, sideways), upwards, Particle.rand(-sideways, sideways)));
		float rotationalForce = 2.0f;
		physical.body.applyTorqueImpulse(new Vector3(Particle.rand(-rotationalForce, rotationalForce),Particle.rand(-rotationalForce, rotationalForce),Particle.rand(-rotationalForce, rotationalForce)));
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
}
