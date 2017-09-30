package tann.village.screens.mapScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import tann.village.Images;
import tann.village.gameplay.island.islands.Island;
import tann.village.screens.gameScreen.panels.inventoryStuff.MoraleCompass;
import tann.village.util.*;

public class MapScreen extends Screen{

	Map map;
	private static MapScreen self;
	public static MapScreen get(){
		if(self == null){
			self = new MapScreen();
		}
		return self;
	}
	
	public MapScreen() {
		map = new Map();
		for(Island i:map.islands){
			addActor(i.getActor());
		}
		TextButton tb = new TextButton(300, 50, "Reset Save Data");
		tb.setRunnable(new Runnable() {
            @Override
            public void run() {
                Prefs.RESETSAVEDATA();
            }
        });
		addActor(tb);
		tb.setPosition(getWidth()-tb.getWidth(),0);

        MoraleCompass mc = new MoraleCompass();
        addActor(mc);
        mc.setPosition(100,100);
	}
	
	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.blue_dark);
		Draw.fillActor(batch, this);
	}

	@Override
	public void postDraw(Batch batch) {
	}

	@Override
	public void preTick(float delta) {
	}

	@Override
	public void postTick(float delta) {
	}

	@Override
	public void keyPress(int keycode) {
		switch(keycode){
		}
	}

    @Override
    public void layout() {

    }
}
