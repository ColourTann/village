package tann.village.util;


import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import tann.village.Main;

public class Draw {
	
	public static Draw draww = new Draw();
	//Texture stuff//
	//noncentered stuff//

	public static void draw(Batch batch, Texture t, float x, float y) {
		drawRotatedScaled(batch, t, x, y, 1, 1, 0);
	}

	public static void drawScaled(Batch batch, Texture t, float x, float y,
			float scaleX, float scaleY) {
		drawRotatedScaled(batch, t, x, y, scaleX, scaleY, 0);
	}

	public static void drawRotatedScaled(Batch batch, Texture t, float x,
			float y, float scaleX, float scaleY, float radianRotation) {
		drawRotatedScaledFlipped(batch, t, x, y, scaleX, scaleY,
				radianRotation, false, false);
	}

	public static void drawRotatedScaledFlipped(Batch batch, Texture t,
			float x, float y, float scaleX, float scaleY, float radianRotation,
			boolean xFlip, boolean yFlip) {
		batch.draw(t, x, y, 0, 0, t.getWidth(), t.getHeight(), scaleX, scaleY,
				rad2deg(radianRotation), 0, 0, t.getWidth(), t.getHeight(),
				xFlip, yFlip);
	}

	//centered stuff//

	public static void drawCentered(Batch batch, Texture t, float x, float y) {
		drawCenteredRotatedScaled(batch, t, x, y, 1, 1, 0);
	}

	public static void drawCenteredScaled(Batch batch, Texture t, float x,
			float y, float scaleX, float scaleY) {
		drawCenteredRotatedScaled(batch, t, x, y, scaleX, scaleY, 0);
	}

	public static void drawCenteredRotated(Batch batch, Texture t, float x,
			float y, float radianRotation) {
		drawCenteredRotatedScaled(batch, t, x, y, 1, 1, radianRotation);
	}

	public static void drawCenteredRotatedScaled(Batch batch, Texture t,
			float x, float y, float xScale, float yScale, float radianRotation) {
		drawCenteredRotatedScaledFlipped(batch, t, x, y, xScale, yScale,
				radianRotation, false, false);
	}

	public static void drawCenteredRotatedScaledFlipped(Batch batch, Texture t,
			float x, float y, float xScale, float yScale, float radianRotation,
			boolean xFlip, boolean yFlip) {
		batch.draw(t, (x - t.getWidth() / 2), (y - t.getHeight() / 2),
				t.getWidth() / 2f, t.getHeight() / 2f, t.getWidth(),
				t.getHeight(), xScale, yScale, rad2deg(radianRotation), 0, 0,
				t.getWidth(), t.getHeight(), xFlip, yFlip);
	}

	//TextureRegion stuff//
	
	public static void draw(Batch batch, TextureRegion t, float x, float y) {
		drawRotatedScaled(batch, t, x, y, 1, 1, 0);
	}

	public static void drawScaled(Batch batch, TextureRegion t, float x,
			float y, float scaleX, float scaleY) {
		drawRotatedScaled(batch, t, x, y, scaleX, scaleY, 0);
	}


	public static void drawRotatedScaled(Batch batch, TextureRegion t, float x,
			float y, float scaleX, float scaleY, float radianRotation) {
		batch.draw(t, (int)x, (int)y, 0f, 0f, 
				(int)(t.getRegionWidth()), 
				(int)(t.getRegionHeight()),
				scaleX, scaleY, rad2deg(radianRotation));
	}

	//centered stuff//

	public static void drawCentered(Batch batch, TextureRegion t, float x,
			float y) {
		drawCenteredRotatedScaled(batch, t, x, y, 1, 1, 0);
	}

	public static void drawCenteredScaled(Batch batch, TextureRegion t,
			float x, float y, float scaleX, float scaleY) {
		drawCenteredRotatedScaled(batch, t, x, y, scaleX, scaleY, 0);
	}


	public static void drawCenteredRotated(Batch batch, TextureRegion t,
			float x, float y, float radianRotation) {
		drawCenteredRotatedScaled(batch, t, x, y, 1, 1, radianRotation);
	}

	public static void drawCenteredRotatedScaled(Batch batch, TextureRegion t,
			float x, float y, float xScale, float yScale, float radianRotation) {
		drawCenteredRotatedScaledFlipped(batch, t, x, y, xScale, yScale,
				radianRotation);
	}

	public static void drawCenteredRotatedScaledFlipped(Batch batch,
			TextureRegion t, float x, float y, float scaleX, float scaleY,
			float radianRotation) {
		batch.draw(t, x - t.getRegionWidth() / 2f,
				y - t.getRegionHeight() / 2f, t.getRegionWidth() / 2f,
				t.getRegionHeight() / 2f, t.getRegionWidth(),
				t.getRegionHeight(), scaleX, scaleY, rad2deg(radianRotation));
	}

	//geometric stuff//
	
	public static void drawRectangle(Batch batch, float x, float y,
			float width, float height, int lineWidth) {
		drawScaled(batch, getSq(), x, y, width, lineWidth);
		drawScaled(batch, getSq(), x, y + height - lineWidth, width, lineWidth);
		drawScaled(batch, getSq(), x, y + lineWidth, lineWidth, height
				- lineWidth * 2);
		drawScaled(batch, getSq(), x + width - lineWidth, y + lineWidth,
				lineWidth, height - lineWidth * 2);
	}

	public static void fillRectangle(Batch batch, float x, float y,
			float width, float height) {
		Draw.drawScaled(batch, Draw.getSq(), x, y, width, height);
	}

	public static void drawLine(Batch batch, float x, float y, float tX,
			float tY, float width) {
		float dist = (float) Math.sqrt((tX - x) * (tX - x) + (tY - y)
				* (tY - y));
		float radians = (float) Math.atan2(tY - y, tX - x);

		x += Math.sin(radians) * width / 2f;
		y -= Math.cos(radians) * width / 2f;

		Draw.drawRotatedScaled(batch, getSq(), x, y, dist, width, radians);
	}

	
	
	
	//utlities//
	
	public static float rad2deg(float rad) {
		return (float) (rad * 180f / Math.PI);
	}

	private static AtlasRegion wSq;

	public static AtlasRegion getSq() {
		if (wSq == null) {
			wSq = Main.atlas.findRegion("pixel");
		}
		return wSq;
	}
	
	public static Pixmap getPixmap(Texture t){
		t.getTextureData().prepare();
		return t.getTextureData().consumePixmap();
	}
	
	public static Pixmap getPixmap(TextureRegion t){
		return getPixmap(t.getTexture());
//		t.getTexture().getTextureData().prepare();
//		return t.getTexture().getTextureData().consumePixmap();
	}

	public static void drawSize(Batch batch, TextureRegion textureRegion, float x, float y, float width, float height) {
		batch.draw(textureRegion, x, y, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), width/textureRegion.getRegionWidth(), height/textureRegion.getRegionHeight(), 0);
		
	}

	public static void fillActor(Batch batch, Actor a) {
		Draw.fillRectangle(batch, a.getX(), a.getY(), a.getWidth(), a.getHeight());
	}

}
