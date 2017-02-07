package tann.village.screens.gameScreen.panels.construction;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.screens.gameScreen.building.Building;
import tann.village.screens.gameScreen.building.BuildingPanel;
import tann.village.screens.gameScreen.panels.inventory.Inventory;
import tann.village.screens.gameScreen.panels.review.InfoPanel;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Layoo;
import tann.village.util.TextBox;

public class ConstructionPanel extends InfoPanel{

	Array<BuildingPanel> slots = new Array<>();
	Array<BuildingPanel> availables = new Array<>();
	BuildingPanel currentSlot;
	static final float WIDTH = 600, HEIGHT = Main.height*.9f;
	public ConstructionPanel() {
		setSize(WIDTH, HEIGHT);
		TextBox available = new TextBox("Available buildings", Fonts.font,-1, Align.center);
		
		TextBox your = new TextBox("Your buildings", Fonts.font,-1, Align.center);

		Layoo l = new Layoo(this);
		l.row(1);

		l.actor(available);
		l.row(1);
		l.gap(1);
		for(int i=0;i<3;i++){
			BuildingPanel bpan = new BuildingPanel(Building.random());
			l.actor(bpan);
			availables.add(bpan);
			l.gap(1);
		}
		l.row(3);
		l.actor(your);
		l.row(1);
		l.gap(1);
		for(int i=0;i<3;i++){
			BuildingPanel slot = new BuildingPanel();
			slots.add(slot);
			l.actor(slot);
			l.gap(1);
		}
		l.row(1);
		l.layoo();
		
		setSlot(slots.get(0));

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
		
		
	}
	
	private void setSlot(BuildingPanel slot){
		if(currentSlot!=null){
			currentSlot.highlight(false);
		}
		currentSlot=slot;
		currentSlot.highlight(true);
	}
	
	private void incrementSlot(){
		setSlot(slots.get((slots.indexOf(currentSlot, true)+1)%slots.size));
	}	
	
	public void attemptToBuy(Building b){
		// maybe have an inventory manager class to deal with this kind of thing.
		// doesn't really make sense to pass it onto gamescreen :P
		if(!Inventory.get().checkCost(b.cost)){
			return;
		}
		Inventory.get().spendCost(b.cost);
		currentSlot.setBuilding(b);
		resetAvailablePanels();
		incrementSlot();
		b.onBuild();
	}

	private void resetAvailablePanels() {
		for(BuildingPanel bp:availables){
			bp.setBuilding(Building.random());
		}
	}

	
	public void upkeep() {
			for(BuildingPanel b:slots){
				if(b.building!=null){
					b.building.upkeep();
				}
			}
	}

	

}
