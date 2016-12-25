package tann.village.util;

import com.badlogic.gdx.Gdx;

import tann.village.Main;

public class Mouse {

	public static int getX(){
		return Gdx.input.getX()/Main.scale;
	}
	
	public static int getY(){
		return Main.height-(Gdx.input.getY()/Main.scale);
	}
}
