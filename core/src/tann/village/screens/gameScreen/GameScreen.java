package tann.village.screens.gameScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import tann.village.gameplay.island.event.EventDebugPanel;
import tann.village.gameplay.island.islands.Island;
import tann.village.gameplay.island.objective.Objective;
import tann.village.gameplay.village.RollManager;
import tann.village.gameplay.village.Village;
import tann.village.gameplay.village.villager.Villager;
import tann.village.gameplay.village.villager.die.Die;
import tann.village.screens.gameScreen.panels.buildingStuff.ConstructionPanel;
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

    public static void reset() {
        self=null;
    }

    public boolean allowDieClicking() {
        return stack.size==0;
    }

    public void addWithProceedButton(Actor panel, boolean proceedButton) {
        center(panel,proceedButton);
        addActor(panel);
        if(proceedButton){
            addProceedButton(panel);
        }
    }


	public CircleButton constructionCircle;
	public ConstructionPanel constructionPanel;
    CircleButton rollCircle;
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
	
	public Village village;
	VillagerBarPanel vbp;
	public void init(Island island, Village village){
		this.village=village;
		Village.island = island;
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


        rollCircle = new CircleButton(Main.width, 0, 180, Colours.dark);
        rollCircle.setClickAction(new Runnable() {
            @Override
            public void run() {
                rollButtonClick();
            }
        });
        addActor(rollCircle);


        showRollContainer(false);
        constructionPanel= new ConstructionPanel();
        vbp = new VillagerBarPanel();
        addActor(vbp);

		btb = new BottomBar();
		addActor(btb);
		objectivePanel= new ObjectivePanel();
		tsp = new TurnStatsPanel();

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
        rollCircle.setSize(getConstructionCircleSize()*2, getConstructionCircleSize()*2);
        rollCircle.setCirclePosition(Main.width, 0);
        showRollContainer(lastRollContainerShow);
        rollButtonPanel = RollManager.getRollPanel();
        rollCircle.setActor(rollButtonPanel, rollCircle.getWidth()/4-rollButtonPanel.getWidth()/2 + 20, rollCircle.getHeight()/2);

        constructionCircle.setSize(getConstructionCircleSize()*2, getConstructionCircleSize()*2);
        constructionCircle.setTexture(Images.hammer, 0.7f, .7f, Main.h(13), Main.h(13));
        constructionCircle.setCirclePosition(0,0);

        vbp.setPosition(Main.width-vbp.getWidth(), GameScreen.getConstructionCircleSize());

        LockBar.get().setPosition(getWidth()/2- LockBar.get().getWidth()/2, getHeight()+ LockBar.get().getHeight());


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
            edp = new EventDebugPanel(Village.island.getRandomEvents());
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
		Draw.drawSize(batch, Village.island.background, getX(), getY(), getWidth(), getHeight());
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
        //TODO this maybe
//        if(state!=State.Rolling) return;
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
	    //TODO here something?????
//        if((state==State.Event||state==State.Story) && !eventPanel.choiceAction()){
//            Sounds.playSound(Sounds.error, 1,1);
//            return false;
//        }
        return true;
    }

	public void proceed() {
	    //TODO this
        if(Village.getInventory().getResourceAmount(EffectType.Morale)<=0){
            showLoss();
            return;
        }
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

	private void levelup(Villager v){

	}
	

	@Override
	public void act(float delta) {
		super.act(delta);
		for(Die d:BulletStuff.dice){
			d.update(delta);
		}
		RollManager.updateRolls();
	}
	


	
	private void showEvent() {

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

	boolean lastRollContainerShow;
	public void showRollContainer(boolean show){
	    lastRollContainerShow=show;
	    rollCircle.addAction(Actions.moveTo(show?Main.width- rollCircle.getWidth()/2:Main.width, -rollCircle.getHeight()/2, .5f, Interpolation.pow2Out));
        constructionCircle.addAction(Actions.moveTo(true?-rollCircle.getWidth()/2:-rollCircle.getWidth(), -rollCircle.getHeight()/2, .5f, Interpolation.pow2Out));
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
	    String vicText = Village.island.getVictoryText();
	    tann.village.screens.gameScreen.panels.miscStuff.VictoryPanel vp = new tann.village.screens.gameScreen.panels.miscStuff.VictoryPanel(vicText);
	    vp.setPosition(getWidth()/2-vp.getWidth()/2, getHeight()/2 - vp.getHeight()/2);
	    addActor(vp);
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
