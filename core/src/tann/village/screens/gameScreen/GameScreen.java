package tann.village.screens.gameScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import tann.village.gameplay.effect.Eff;
import tann.village.gameplay.effect.Eff.EffectType;
import tann.village.gameplay.island.event.Event;
import tann.village.gameplay.island.event.EventDebugPanel;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.village.Inventory;
import tann.village.gameplay.village.RollManager;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.gameplay.village.villager.die.Die;
import tann.village.screens.gameScreen.panels.buildingStuff.ConstructionPanel;
import tann.village.screens.gameScreen.panels.eventStuff.EventPanel;
import tann.village.screens.gameScreen.panels.eventStuff.JoelDebugPanel;
import tann.village.screens.gameScreen.panels.inventoryStuff.InventoryPanel;
import tann.village.screens.gameScreen.panels.miscStuff.ProceedButton;
import tann.village.screens.gameScreen.panels.review.SpoilPanel;
import tann.village.screens.gameScreen.panels.rollStuff.LockBar;
import tann.village.screens.gameScreen.panels.rollStuff.RerollPanel;
import tann.village.screens.gameScreen.panels.villagerStuff.*;
import tann.village.screens.gameScreen.panels.bottomBar.BottomBar;
import tann.village.screens.gameScreen.panels.bottomBar.ObjectivePanel;
import tann.village.screens.gameScreen.panels.bottomBar.TurnStatsPanel;
import tann.village.screens.gameScreen.panels.review.LossPanel;
import tann.village.screens.gameScreen.panels.review.StarvationPanel;
import tann.village.screens.gameScreen.panels.review.LossPanel.LossReason;
import tann.village.util.*;

public class GameScreen extends Screen{
	public static final int BUTTON_BORDER=10;
	private static GameScreen self;
    public RerollPanel rollButtonPanel;
    Group rollContainer;

    public static void reset() {
        self=null;
    }

    public boolean allowDieClicking() {
        return stack.size==0;
    }


    public enum State{Story, Event, Rolling, Upkeep, Levelling}
	public State state;
	public CircleButton constructionCircle;
	public Array<Villager> villagersToLevelUp = new Array<>();
	EventPanel eventPanel;
	public ConstructionPanel constructionPanel;
    CircleButton cButt1;
    public BottomBar btb;
	public TurnStatsPanel tsp;
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
	VillagerBarPanel vbp;
	public void init(Island island, Village village){
		this.village=village;
		this.island=island;
		addActor(LockBar.get());
		setSize(Main.width, Main.height);
		addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(event.isHandled())return true;
				if(stack.size>0) return true;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		addActor(Village.getInventory().getGroup());

		refreshBulletStuff();
        //todo upkeep
        Village.get().getUpkeep().addEffect(new Eff(EffectType.Food, -2));

        constructionCircle = new CircleButton(0, 0, 180, Colours.dark);
        constructionCircle.setClickAction(new Runnable() {
            @Override
            public void run() {
                openBuildingPanel();
            }
        });
        addActor(constructionCircle);

        rollContainer = new BasicLay();
        addActor(rollContainer);

        cButt1 = new CircleButton(Main.width, 0, 180, Colours.dark);
        cButt1.setClickAction(new Runnable() {
            @Override
            public void run() {
                rollButtonClick();
            }
        });
        rollContainer.addActor(cButt1);


        showRollContainer(false);
        constructionPanel= new ConstructionPanel();
        vbp = new VillagerBarPanel();
        addActor(vbp);

		btb = new BottomBar();
		addActor(btb);
		objectivePanel= new ObjectivePanel();
		tsp = new TurnStatsPanel();

		setState(State.Event);
		Sounds.playMusic(island.getAmbienceString());


		layChain();
	}

