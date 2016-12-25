package tann.village.screens.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;

import tann.village.Main;
import tann.village.Main.TransitionType;
import tann.village.util.Border;
import tann.village.util.Button;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Screen;
import tann.village.util.Slider;
import tann.village.util.TannFont;

public class PauseScreen extends Group{
	private static int w=120,h=70;
	private static PauseScreen self;
	public static PauseScreen get(){
		if(self==null)self=new PauseScreen();
		return self;
	}
	
	private PauseScreen(){
		setSize(w,h);
		setPosition(Main.width/2-w/2, Main.height/2-h/2);
		
		addAttribution("willfor: music/sfx", "https://twitter.com/thewillformusic", (int)(getWidth()/2), getY(.81f), 70);
		addAttribution("tann: the rest", "https://twitter.com/colourtann", (int)(getWidth()/2), getY(.63f), 70);
		
		Slider.SFX.setPosition(w/2-Slider.SFX.getWidth()/2, getY(.25f));
		addActor(Slider.SFX);
		
		Slider.music.setPosition(w/2-Slider.SFX.getWidth()/2, getY(.4f));
		addActor(Slider.music);
		
		int numScales=5;
		int increment = w/(numScales+1);
		for(int i=0;i<numScales;i++) addScaleButton(i+1, increment*(i+1), getY(.13f), increment-2);
	}
	
	private void addScaleButton(final int scale, int x, int y, int width){
		Button t = new Button("X "+scale, width);
		t.setClickAction(new Runnable() {
			@Override
			public void run() {
				Main.self.setScale(scale);
			}
		});
		t.setPosition((int)(x-t.getWidth()/2), (int)(y-t.getHeight()/2));
		addActor(t);
	}

	private void addTransitionButton(String name, final Screen transitionTo, int x, int y, int width){
		Button t = new Button(name, width);
		
		t.setClickAction(new Runnable() {
			@Override
			public void run() {
				Main.self.setScreen(transitionTo, TransitionType.LEFT, Interpolation.pow2Out, .3f);
				Main.self.toggleMenu();
			}
		});
		t.setPosition((int)(x-t.getWidth()/2), (int)(y-t.getHeight()/2));
		addActor(t);
	}

	private void addAttribution(String text, final String url, int x, int y, int width){
		Button b = new Button(text, width);
		b.setPosition(x-b.getWidth()/2, y);
		b.setClickAction(new Runnable() {
			@Override
			public void run() {
				Gdx.net.openURI(url);
			}
		});
		addActor(b);		
	}
	
	private int getY(float ratio){
		return (int) (getHeight()*ratio);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.light);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		Border.draw(batch, getX(), getY(), getWidth(), getHeight(), false);
		
		batch.setColor(Colours.dark);
		TannFont.font.draw(batch, "v"+Main.version, (int)getX()+2, (int)(getY()-1+getHeight()-TannFont.font.getLineHeight()));
//		TannFont.font.draw(batch, "made by", (int)(getX()+getWidth()/2), (int)getY()+(getY(.9f)), Align.center);
		
		super.draw(batch, parentAlpha);
	}
}
