package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.village.Inventory;
import tann.village.gameplay.village.Village;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.EffectPanel;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.screens.gameScreen.panels.review.ReviewPanel;
import tann.village.util.*;

import java.util.ArrayList;
import java.util.List;

public class UpkeepPanel extends InfoPanel{

	static final int EXTRA= 10;


	public UpkeepPanel() {
		setBackground(Colours.brown_dark);
		build(new ArrayList<>());
	}
	

	public void build(List<Effect> effects){
		clear();
		TextBox title = new TextBox("Upkeep", Fonts.font, 599, Align.center);
		setSize(Math.max(title.getWidth(), (EffectPanel.WIDTH+EXTRA)*effects.size())+EXTRA, InventoryPanel.HEIGHT);
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(title);
		l.row(1);
		l.abs(-ReviewPanel.SMALL_GAP);
		for(Effect e:effects){
			l.abs(ReviewPanel.SMALL_GAP);
			l.actor(new EffectPanel(e));
			
		}
		l.row(1);
		l.layoo();
	}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }
}
