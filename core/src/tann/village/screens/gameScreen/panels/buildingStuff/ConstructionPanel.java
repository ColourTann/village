package tann.village.screens.gameScreen.panels.buildingStuff;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.project.Project;
import tann.village.screens.gameScreen.GameScreen;
import tann.village.screens.gameScreen.panels.eventStuff.CostTab;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.*;

public class ConstructionPanel extends InfoPanel{

	Array<ProjectPanel> availables = new Array<>();
	static final float WIDTH = 650, HEIGHT = 350;
	public ConstructionPanel() {
		setSize(WIDTH, HEIGHT);

		Layoo l = new Layoo(this);
        l.row(1);
        Fonts.font.setColor(Colours.light);
        TextBox title = new TextBox("choose a project", Fonts.font, WIDTH, Align.center);
        l.actor(title);
        l.row(1);
        l.absRow(CostTab.height());
		l.gap(1);
		
		for(int i=0;i<3;i++){
			ProjectPanel bpan = new ProjectPanel();
			l.actor(bpan);
			availables.add(bpan);
			l.gap(1);
		}
        l.absRow(UnlockedByPanel.height());
		l.row(1);
		l.layoo();
		

		for(final ProjectPanel bp:availables){
			bp.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				    if(GameScreen.get().state!= GameScreen.State.Rolling) return false;
					attemptToBuy(bp.building);
					return super.touchDown(event, x, y, pointer, button);
				}
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if(GameScreen.get().state!= GameScreen.State.Rolling) return;
					bp.highlight(true);
					super.enter(event, x, y, pointer, fromActor);
				}
				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					bp.highlight(false);
					super.exit(event, x, y, pointer, toActor);
				}
			});
		}

		resetAvailablePanels();

	}
	
	public void attemptToBuy(Project b){
		// maybe have an inventory manager class to deal with this kind of thing.
		// doesn't really make sense to pass it onto gamescreen :P
		if(!Village.getInventory().checkCost(b.cost)){
			return;
		}
		Village.getInventory().spendCost(b.cost);
		startProject(b);
	}

	private void startProject(Project p ){
        Village.get().addBuilding(p);
        p.onCommence();
        GameScreen.get().checkEnd();
        built=true;
        for(ProjectPanel bp:availables){
            bp.setVisible(false);
        }
    }

	private void resetAvailablePanels() {
		int levelToGenerate = 0;
		levelToGenerate = Math.min(1, levelToGenerate);
		for(ProjectPanel bp:availables){
		    bp.setVisible(true);
			bp.setProject(GameScreen.get().island.getRandomBuilding());
		}
	}

	boolean built;
	public void turn(){
	    if(built){
	        built=false;
	        resetAvailablePanels();
        }
    }

    @Override
    public void clipEnd() {
        Sounds.playSound(Sounds.cancel, 1, 1);
    }

    public static float staticHeight() {
	    return 300;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(built){
            Fonts.draw(batch, "Come back next turn", Fonts.font, Colours.light, getX(), getY(), getWidth(), getHeight(), Align.center);
        }
        else if(GameScreen.get().state!= GameScreen.State.Rolling){
            batch.setColor(0,0,0,.6f);
            Draw.fillActor(batch,this);
            batch.setColor(Colours.dark);
            float w = 550;
            float h = 50;
            Draw.fillRectangle(batch, getX()+getWidth()/2-w/2, getY()+getHeight()/2-h/2, w, h);
            Fonts.draw(batch, "Can only build while rolling", Fonts.font, Colours.light, getX(), getY(), getWidth(), getHeight(), Align.center);
        }
    }
}
