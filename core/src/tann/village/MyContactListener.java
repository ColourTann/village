package tann.village;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;

public class MyContactListener extends ContactListener {

	@Override
	public boolean onContactAdded(int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
//		if (userValue0 != 0)
//			((ColorAttribute) Main.instances.get(userValue0).materials.get(0).get(ColorAttribute.Diffuse)).color
//					.set(Color.WHITE);
//		if (userValue1 != 0)
//			((ColorAttribute) Main.instances.get(userValue1).materials.get(0).get(ColorAttribute.Diffuse)).color
//					.set(Color.WHITE);
		return true;
	}
}
