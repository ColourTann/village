package tann.village;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Images {
	public static final TextureRegion food = Main.atlas.findRegion("resource/food");
	public static final TextureRegion food_storage = Main.atlas.findRegion("resource/crate");
	public static final TextureRegion brain = Main.atlas.findRegion("resource/brain");
	public static final TextureRegion wood = Main.atlas.findRegion("resource/wood");
	public static final TextureRegion morale = Main.atlas.findRegion("resource/morale");
	public static final TextureRegion fate = Main.atlas.findRegion("resource/fate");
	public static final TextureRegion level_up = Main.atlas.findRegion("resource/levelup");
	public static final TextureRegion boat_wheel = Main.atlas.findRegion("resource/ship_wheel");
	
	public static final TextureRegion roll = Main.atlas.findRegion("roll");
	public static final TextureRegion tick = Main.atlas.findRegion("tick");
	
	
	
	//3d
	
	public static final TextureRegion side_brain = Main.atlas_3d.findRegion("dice/face/brain");
	
	public static final TextureRegion side_food_1= Main.atlas_3d.findRegion("dice/face/food1");
	public static final TextureRegion side_food_2= Main.atlas_3d.findRegion("dice/face/food2");
	public static final TextureRegion side_food_3 = Main.atlas_3d.findRegion("dice/face/food3");
	
	public static final TextureRegion side_food_1_wood_1 = Main.atlas_3d.findRegion("dice/face/food1wood1");
	public static final TextureRegion side_wood_1 = Main.atlas_3d.findRegion("dice/face/wood1");
	public static final TextureRegion side_wood_2 = Main.atlas_3d.findRegion("dice/face/wood2");
	public static final TextureRegion side_wood_3 = Main.atlas_3d.findRegion("dice/face/wood3");
	
	public static final TextureRegion side_skull = Main.atlas_3d.findRegion("dice/face/nothing");
	
	public static final TextureRegion side_morale_1 = Main.atlas_3d.findRegion("dice/face/morale1");
	public static final TextureRegion side_morale_2_minus_2_food = Main.atlas_3d.findRegion("dice/face/morale2foodminus2");
	public static final TextureRegion side_morale_2 = Main.atlas_3d.findRegion("dice/face/morale2");
	
	public static final TextureRegion side_fate_1 = Main.atlas_3d.findRegion("dice/face/fate1");
	public static final TextureRegion side_fateForFood = Main.atlas_3d.findRegion("dice/face/fate1foodminus1");
	public static final TextureRegion side_fateForWood = Main.atlas_3d.findRegion("dice/face/fate1woodminus1");
	public static final TextureRegion side_2fateForWoodAndFood = Main.atlas_3d.findRegion("dice/face/fate2woodminus1foodminus1");
	
	public static final TextureRegion lapel = Main.atlas_3d.findRegion("dice/lapel/c");
	
	private static Map<String, TextureRegion[]> threeDTextures = new HashMap<>();
	private static TextureRegion[] makeFace(String name){
		TextureRegion base = Main.atlas_3d.findRegion("dice/face/"+name);
		TextureRegion highlight = Main.atlas_3d.findRegion("dice/face/"+name+"_highlight");
		return  new TextureRegion[]{base,highlight};
	}
	
	public static TextureRegion[] get(String name){
		TextureRegion[] tr = threeDTextures.get(name);
		if(tr==null){
			tr = makeFace(name);
			threeDTextures.put(name, tr);
		}
		return tr;
	}
}