    @Override
    public void layout() {
        setSize(Main.width, Main.height);

        // inventory stuff

        InventoryPanel inventoryGroup = Village.getInventory().getGroup();
        inventoryGroup.setPosition(0, (getHeight()-inventoryGroup.getHeight()));

        //reroll stuff

        float confirmSize = Main.h(18)*2;
        cButt1.setSize(getConstructionCircleSize()*2, getConstructionCircleSize()*2);
        cButt1.setCirclePosition(Main.width, 0);
        showRollContainer(lastRollContainerShow);
        rollButtonPanel = RollManager.getRollPanel();
        cButt1.setActor(rollButtonPanel, cButt1.getWidth()/4-rollButtonPanel.getWidth()/2 + 20, cButt1.getHeight()/2);

        constructionCircle.setSize(getConstructionCircleSize()*2, getConstructionCircleSize()*2);
        constructionCircle.setTexture(Images.hammer, 0.7f, .7f, Main.h(13), Main.h(13));
        constructionCircle.setCirclePosition(0,0);

        vbp.setPosition(Main.width-vbp.getWidth(), GameScreen.getConstructionCircleSize());

        LockBar.get().setPosition(getWidth()/2- LockBar.get().getWidth()/2, getHeight()+ LockBar.get().getHeight());

        if(state==State.Rolling){
            LockBar.get().moveIn();
        }

        if(eventPanel!=null){
            center(eventPanel,true);
        }
        if(proceedButton!=null){
            proceedButton.refreshPosition();
        }
    }

    public static float getConstructionCircleSize(){
        return Main.h(26);
    }
	
	public void center(Actor a, boolean andProgressBar){
        a.setPosition(getWidth()/2-a.getWidth()/2, (andProgressBar?50:0)+BottomBar.height()+(getHeight()-BottomBar.height())/2-a.getHeight()/2);
	}
	
	private void refreshBulletStuff() {
		BulletStuff.refresh(Village.get().villagers);
	}

    EventDebugPanel edp;
    public void toggleEventDebug(){
        if(edp==null) {
            edp = new EventDebugPanel(island.getRandomEvents());
            edp.setPosition(getWidth()/2-edp.getWidth()/2, getHeight()/2-edp.getHeight()/2);
        }
        if(!edp.remove()) addActor(edp);
    }

