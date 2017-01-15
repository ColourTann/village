package tann.village.screens.gameScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;
import tann.village.bullet.BulletStuff;
import tann.village.screens.gameScreen.effect.Effect;
import tann.village.screens.gameScreen.effect.Effect.EffectSource;
import tann.village.screens.gameScreen.effect.Effect.EffectType;
import tann.village.screens.gameScreen.event.Event;
import tann.village.screens.gameScreen.event.EventPanel;
import tann.village.screens.gameScreen.panels.construction.ConstructionPanel;
import tann.village.screens.gameScreen.panels.inventory.InventoryPanel;
import tann.village.screens.gameScreen.panels.review.ReviewPanel;
import tann.village.screens.gameScreen.panels.roll.RollPanel;
import tann.village.screens.gameScreen.panels.stats.StatsPanel;
import tann.village.screens.gameScreen.villager.LevelupPanel;
import tann.village.screens.gameScreen.villager.Villager;
import tann.village.screens.gameScreen.villager.VillagerPanel;
import tann.village.screens.gameScreen.villager.die.Die;
import tann.village.util.Button;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Fonts;
import tann.village.util.Screen;

public class GameScreen extends Screen{
	public static final int BUTTON_BORDER=20;
	private static GameScreen self;
	public InventoryPanel inventoryPanel;
	public RollPanel rollButtonPanel;
	public StatsPanel statsPanel;
	public enum State{Event, Rolling, Review, Levelling}
	
	public State state;
	
	public Array<Villager> villagers = new Array<>();
	
	public static GameScreen get(){
		if(self==null) self= new GameScreen();
		return self;
	}

