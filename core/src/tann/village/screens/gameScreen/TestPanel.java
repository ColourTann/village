package tann.village.screens.gameScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import tann.village.Images;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.ImageGap;
import tann.village.util.Layoo;

public class TestPanel extends Group{
	
	public TestPanel() {
		setSize(300, 500);
		Layoo l = new Layoo(this);
		l.row(1);
		l.actor(new ImageGap(Draw.getSq(), 50, 150, 0));
		l.row(1);
		l.add(1, new ImageGap(Draw.getSq(), 50, 150, 0), 1, new ImageGap(Draw.getSq(), 50, 150, 0), 1);
		l.row(1);
		l.add(1, new ImageGap(Draw.getSq(), 10, 150, 0), 1, new ImageGap(Draw.getSq(), 50, 10, 0), 1, new ImageGap(Draw.getSq(), 50, 150, 0), 1);
		l.row(5);
		l.layoo();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}

}
