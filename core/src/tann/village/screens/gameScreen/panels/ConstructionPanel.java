package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

import tann.village.gameplay.island.BuildingGenerator;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.building.Building;
import tann.village.gameplay.village.Inventory;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.Layoo;

public class ConstructionPanel extends InfoPanel{

	Array<BuildingPanel> availables = new Array<>();
	static final float WIDTH = 550, HEIGHT = 300;
	public ConstructionPanel() {
		setSize(WIDTH, HEIGHT);

		Layoo l = new Layoo(this);
		l.row(1);
		l.gap(1);
		
		for(int i=0;i<3;i++){
			BuildingPanel bpan = new BuildingPanel();
			l.actor(bpan);
			availables.add(bpan);
			l.gap(1);
		}
		l.row(1);
		l.layoo();
		

		for(final BuildingPanel bp:availables){
			bp.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					attemptToBuy(bp.building);
					return super.touchDown(event, x, y, pointer, button);
				}
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
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
	
	public void attemptToBuy(Building b){
		// maybe have an inventory manager class to deal with this kind of thing.
		// doesn't really make sense to pass it onto gamescreen :P
		if(!Inventory.get().checkCost(b.cost)){
			return;
		}
		Inventory.get().spendCost(b.cost);
		Village.get().addBuilding(b);
		resetAvailablePanels();
		b.onBuild();
	}

	private void resetAvailablePanels() {
		int levelToGenerate = 0;
		levelToGenerate = Math.min(1, levelToGenerate);
		for(BuildingPanel bp:availables){
			bp.setBuilding(BuildingGenerator.random(levelToGenerate));
		}
	}
}
