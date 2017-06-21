package tann.village.screens.gameScreen.panels.bottomBar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import tann.village.Main;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Lay;
import tann.village.util.TextBox;

public class BottomTextBarTab extends Lay {

    public Runnable runnable;
    String name;
    Color bg;
    public BottomTextBarTab(String name, Color bg, Runnable r) {
        this.runnable=r;
        this.name=name;
        this.bg=bg;
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(runnable!=null)runnable.run();
                return true;
            }
        });
        layout();
    }

    @Override
    public void layout() {
        clearChildren();
        TextBox tb = new TextBox(name, Fonts.fontSmall, 999, Align.center);
        float gap = Main.h(1);
        setSize(tb.getWidth()+gap*2, tb.getHeight()+gap*2);
        addActor(tb);
        tb.setPosition(gap, gap);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(bg);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }
}