    public void toggleJoelDebug(){
        JoelDebugPanel jdp =Village.get().getJoelDebugPanel();
        jdp.setPosition(getWidth()/2-jdp.getWidth()/2, BottomBar.height()+ 40);
        if(!jdp.remove()) addActor(jdp);
    }
	
	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.z_white);
		Draw.drawSize(batch, island.background, getX(), getY(), getWidth(), getHeight());
        LockBar.get().render(batch);
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
            case Input.Keys.SHIFT_LEFT:
                toggleJoelDebug();
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
	    if(BulletStuff.allDiceLocked() || (!RollManager.hasRoll()&&BulletStuff.noDiceMoving())){
            Sounds.playSound(Sounds.accept,1,1);
            proceed();
        }
        else if(BulletStuff.noDiceMoving()){
            roll(true);
        }
    }

	public void roll(boolean reroll){
        if(state!=State.Rolling) return;
		if(!RollManager.hasRoll()) return;
		if(!reroll){

		}
		int diceRolled = 0;
        for (Die d : BulletStuff.dice) {
            if(d.getState() == Die.DieState.Stopped || !reroll) {
                d.roll(reroll);
                diceRolled++;
            }
		}

		if(diceRolled>0) {
            RollManager.spendRoll();
            Sounds.playSound(Sounds.roll,1,1);
		}
	}


	public boolean canProceed(){
        if((state==State.Event||state==State.Story) && !eventPanel.choiceAction()){
            Sounds.playSound(Sounds.error, 1,1);
            return false;
        }
        return true;
    }

	public void proceed() {
        if(Village.getInventory().getResourceAmount(EffectType.Morale)<=0){
            showLoss();
            return;
        }
		switch(state){
            case Event:
            case Story:
                setState(State.Rolling);
			break;
		case Rolling:
            finishRolling();
			setState(State.Upkeep);
			break;
		case Upkeep:

            int spoiled = Village.getInventory().imposeLimits();
            if(spoiled>0){
                showSpoiled(spoiled);
                break;
            }

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
		return;
	}

    boolean lost;
	public void showLoss() {
	    lost = true;
        Sounds.playSound(Sounds.marimba_sad,1,1);
		LossPanel panel = new LossPanel(LossReason.Morale, Village.get().getDayNum());
		addActor(panel);
		panel.setPosition(getWidth()/2-panel.getWidth()/2, getHeight()/2-panel.getHeight()/2);
	}

    private void showSpoiled(int amount) {
	    SpoilPanel panel = new SpoilPanel(amount);
	    addActor(panel);
	    center(panel, true);
	    addProceedButton(panel);
    }

	private void showStarvation() {
		int food = Village.getInventory().getResourceAmount(EffectType.Food);
		int wood = Village.getInventory().getResourceAmount(EffectType.Wood);
		int foodMissing = Math.max(0, -food);
		int woodMissing = Math.max(0, -wood);
		StarvationPanel panel = new StarvationPanel(foodMissing, woodMissing);
		addActor(panel);
		center(panel, true);
		addProceedButton(panel);
		Village.getInventory().imposeFoodAndWoodMinimum();
	}

	boolean levelledUpAlready=false;
	private void levelup(Villager v){
		LevelupPanel lup = new LevelupPanel(v, island.getRandomVillagerTypes(Math.min(Villager.MAX_LEVEL, v.type.level+1), 3));
		addActor(lup);
		center(lup,false);
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
		case Upkeep:
            proceed();
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
		resetWisps();
        showRollContainer(false);
        LockBar.get().moveAway();
        Village.get().actionPotential();
        showWisps();
        BulletStuff.clearDice();
	}
	

	
	private void showEvent() {
	    int dayNum = Village.get().getDayNum();
        if(checkEnd()){
            return;
        }
		state=State.Event;
		Event event = island.getEventForTurn(dayNum);
        Village.get().nextDay();
		if(event.isStory() && dayNum != 0){
		    state=State.Story;
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
		event.initialAction();
		center(eventPanel,true);
		addActor(eventPanel);
        addProceedButton(eventPanel);
	}

    public boolean checkEnd() {
        if(lost){
            return true;
        }
        ObjectivePanel.ObjectiveOutcome outcome = Village.get().getObjectivePanel().objectivesCompletes();
        if(outcome== ObjectivePanel.ObjectiveOutcome.Fail){
            showLoss();
            return true;
        }
        if(outcome == ObjectivePanel.ObjectiveOutcome.Success){
            win();
            return true;
        }
        return false;
    }

    private void startRolling() {

        if(checkEnd()){
            return;
        }

        levelledUpAlready=false;
	    showRollContainer(true);
		Village.get().startOfRoll();
		BulletStuff.refresh(Village.get().villagers);

		RollManager.setMaximumRolls(Village.get().getRerolls());
		RollManager.refreshRolls();
		state=State.Rolling;
		for(Die d: BulletStuff.dice){
			d.addToScreen();
		}
		roll(false);
        Village.get().getUpkeep().activateDelta();
        LockBar.get().moveIn();
        LockBar.get().reset();

	}

	boolean lastRollContainerShow;
	public void showRollContainer(boolean show){
	    lastRollContainerShow=show;
	    rollContainer.addAction(Actions.moveTo(show?0:Main.h(50), 0, .5f, Interpolation.pow2Out));
    }
	
	private ProceedButton proceedButton = new ProceedButton();
	
	public void addProceedButton(Actor relativeTo){
		proceedButton.setColor(Colours.green_light);
		addActor(proceedButton);
		proceedButton.setLinkedActor(relativeTo);
	}
	
	public void addVillagerPanel(Villager villager) {
		VillagerPanel panel = new tann.village.screens.gameScreen.panels.villagerStuff.VillagerPanel(villager);
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
		    if(inputBlocker!=null){
                inputBlocker.remove();
            }
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
        Sounds.playSound(Sounds.marimba_too_happy,1,1);
	    String vicText = island.getVictoryText();
	    tann.village.screens.gameScreen.panels.miscStuff.VictoryPanel vp = new tann.village.screens.gameScreen.panels.miscStuff.VictoryPanel(vicText);
	    vp.setPosition(getWidth()/2-vp.getWidth()/2, getHeight()/2 - vp.getHeight()/2);
	    addActor(vp);
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

    public ObjectivePanel objectivePanel;
    public void addObjectiveToPanel(Objective objective) {
        objectivePanel.addObject(objective);
        float buttHeight = 55;
        float y = (Main.height-buttHeight)/2 - objectivePanel.getHeight()/2 + buttHeight;
    }

}