	public GameScreen() {
		setSize(Main.width, Main.height);
		addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(event.isHandled())return true;
				if(state==State.Rolling){
					BulletStuff.click(x, y, button);
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		addActor(inventoryPanel = new InventoryPanel());
		addActor(rollButtonPanel = new RollPanel());
		addActor(statsPanel = new StatsPanel());
		rollButtonPanel.setPosition(Main.width/2-rollButtonPanel.getWidth()/2, BUTTON_BORDER);
		statsPanel.setPosition(Main.width-statsPanel.getWidth()-BUTTON_BORDER, Main.height/2-statsPanel.getHeight()/2);
		for(int i=0;i<5;i++){
			villagers.add(new Villager());
		}
		refreshBulletStuff();
		setState(State.Rolling);
	}


	private void refreshBulletStuff() {
		BulletStuff.refresh(villagers);
	}


	TextureRegion bg = Main.atlas.findRegion("gamescreen");

	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.z_white);
		Draw.draw(batch, bg, 0, 0);
		Fonts.font.draw(batch, "state: "+state, 150, Main.height-Fonts.font.getAscent());
	}

	@Override
	public void postDraw(Batch batch) {
	}

	@Override
	public void preTick(float delta) {
	}

	@Override
	public void postTick(float delta) {
	}

	@Override
	public void keyPress(int keycode) {
		switch(keycode){
		case Input.Keys.SPACE:
				roll();
			break;
		}
	}
	
	public void roll(){
		if(state!=State.Rolling) return;
		if(rollButtonPanel.rollsLeft.getValue()<=0) return;
		if(!BulletStuff.finishedRolling()) return;
		rollButtonPanel.rollsLeft.changeValue(-1);
		for (Die d : BulletStuff.dice) {
			if(BulletStuff.lockedDice.contains(d, true)) continue;
			d.roll();
		}
	}

	public Array<Villager> villagersToLevelUp = new Array<>();
	
	public void proceed() {
		switch(state){
		case Event:
			setState(State.Rolling);
			break;
		case Rolling:
			if(!BulletStuff.finishedRolling()) return;
			setState(State.Review);
			break;
		case Review:
			if(villagersToLevelUp.size>0){
				levelup(villagersToLevelUp.removeIndex(0));
				break;
			}
			else{
				setState(State.Event);
			}
			break;
		}
	}
	
	private void levelup(Villager v){
		LevelupPanel lup = new LevelupPanel(v);
		addActor(lup);
		lup.setPosition(getWidth()/2, getHeight()/2, Align.center);
	}
	
	private void setState(State state) {
		this.state=state;
		switch(state){
		case Rolling:
			startRolling();
			break;
		case Review:
			finishRolling();
			showReview();
			break;
		case Event:
			showEvent();
			break;
		default:
			break;
			
		}
	
	}

	
	ReviewPanel reviewPanel;

	

	private void finishRolling() {
		if(!BulletStuff.finishedRolling()) return;
		
		
		refreshPanels();
		
		addEffect(new Effect(EffectType.Food, -5, EffectSource.Upkeep));
		for(Die d:BulletStuff.dice){
			d.activate();
		}
		
		showWisps();
		
		
		for(Die d: BulletStuff.dice){
			d.villager.gainXP(1);
			d.removeFromScreen();
		}
		BulletStuff.clearDice();
		
	}
	
	static int dayNum=1;

	EventPanel eventPanel;
	
	private void showEvent() {
		reviewPanel.remove();
		state=State.Event;
		Event event = Event.getRandomEvent();
		eventPanel= new EventPanel(event, ++dayNum);
		event.action();
		eventPanel.setPosition(Main.width/2-eventPanel.getWidth()/2, Main.height/2-eventPanel.getHeight()/2);
		addActor(eventPanel);
		addProceedButton(eventPanel);
	}

	private void startRolling() {
		BulletStuff.refresh(villagers);
		if(eventPanel!=null) eventPanel.remove();
		removeProceedButton();
		rollButtonPanel.setVisible(true);
		rollButtonPanel.rollsLeft.setValue(3);
		reviewPanel = new ReviewPanel(dayNum);
		state=State.Rolling;
		for(Die d: BulletStuff.dice){
			d.addToScreen();
		}
		roll();
	}
	
	private void showReview() {
		state=State.Review;
		reviewPanel.build();
		reviewPanel.setPosition(Main.width/2-reviewPanel.getWidth()/2, Main.height/2-reviewPanel.getHeight()/2);
		addActor(reviewPanel);
		addProceedButton(reviewPanel);
		rollButtonPanel.setVisible(false);
	}
	

	public void addEffect(Effect effect){
		reviewPanel.addItem(effect);
		inventoryPanel.activate(effect);
	}
	
	
	private Button proceedButton = new Button(200, 60, .8f, Main.atlas.findRegion("arrow"), Colours.dark, new Runnable() {
		public void run() {
			proceed();
		}
	});
	
	public void addProceedButton(Actor relativeTo){
		int dist = 10;
		proceedButton.setColor(Colours.green_light);
		addActor(proceedButton);
		proceedButton.setPosition(Main.width/2-proceedButton.getWidth()/2, relativeTo.getY()-proceedButton.getHeight()-dist);
	}
	
	public void removeProceedButton(){
		proceedButton.remove();
	}

	public void addVillagerPanel(Villager villager) {
		VillagerPanel panel = new VillagerPanel(villager);
		push(panel);
		panel.setPosition(getWidth()/2-panel.getWidth()/2, getHeight()/2-panel.getHeight()/2);
	}
	
	private static Actor inputBlocker;
	private static Array<Actor> stack = new Array<>();
	public void push(Actor a){
		
		if(inputBlocker==null){
			inputBlocker = new Actor();
			inputBlocker.setSize(Main.width, Main.height);
			inputBlocker.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					pop();
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
		
		stack.add(a);
		
		addActor(inputBlocker);
		addActor(a);
	}
	public void pop(){
		if(stack.size>0){
			stack.removeIndex(stack.size-1).remove();
		}
		
		if(stack.size==0){
			inputBlocker.remove();
		}
		else{
			stack.get(stack.size-1).toFront();
		}
	}

	public void refreshPanels() {
		inventoryPanel.clearWisps();
	}

	public void showWisps() {
		inventoryPanel.showWisps();
	}

	public void finishedLevellingUp() {
		proceed();
	}

	ConstructionPanel constructionPanel = new ConstructionPanel();
	
	public void openBuildingPanel() {
		constructionPanel.setPosition(Main.width/2-constructionPanel.getWidth()/2, Main.height/2-constructionPanel.getHeight()/2);
		push(constructionPanel);
	}
	
}
