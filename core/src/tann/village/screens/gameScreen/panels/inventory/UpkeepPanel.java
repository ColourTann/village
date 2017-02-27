package tann.village.screens.gameScreen.panels.inventory;

import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;
import tann.village.screens.gameScreen.panels.EffectPanel;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.screens.gameScreen.panels.review.ReviewPanel;
import tann.village.util.Colours;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class UpkeepPanel extends InfoPanel{

	static final int WIDTH=150, HEIGHT=80;
	static final int EXTRA= 20;
	
	Array<Effect> effects = new Array<>();
	
	public UpkeepPanel() {
		setBackground(Colours.brown_dark);
	}
	
	public void clearEffects(){
		effects.clear();
	}
	
	public void addEffect(Effect effect){
		boolean added = false;
		for(Effect existing:effects){
			if(existing.type == effect.type){
				existing.value+=effect.value;
				added=true;
				break;
			}
		}
		if(!added) effects.add(effect);
		build();
	}
	
	public void build(){
		clear();
		TextBox title = new TextBox("Upkeep", Fonts.font, WIDTH, Align.center);
		setSize(Math.max(title.getWidth(), EffectPanel.WIDTH)+EXTRA, title.getHeight()+EffectPanel.HEIGHT+EXTRA);
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

	public void activate() {
		for(Effect e:effects){
			GameScreen.get().addEffect(e);
		}
	}
	
}