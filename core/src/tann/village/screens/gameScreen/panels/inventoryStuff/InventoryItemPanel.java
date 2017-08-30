package tann.village.screens.gameScreen.panels.inventoryStuff;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

import tann.village.Images;
import tann.village.Main;
import tann.village.util.*;

public class InventoryItemPanel extends Lay{
	private int value;
	public int max;
	TextureRegion tr;
    protected Color border = Colours.dark;
	public InventoryItemPanel(TextureRegion tr, int value, int max) {
		this.value=value;
		this.max=max;
		this.tr=tr;
        layout();
    }

    public static float invPanelWidth(){
	    return Main.h(19);
    }

    public static float invPanelHeight(){
        return Main.h(14);
    }

    public static float extraBitWidth(){ return Main.h(8);}

    public static float totalWidth(){ return invPanelWidth() + extraBitWidth();}

    @Override
    public void layout() {
        setSize(invPanelWidth(), invPanelHeight());
        setup();
    }
	
	public void setValue(int value){
		this.value=value;
		setup();
	}
	
	public void setMax(int max){
		this.max=max;
		setup();
	}
	
	int prevAmount=0;

	public void clearWisp(){
		prevAmount=value;
	}

	TextWisp tWisp;
	public void wisp(){

        int diff = value-prevAmount;
		if(diff==0)return;
		tWisp = new TextWisp((diff>0?"+":"")+diff,  getWidth()/2, 0);
        if(diff<0){
			tWisp.setColor(Colours.red);
		}
		addActor(tWisp);
		clearWisp();
	}
	
	public void changeValue(int delta){
		this.value+=delta;
		setup();
	}
	
	public int getValue(){
		return value;
	}

	int pos;
	int neg;
	public void addDelta(int delta, boolean invert){
	    if(delta>0)pos += delta;
	    if(delta<0)neg += delta;
	    layout();
    }

    public void clearDelta(){
	    pos=0;
	    neg=0;
	    layout();
    }

    TextBox amount;
	TextBox outOf;
	ImageActor imageActor;
	protected void setup(){
        clearChildren();

        float TEXTURESIZE =getHeight()*.65f;
        imageActor = new ImageActor(tr, TEXTURESIZE, TEXTURESIZE);
		Color col = null;
        if(tr== Images.fate){
             col = Colours.light;
        }
		amount = new TextBox(String.valueOf(getValue()), Fonts.font, getWidth(), Align.center);
		outOf = new TextBox(max!=Integer.MAX_VALUE?"/"+max:"", Fonts.fontSmall, getWidth(), Align.center);
		outOf.setup(max!=Integer.MAX_VALUE?"/"+max:"");
        amount.setup(String.valueOf(getValue()));
        outOf.setTextColour(Colours.light);
        Layoo l = new Layoo(this);
        l.add(1,imageActor,1, amount, 0, outOf, 1);
        l.layoo();
        if(col!=null){
            amount.setTextColour(col);
            imageActor.setColor(col);
        }
        if(tWisp!=null){
            addActor(tWisp);
        }

        if(pos!=0 || neg != 0){
            InventoryDeltaGroup idg = new InventoryDeltaGroup();
            idg.setup(pos, neg);
            addActor(idg);
            idg.setPosition(getWidth(),0);
        }
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
	    Draw.fillActor(batch, this, Colours.dark, border, 2);
		batch.setColor(Colours.z_white);
        super.draw(batch, parentAlpha);
	}

	public void maxOut() {
		setValue(max);
		setup();
	}

	public void addMax(int value) {
		setMax(max+value);
	}

    public void setDeltas(int pos, int neg) {
	    this.pos=pos;
	    this.neg=neg;
	    layout();
    }

    static class InventoryDeltaGroup extends Lay{

        public InventoryDeltaGroup() {

        }

        int plus, negative;
        public void setup(int plus, int negative){
            this.plus = plus;
            this.negative=negative;
            layout();
        }

        @Override
        public void layout() {
            Layoo l = new Layoo(this);
            setSize(extraBitWidth(), invPanelHeight());
            BitmapFont font = Fonts.fontSmallish;
            if(plus!=0){
                TextBox plusBox = new TextBox("+"+plus, font, -1, Align.center);
                plusBox.setTextColour(Colours.green_light);
                l.row(1);
                l.actor(plusBox);
            }
            if(negative!=0){
                TextBox minusBox = new TextBox(""+negative, font, -1, Align.center);
                minusBox.setTextColour(Colours.red);
                l.row(1);
                l.actor(minusBox);
            }
            l.row(1);
            l.layoo(false);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {

            batch.setColor(Colours.dark);
            Draw.fillActor(batch,this);
            batch.setColor(Colours.brown_dark);
            Draw.fillRectangle(batch, getX(), getY(), 2, getHeight());
            super.draw(batch, parentAlpha);
        }
    }

}
