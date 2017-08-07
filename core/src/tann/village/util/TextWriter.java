package tann.village.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import tann.village.Images;

import java.util.HashMap;
import java.util.Map;

public class TextWriter extends Group {
    public TextWriter(String text, BitmapFont font) {
        String[] split = text.split("[\\[\\]]");
        int index = (text.charAt(0)=='[')?1:0;
        float x = 0;
        for(String s:split){
            if(index%2==0){
                // text
                TextBox tb =  new TextBox(s, font, -1, Align.center);
                addActor(tb);
                tb.setX(x);
                x += tb.getWidth();
            }
            else{
                // image
                TextureRegion tr = textureMap.get(s);
                if(tr==null){
                    System.err.println("unable to find texture "+s+" for string "+text);
                }
                float scale = font.getCapHeight()/tr.getRegionHeight();
                ImageActor ia = new ImageActor(tr, tr.getRegionWidth()*scale, tr.getRegionHeight()*scale);
                addActor(ia);
                ia.setX(x);
                x+=ia.getWidth();
            }
            index++;
        }
        setSize(x, font.getCapHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(0,0,0,.5f);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }

    private static Map<String, TextureRegion> textureMap = new HashMap<>();

    public static void setup(){
        textureMap.put("food", Images.food);
        textureMap.put("food_storage", Images.food_storage);
        textureMap.put("brain", Images.brain);
        textureMap.put("wood", Images.wood);
        textureMap.put("morale", Images.morale);
        textureMap.put("fate", Images.fate);
        textureMap.put("hut", Images.obj_village);
    }
}
