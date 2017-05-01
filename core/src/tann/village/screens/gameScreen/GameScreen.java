package tann.village.screens.gameScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.Images;
import tann.village.Main;
import tann.village.bullet.BulletStuff;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.islands.TutorialIsland;
import tann.village.gameplay.village.RollManager;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.panels.*;
import tann.village.gameplay.village.Inventory;
import tann.village.screens.gameScreen.panels.review.LossPanel;
import tann.village.screens.gameScreen.panels.review.ReviewPanel;
import tann.village.screens.gameScreen.panels.review.StarvationPanel;
import tann.village.screens.gameScreen.panels.review.LossPanel.LossReason;
import tann.village.screens.gameScreen.panels.stats.StatsPanel;
import tann.village.util.*;

public class GameScreen extends Screen{
	public static final int BUTTON_BORDER=10;
	private static GameScreen self;
	public RollPanel rollButtonPanel;
	public StatsPanel statsPanel;
	public enum State{Event, Rolling, Review, Levelling}
	public State state;
	private static final int STARTING_VILLAGERS = 5;
	public Array<Villager> villagers = new Array<>();
	
	TextureRegion bg = Main.atlas.findRegion("gamescreen");
	public Array<Villager> villagersToLevelUp = new Array<>();
	ReviewPanel reviewPanel = new ReviewPanel(dayNum);
	static int dayNum=0;
	EventPanel eventPanel;
	ConstructionPanel constructionPanel = new ConstructionPanel();
	
	public static GameScreen get(){
		if(self==null){
			self= new GameScreen();
		}
		return self;
	}

	public GameScreen() {
		
	}
	
	public Island island;
	public Village village;
	public void init(Island island, Village village){
		this.village=village;
		this.island=island;
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
		Group inventoryGroup = Inventory.get().getGroup();
		addActor(inventoryGroup);
		inventoryGroup.setPosition(0, (getHeight()-inventoryGroup.getHeight())/2);
		addActor(statsPanel = new StatsPanel());
		statsPanel.setPosition(Main.width-statsPanel.getWidth()-BUTTON_BORDER, Main.height/2-statsPanel.getHeight()/2);
		for(int i=0;i<STARTING_VILLAGERS;i++){
			villagers.add(new Villager(i));
		}
		refreshBulletStuff();
		upkeepPanel.addEffect(new Effect(EffectType.Food, -2, EffectSource.Upkeep));
		upkeepPanel.build();
		addActor(upkeepPanel);
		upkeepPanel.setPosition(BUTTON_BORDER, getHeight()-BUTTON_BORDER-upkeepPanel.getHeight());
		setState(State.Event);
		Sounds.playMusic(Sounds.get("beach", Music.class));

        CircleButton cButt = new CircleButton(0, 0, 180, Colours.dark);
        cButt.setTexture(Images.hammer, 0.7f, .7f, 80, 80);
        cButt.setClickAction(new Runnable() {
            @Override
            public void run() {
                openBuildingPanel();
            }
        });
        addActor(cButt);

        cButt = new CircleButton(Main.width, 0, 180, Colours.dark);
        rollButtonPanel = RollManager.getRollPanel();
        cButt.setActor(rollButtonPanel, cButt.getWidth()/4-rollButtonPanel.getWidth()/2 + 20, cButt.getHeight()/2);
        cButt.setClickAction(new Runnable() {
            @Override
            public void run() {
                rollButtonClick();
            }
        });
        addActor(cButt);


        CrystalBall ball = new CrystalBall();
        int gap = 40;
        ball.setPosition(getWidth() - ball.getWidth()-gap,getHeight()-ball.getHeight()-gap);
        addActor(ball);
	}
	
	public void center(Actor a){
        a.setPosition(getWidth()/2-a.getWidth()/2, (getHeight())/2-a.getHeight()/2);
	}
	
	UpkeepPanel upkeepPanel = new UpkeepPanel();

	private void refreshBulletStuff() {
		BulletStuff.refresh(villagers);
	}


	
	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.z_white);
