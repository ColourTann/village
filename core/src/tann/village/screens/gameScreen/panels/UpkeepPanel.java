package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.gameplay.effect.Eff;
import tann.village.screens.gameScreen.panels.eventStuff.EffectPanel;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.*;

public class UpkeepPanel extends Lay{

	static float EXTRA= 10;
    Array<Eff> effects = new Array<>();
	public UpkeepPanel() {
	    layout();
	}

	public void setEffects(Array<Eff> effects){
	    this.effects=effects;
	    layout();
    }

    @Override
    public void layout() {
        EXTRA = Main.h(2);
        clearChildren();
        TextBox title = new TextBox("Upkeep", Fonts.font, 599, Align.center);
        setSize(
                Math.max(title.getWidth(), (EffectPanel.staticWidth()+EXTRA)*Math.min(2,effects.size))+EXTRA,
                Main.h(8) + (EffectPanel.staticHeight()+Main.h(1)) * (Math.max(1, (effects.size-1)/2)));
        Layoo l = new Layoo(this);
        l.row(1);
        l.actor(title);
        l.row(2);
        l.abs(Main.h(1));
        for(int i=0; i<effects.size;i++){
            if(i%2==0 && i != 0){
                l.row(1);
            }
            Eff e=effects.get(i);
            l.actor(new EffectPanel(e));
            l.abs(Main.h(1));

        }
        l.row(1);
        l.layoo();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
            setY(getParent().getHeight()-getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        InfoPanel.drawBorder(batch, this);
        super.draw(batch, parentAlpha);
    }


}
