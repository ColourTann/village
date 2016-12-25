package tann.village;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectWrapper;

public class MyContactListener extends ContactListener{
	
	
	 @Override
     public boolean onContactAdded (int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
		 if (userValue1 == 0)
             Main.instances.get(userValue0).moving = false;
         else if (userValue0 == 0)
        	 Main.instances.get(userValue1).moving = false;
         return true;
     }

}
