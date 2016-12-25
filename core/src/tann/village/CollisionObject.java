package tann.village;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;
import com.badlogic.gdx.utils.Disposable;

public class CollisionObject extends ModelInstance implements Disposable{
	public final btRigidBody body;
    public boolean moving = true;
	private Vector3 localInertia = new Vector3();
	public CollisionObject(Model model, String node, btCollisionShape shape, float mass) {
        super(model, node);
        if (mass > 0f)
            shape.calculateLocalInertia(mass, localInertia );
        else
        	localInertia.set(0, 0, 0);
        body = new btRigidBody(new btRigidBodyConstructionInfo(mass, null, shape, localInertia));
        body.setCollisionShape(shape);
        body.setWorldTransform(transform);
    }

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void update(){
		body.getWorldTransform(transform);
//		body.setWorldTransform(transform);
	}

}
