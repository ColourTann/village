package tann.village.screens.gameScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import tann.village.Images;
import tann.village.Main;
import tann.village.bullet.BulletStuff;
import tann.village.gameplay.effect.Effect;
import tann.village.gameplay.effect.Effect.EffectSource;
import tann.village.gameplay.effect.Effect.EffectType;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.EventCreator;
import tann.village.gameplay.island.event.EventDebugPanel;
import tann.village.gameplay.island.event.Outcome;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.village.RollManager;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.screens.gameScreen.panels.*;
import tann.village.screens.gameScreen.panels.review.LossPanel;
import tann.village.screens.gameScreen.panels.review.ReviewPanel;
import tann.village.screens.gameScreen.panels.review.StarvationPanel;
import tann.village.screens.gameScreen.panels.review.LossPanel.LossReason;
import tann.village.util.*;

public class GameScreen extends Screen{
	public static final int BUTTON_BORDER=10;
	private static GameScreen self;
    public RerollPanel rollButtonPanel;
    public ConfirmPanel confirmPanel;
    Group rollContainer;
    private static final int ROLL_CONTAINER_OFFSCREEN = 200;

    public static void reset() {
        self=null;
    }



    public enum State{Story, Event, Rolling, Review, Levelling}
	public State state;
	private static final int STARTING_VILLAGERS = 5;
	public Array<Villager> villagers = new Array<>();
	CircleButton constructionButt;
	TextureRegion bg = Main.atlas.findRegion("gamescreen");
	public Array<Villager> villagersToLevelUp = new Array<>();
	ReviewPanel reviewPanel = new ReviewPanel(Village.get().getDayNum());
	EventPanel eventPanel;
	ConstructionPanel constructionPanel;
	public EachTurnPanel eachTurnPanel = new EachTurnPanel();
	public static GameScreen get(){
		if(self==null){
			self= new GameScreen();
		}
		return self;
	}

