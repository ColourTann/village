package tann.village.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import tann.village.screens.gameScreen.panels.review.InfoPanel;

public class TextButton extends InfoPanel{
    String text;
    public TextButton(float width, float height, String text) {
        this.text=text;
        setSize(width, height);
    }

    public void setRunnable(final Runnable runnable){
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                runnable.run();
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Fonts.draw(batch, text, Fonts.fontSmall, Colours.light, getX(), getY(), getWidth(), getHeight());
    }
}