//		Draw.draw(batch, bg, 0, 0);
//		Fonts.font.draw(batch, "state: "+state, 400, Main.height-Fonts.font.getAscent());
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
			break;
		}
	}

	public void rollButtonClick(){
	    if(!BulletStuff.isFinishedRolling()) return;
	    if(BulletStuff.numSelectedDice()==0){
	        proceed();
        }
        else{
            roll(true);
        }
    }

	public void roll(boolean reroll){

		if(state!=State.Rolling) return;
//		if(rollButtonPanel.rollsLeft.getValue()<=0) return;
		if(reroll && !BulletStuff.isFinishedRolling()) return;
		int diceRolled = 0;
		for (Die d : BulletStuff.dice) {
			if(d.rerolling){
				d.roll();
				diceRolled++;
			}
		}
		if(diceRolled>0) {
            RollManager.spendRoll();
		}
	}


	public void proceed() {
		switch(state){
		case Event:
			setState(State.Rolling);
			break;
		case Rolling:
			if(!BulletStuff.isFinishedRolling()) return;
			setState(State.Review);
			break;
		case Review:
			if(Inventory.get().getResourceAmount(EffectType.Food)<0){
				showStarvation();
				break;
			}
			if(Inventory.get().getResourceAmount(EffectType.Morale)<=0){
				showLoss();
				break;
			}
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
	
	private void showLoss() {
		LossPanel panel = new LossPanel(LossReason.Morale, dayNum);
		addActor(panel);
		panel.setPosition(getWidth()/2-panel.getWidth()/2, getHeight()/2-panel.getHeight()/2);
	}

	private void showStarvation() {
		int food = Inventory.get().getResourceAmount(EffectType.Food);
		int wood = Inventory.get().getResourceAmount(EffectType.Wood);
		int missing = 0;
		if(food<0) missing -= food;
		if(wood<0) missing -= wood;
		StarvationPanel panel = new StarvationPanel(missing);
		addActor(panel);
		panel.setPosition(getWidth()/2-panel.getWidth()/2, getHeight()/2-panel.getHeight()/2);
		addProceedButton(panel);
		Inventory.get().imposeMaximums();
	}

	private void levelup(Villager v){
		LevelupPanel lup = new LevelupPanel(v, Villager.getRandomVillagerTypes(Math.min(Villager.MAX_LEVEL, v.type.level+1), 3));
		addActor(lup);
		center(lup);
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
	
	@Override
	public void act(float delta) {
		super.act(delta);
		for(Die d:BulletStuff.dice){
			d.update(delta);
		}
		RollManager.updateRolls();
	}
	
	private void finishRolling() {
		if(!BulletStuff.isFinishedRolling()) return;
		refreshPanels();
		upkeepPanel.activate();
		for(Die d:BulletStuff.dice){
			d.activate();
		}
		showWisps();
		for(Die d: BulletStuff.dice){
			d.villager.gainXP(1);
			d.removeFromScreen();
		}
	}
	

	
	private void showEvent() {
		state=State.Event;
		
		Event event = island.getEventForTurn(dayNum);
		
		eventPanel= new EventPanel(event, dayNum++);
		event.action();
		center(eventPanel);
		addActor(eventPanel);
		addProceedButton(eventPanel);
		Inventory.get().imposeMaximums();
	}

	private void startRolling() {
		BulletStuff.refresh(villagers);

		RollManager.setMaximumRolls(2+Inventory.get().getResourceAmount(EffectType.Morale)/3);
		RollManager.refreshRolls();
		reviewPanel = new ReviewPanel(dayNum);
		state=State.Rolling;
		for(Die d: BulletStuff.dice){
			d.addToScreen();
			d.rerolling=true;
		}
		roll(false);
	}
	
	private void showReview() {
		state=State.Review;
		Village.get().upkeep();
		reviewPanel.build();
		center(reviewPanel);
		addActor(reviewPanel);
		addProceedButton(reviewPanel);
		Inventory.get().imposeMaximums();
	}
	
	public void increaseUpkeepEffect(Effect effect){
		upkeepPanel.addEffect(effect);
		upkeepPanel.build();
	}

	public void addEffect(Effect effect){
		reviewPanel.addItem(effect);
		Inventory.get().activate(effect);
	}
	
	
	private ProceedButton proceedButton = new ProceedButton();
	
	public void addProceedButton(Actor relativeTo){
		int dist = 10;
		proceedButton.setColor(Colours.green_light);
		addActor(proceedButton);
		proceedButton.setLinkedActor(relativeTo);
		proceedButton.setPosition(Main.width/2-proceedButton.getWidth()/2, relativeTo.getY()-proceedButton.getHeight()-dist);
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
		//Inventory.get().clearWisps();
	}

	public void showWisps() {
		//Inventory.get().showWisps();
	}

	public void finishedLevellingUp() {
		proceed();
	}
	
	public void openBuildingPanel() {
		constructionPanel.setPosition(Main.width/2-constructionPanel.getWidth()/2, Main.height/2-constructionPanel.getHeight()/2);
		push(constructionPanel);
	}

	public void addObjective() {
		ObjectivePanel panel = new ObjectivePanel();
		addActor(panel);
		panel.setPosition(getWidth()/2-panel.getWidth()/2, getHeight()-panel.getHeight()-BUTTON_BORDER);
	}

	public void win() {
		TextBox tb = new TextBox("You win! It took you "+dayNum+" turns :D", Fonts.fontBig, getWidth()/2, Align.center);
		tb.setTextColour(Colours.blue_dark);
		tb.setBackgroundColour(Colours.dark);
		addActor(tb);
		tb.setPosition(getWidth()/2, getHeight()/2, Align.center);
	}
	
}