	public static void nullScreen(){
	    self=null;
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
				if(stack.size>0) return true;
				if(state==State.Rolling){
					BulletStuff.click(x, y, button);
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		addActor(eachTurnPanel);

		InventoryPanel inventoryGroup = Village.getInventory().getGroup();
		addActor(inventoryGroup);
		inventoryGroup.setPosition(0, (getHeight()-inventoryGroup.getHeight())) ;
		for(int i=0;i<STARTING_VILLAGERS;i++){
			villagers.add(new Villager(i));
		}
		refreshBulletStuff();
        Village.get().getUpkeep().addEffect(new Effect(EffectType.Food, -2, EffectSource.Upkeep));

		setState(State.Event);
		Sounds.playMusic(Sounds.beach);

        constructionButt = new CircleButton(0, 0, 180, Colours.dark);
        constructionButt.setTexture(Images.hammer, 0.7f, .7f, 80, 80);
        constructionButt.setClickAction(new Runnable() {
            @Override
            public void run() {
                openBuildingPanel();
            }
        });
        addActor(constructionButt);

        rollContainer = new Group();
        addActor(rollContainer);

        CircleButton cButt = new CircleButton(Main.width, 0, 180, Colours.dark);
        rollButtonPanel = RollManager.getRollPanel();
        cButt.setActor(rollButtonPanel, cButt.getWidth()/4-rollButtonPanel.getWidth()/2 + 20, cButt.getHeight()/2);
        cButt.setClickAction(new Runnable() {
            @Override
            public void run() {
                rollButtonClick();
            }
        });
        rollContainer.addActor(cButt);


        cButt = new CircleButton(Main.width, 330, 110, Colours.dark);
        confirmPanel = RollManager.getConfirmPanel();
        cButt.setActor(confirmPanel, cButt.getWidth()/4-confirmPanel.getWidth()/2+10, cButt.getHeight()/2-confirmPanel.getHeight()/2);
        cButt.setClickAction(new Runnable() {
            @Override
            public void run() {
                confirmButtonClick();
            }
        });
        rollContainer.addActor(cButt);
        showRollContainer(false);
//        CrystalBall ball = CrystalBall.get();
//        int gap = 40;
//        ball.setPosition(getWidth() - ball.getWidth()-gap,getHeight()-ball.getHeight()-gap);
//        addActor(ball);
        constructionPanel= new ConstructionPanel();
	}
	
	public void center(Actor a){
        a.setPosition(getWidth()/2-a.getWidth()/2, (getHeight())/2-a.getHeight()/2);
	}
	
	private void refreshBulletStuff() {
		BulletStuff.refresh(villagers);
	}

    EventDebugPanel edp;
    public void toggleEventDebug(){
        if(edp==null) {
            edp = new EventDebugPanel(EventCreator.getEvents(EventCreator.EventType.Tutorial));
            edp.setPosition(getWidth()/2-edp.getWidth()/2, getHeight()/2-edp.getHeight()/2);
        }
        if(!edp.remove()) addActor(edp);
    }
	
	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.z_white);
		Draw.draw(batch, bg, getX(), getY());
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
                toggleEventDebug();
                break;
            case Input.Keys.ESCAPE:
                toggleEscMenu();
                break;
		}
	}

    private void toggleEscMenu() {
        if(stack.size==0 || stack.get(stack.size-1)!=EscMenu.get()){
            push(EscMenu.get());
        }
        else{
            pop();
        }
    }

    public void rollButtonClick(){
	    if(!BulletStuff.isFinishedRolling()) return;
        if(BulletStuff.numSelectedDice()==0) return;
        roll(true);
    }

    public void confirmButtonClick(){
        if(!BulletStuff.isFinishedRolling()) return;
        Sounds.playSound(Sounds.accept,1,1);
            proceed();
    }

	public void roll(boolean reroll){

		if(state!=State.Rolling) return;
		if(!RollManager.hasRoll()) return;
		if(reroll && !BulletStuff.isFinishedRolling()) return;
		int diceRolled = 0;

		for (tann.village.gameplay.village.villager.die.Die d : BulletStuff.dice) {
			if(d.rerolling){
				d.roll();
				diceRolled++;
			}
		}

		if(diceRolled>0) {
            RollManager.spendRoll();
            Sounds.playSound(Sounds.roll,1,1);
		}
	}


	public void proceed() {
        if(Village.getInventory().getResourceAmount(EffectType.Morale)<=0){
            showLoss();
            return;
        }
		switch(state){
        case Story:
            setState(State.Event);
            break;
		case Event:
			setState(State.Rolling);
			break;
		case Rolling:
			if(!BulletStuff.isFinishedRolling()) return;
			setState(State.Review);
			break;
		case Review:
			if(Village.getInventory().getResourceAmount(EffectType.Food)<0 || Village.getInventory().getResourceAmount(EffectType.Wood)<0){
				showStarvation();
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
	
	public void showLoss() {
		LossPanel panel = new LossPanel(LossReason.Morale, Village.get().getDayNum());
		addActor(panel);
		panel.setPosition(getWidth()/2-panel.getWidth()/2, getHeight()/2-panel.getHeight()/2);
	}

	private void showStarvation() {
		int food = Village.getInventory().getResourceAmount(EffectType.Food);
		int wood = Village.getInventory().getResourceAmount(EffectType.Wood);
		int foodMissing = Math.max(0, -food);
		int woodMissing = Math.max(0, -wood);
		StarvationPanel panel = new StarvationPanel(foodMissing, woodMissing);
		addActor(panel);
		panel.setPosition(getWidth()/2-panel.getWidth()/2, getHeight()/2-panel.getHeight()/2);
		addProceedButton(panel);
		Village.getInventory().imposeFoodAndWoodMinimum();
	}

	boolean levelledUpAlready=false;
	private void levelup(Villager v){
		LevelupPanel lup = new LevelupPanel(v, island.getRandomVillagerTypes(Math.min(Villager.MAX_LEVEL, v.type.level+1), 3));
		addActor(lup);
		center(lup);
		if(!levelledUpAlready) {
            Sounds.playSound(Sounds.marimba_happy, 1, 1);
        }
        levelledUpAlready=true;
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
		for(tann.village.gameplay.village.villager.die.Die d:BulletStuff.dice){
			d.update(delta);
		}
		RollManager.updateRolls();
	}
	
	private void finishRolling() {
		if(!BulletStuff.isFinishedRolling()) return;
		resetWisps();
        showRollContainer(false);
		Village.get().getUpkeep().activate();
		for(tann.village.gameplay.village.villager.die.Die d:BulletStuff.dice){
			d.activate();
		}
		for(tann.village.gameplay.village.villager.die.Die d: BulletStuff.dice){
			d.removeFromScreen();
		}
		showWisps();
	}
	

	
	private void showEvent() {

	    int dayNum = Village.get().getDayNum();

        Village.get().nextDay();

        if(checkEnd()){
            return;
        }

		state=State.Event;
		
		Event event = island.getEventForTurn(dayNum);


		if(event.isStory() && dayNum != 0){
		    state=State.Story;
        }

        if(event.isStory() && dayNum == 0){

        }
        else {
            int goodness = event.getGoodness();
            String[] sound = null;
            if (goodness == -1) sound = Sounds.eventNegBird;
            else if (goodness == 1) sound = Sounds.eventPosBird;
            else sound = Sounds.eventNeuBird;

            Sounds.playSound(sound, 1, 1);
        }

		eventPanel= new EventPanel(event, dayNum);
		event.action();
		center(eventPanel);
		addActor(eventPanel);
		if(event.outcomes.size==0) {
            addProceedButton(eventPanel);
        }
		Village.getInventory().imposeLimits();
	}

    private boolean checkEnd() {
        Island.ObjectiveOutcome outcome = island.objectivesCompletes();
        if(outcome== Island.ObjectiveOutcome.Fail){
            showLoss();
            return true;
        }
        if(outcome == Island.ObjectiveOutcome.Success){
            win();
            return true;
        }
        return false;
    }

    private void startRolling() {
        levelledUpAlready=false;
	    showRollContainer(true);
		BulletStuff.refresh(villagers);
		RollManager.setMaximumRolls(Village.get().getRerolls());
		RollManager.refreshRolls();
		reviewPanel = new ReviewPanel(Village.get().getDayNum());
		state=State.Rolling;
		for(tann.village.gameplay.village.villager.die.Die d: BulletStuff.dice){
			d.addToScreen();
			d.rerolling=true;
		}
		roll(false);
	}

	public void showRollContainer(boolean show){
	    rollContainer.addAction(Actions.moveTo(show?0:ROLL_CONTAINER_OFFSCREEN, 0, .5f, Interpolation.pow2Out));
    }
	
	private void showReview() {
		state=State.Review;
		Village.get().upkeep();
		reviewPanel.build();
		center(reviewPanel);
		addActor(reviewPanel);
		addProceedButton(reviewPanel);
		Village.getInventory().imposeLimits();
	}
	
	public void increaseUpkeepEffect(Effect effect){
        Village.get().getUpkeep().addEffect(effect);
	}

	public void addEffect(Effect effect, boolean addToReview){
	    Village.get().process(effect);
	    if(effect.type.objective){
                island.addObjective(effect);
        }
        if(addToReview) {
            reviewPanel.addItem(effect);
        }
		Village.getInventory().activate(effect);
	}
	
	
	private ProceedButton proceedButton = new ProceedButton();
	
	public void addProceedButton(Actor relativeTo){
		int dist = 10+20;
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
					return true;
				}
			});
		}
		
		stack.add(a);
		
		addActor(inputBlocker);
		addActor(a);
	}
	public void pop(){
		if(stack.size>0){
		    Actor a = stack.removeIndex(stack.size-1);
		    a.remove();
		}
		if(stack.size==0){
			inputBlocker.remove();
		}
		else{
			stack.get(stack.size-1).toFront();
		}
	}


	public void finishedLevellingUp() {
		proceed();
	}
	
	public void openBuildingPanel() {
	    Sounds.playSound(Sounds.buildPanel, 1, 1);
		constructionPanel.setPosition(Main.width/2-constructionPanel.getWidth()/2, Main.height/2-constructionPanel.getHeight()/2);
		push(constructionPanel);
	}

	public void win() {
	    addActor(inputBlocker);
	    String vicText = island.getVictoryText();
	    VictoryPanel vp = new VictoryPanel(vicText);
	    vp.setPosition(getWidth()/2-vp.getWidth()/2, getHeight()/2 - vp.getHeight()/2);
	    addActor(vp);
	}

    public void chooseOutcome(Outcome o) {
        eventPanel.remove();
        o.activate();
        proceed();
    }

    public void resetWisps(){
        village.getInventory().resetWisps();
    }

    public void showWisps(){
        village.getInventory().showWisps();
    }

    @Override
    public void removeFromScreen(){
        BulletStuff.reset();
    }

    public ObjectivePanel objectivePanel= new ObjectivePanel();
    public void addObjectiveToPanel(Objective objective) {
        objectivePanel.addObject(objective);

        float buttHeight = 55;
        float y = (Main.height-buttHeight)/2 - objectivePanel.getHeight()/2 + buttHeight;
        addActor(objectivePanel);
        objectivePanel.setPosition(-objectivePanel.getWidth(), y);
        objectivePanel.addAction(Actions.moveTo(0, y, .5f, Interpolation.pow2Out));
    }

}
