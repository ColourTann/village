package tann.village.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import tann.village.Main;


public class TextRenderer extends Actor{
	private static Color defaultColour=Colours.dark;
	private static TannFont defaultFont=TannFont.font;
	TannFont font = defaultFont;
	int wrapWidth;
	String text;
	int fontHeight;
	int align = Align.center;
	private FrameBuffer buffer;
	
	public TextRenderer(String text){
		setup(text, defaultFont, (int)font.getWidth(text), Align.center);
	}

	public TextRenderer(String text, int boxWidth) {
		setup(text, defaultFont, boxWidth, Align.center);
	}

	public TextRenderer(String text, TannFont font, int boxWidth) {
		setup(text, font, boxWidth, Align.center);
	}

	public TextRenderer(String text, TannFont font, int boxWidth, int align) {
		setup(text, font, boxWidth, align);
	}

	private void setup(String text, TannFont font, int boxWidth, int align){
		this.align=align;
		this.font=font;
		this.text=text;
		this.wrapWidth=boxWidth;
		fontHeight=(int) (getHeight());		
		setupLines(text);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		Draw.drawRotatedScaledFlipped(batch, buffer.getColorBufferTexture(), (int)getX(), (int)getY(), 1, 1 ,0 ,false, true);
		super.draw(batch, parentAlpha);
	}

	Array<Line> lines = new Array<Line>();
	SpriteBatch batch = new SpriteBatch();
	OrthographicCamera bufferCam;
	private void setupLines(String entireText){

		int baseLineHeight = font.getLineHeight(); 
		int currentX=0;
		int currentY=font.getHeight();
		int lineHeight = baseLineHeight;
		int spaceWidth = font.getSpaceWidth();
		char[] charArray = entireText.toCharArray();
		int previousIndex=0;
		lines.clear();
		Line currentLine= new Line();
		boolean specialMode=false;
		for(int index = 0; index<=charArray.length; index++){
			boolean finishedWord=false;
			char c=0;
			if(index==charArray.length) finishedWord=true;
			else{
				c = charArray[index];
				if(c==' '||c=='['||c==']')finishedWord=true;
			}
			if(finishedWord){
				String word = entireText.substring(previousIndex, index);
				TextureRegion tr=null;
				int length=0;
				previousIndex=index+1;
				if(specialMode){
					boolean specialNewLine=false;
					int specialLineHeight=0;
					if(word.equalsIgnoreCase("n")){
						specialNewLine=true;
						specialLineHeight=lineHeight;
					}
					if(word.equals("nh")){
						specialNewLine=true;
						specialLineHeight=lineHeight/2;
					}
					if(word.equals("nq")){
						specialNewLine=true;
						specialLineHeight=lineHeight/4;
					}
					if(specialNewLine){
						//newline and cancel special mode
						currentLine.setWidth(currentX-(specialMode?0:spaceWidth));
						currentLine.setY(currentY);
						lines.add(currentLine);
						currentLine = new Line();
						currentY+=specialLineHeight;
						lineHeight=baseLineHeight;
						currentX=0;
						specialMode=false;
						continue;
					}
					boolean space=false;
					if(word.equals("h")){
						space=true;
						currentX+=spaceWidth/2;
					}
					if(word.equals("q")){
						space=true;
						currentX+=spaceWidth/4;
					}
					if(space){
						specialMode=false;
						continue;
					}
					if(!space){
						//find texture
						tr = textureMap.get(word);
						if(tr==null){
							System.out.println("couldn't find image id: "+word);
						}
						length = tr.getRegionWidth();
					}
				}
				else{
					length = font.getWidth(word);
				}
				if(currentX+length>wrapWidth){
					//too far, needs new line
					currentLine.setWidth(currentX-(specialMode?0:spaceWidth));
					currentLine.setY(currentY);
					lines.add(currentLine);
					currentLine = new Line();
					currentY+=lineHeight;
					lineHeight=baseLineHeight;
					currentX=0;
				}
				if(specialMode){
					//adjust line height based on texture height
					int diff = tr.getRegionHeight()-baseLineHeight+1;
					int newDiff = diff/2-(lineHeight-baseLineHeight);
					if(newDiff>0){
						lineHeight+=newDiff;
						currentY+=newDiff;
					}
					currentLine.addTextPosition(new TextPosition(tr, currentX, 0));
				}
				else currentLine.addTextPosition(new TextPosition(word, currentX, 0));
				currentX+=length;
			}
			if(c=='[')specialMode=true;
			if(c==']')specialMode=false;
			if(c==' ')currentX+=spaceWidth;
		}
		if(!lines.contains(currentLine, true)){
			//finish final word
			currentLine.setWidth(currentX);
			currentLine.setY(currentY);
			lines.add(currentLine);
			currentY+=lineHeight;
		}
		currentY-=font.getLineHeight();
		//now setup buffer and draw to it
		batch.setColor(defaultColour);
		int bufferWidth=(int)(wrapWidth);
		int bufferHeight=(int) (currentY);
		setSize(bufferWidth, bufferHeight);
		bonusBonusY=0;
		if(bufferWidth%2!=0)bufferWidth++;
		if(bufferHeight%2!=0){
			bufferHeight++;
			bonusBonusY=-1;
		}
		buffer = new FrameBuffer(Format.RGBA8888, bufferWidth, bufferHeight, false);
		bufferCam = new OrthographicCamera(buffer.getWidth(), buffer.getHeight());
		bufferCam.translate((int)(buffer.getWidth()/2), (int)(buffer.getHeight()/2));
		bufferCam.update();
		
		buffer.bind();
		buffer.begin();
		batch.setProjectionMatrix(bufferCam.combined);
		batch.begin();
		batch.setColor(defaultColour);
		for(Line l: lines){
			l.render(batch, align);
		}
		batch.end();
		buffer.end();
		FrameBuffer.unbind();
		buffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		Main.self.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	private static HashMap<String, TextureRegion> textureMap = new HashMap<String, TextureRegion>();

	public static void setupTextures(){
	
	}

	public static void setImage(String id, TextureRegion texture){
		textureMap.put(id, texture);
	}

	private class Line{
		int width;
		int y;
		Array<TextPosition> textPositions = new Array<TextPosition>();
		public Line() {
		}
		public void setWidth(int width) {
			this.width=width;
		}
		public void setY(int y) {
			this.y=y;
		}
		public void addTextPosition(TextPosition tp){
			textPositions.add(tp);
		}
		public void render(Batch batch, int align){
			int bonusX=0;
			if(align == Align.center){
				bonusX=(wrapWidth-width)/2;
			}
			for(TextPosition tp: textPositions) tp.render(batch, bonusX, y);
		}
	}

	static int bonusBonusY;
	
	private class TextPosition{
		String text; 
		TextureRegion tr;
		int x, y;
		public TextPosition(String text, int x, int y) {
			this.text=text; 
			this.x=x; this.y=y;
		}
		public TextPosition(TextureRegion tr, int x, int y) {
			this.tr=tr; 
			this.x=x; this.y=y;
		}
		public void render(Batch batch, int bonusX, int bonusY){
			if(tr!=null) {
				Color old = batch.getColor();
				batch.setColor(Colours.white);
				Draw.draw(batch, tr, (int)(x+bonusX), (int)(buffer.getHeight()-y+font.getLineHeight()/2f-tr.getRegionHeight()/2f-bonusY+bonusBonusY));
				batch.setColor(old);
			}
			else font.draw(batch, text, x+bonusX, buffer.getHeight()-y-bonusY+bonusBonusY);
		}
	}
}