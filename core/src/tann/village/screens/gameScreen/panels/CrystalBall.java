package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import tann.village.Images;
import tann.village.util.Colours;
import tann.village.util.Draw;
import tann.village.util.Slider;

public class CrystalBall extends Group{

    static ShaderProgram shader;
    int test;
    int slide;
    float testValue = (float)Math.random()*500;
    Slider slider;
    private static final int WIDTH = 130, HEIGHT= 130;

    private static boolean SLIDE_IT = false;


    private static CrystalBall self;
    public static CrystalBall get(){
        if(self == null) self = new CrystalBall();
        return self;
    }


    private CrystalBall() {
        setSize(WIDTH,HEIGHT);
        ShaderProgram.pedantic = true;
        shader = new ShaderProgram(Gdx.files.internal("shader/ball_vertex.glsl"), Gdx.files.internal("shader/ball_fragment.glsl"));
        test = shader.getUniformLocation("test");
        slide = shader.getUniformLocation("slide");
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }

        if(SLIDE_IT) {
            slider = new Slider("fate", 0.5f, Colours.light, Colours.dark);
            slider.setSize(getWidth(), 50);
            slider.setPosition(0, getY() - 260);
            addActor(slider);
        }
    }

    int fate = 0;
    public void setFate(int fate){
        this.fate=fate;
    }

    @Override
    public void act(float delta) {
        if(SLIDE_IT){
            fate = (int)(slider.getValue()*24)-12;
        }
        testValue+=delta/8f;
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.double_dark);
        float border = 60;
        Draw.fillEllipse(batch, getX()-border, getY()-border, 300,300);
//        Draw.fillRectangle(batch, getX()-10, getY()-10, 500, 500);
        batch.setColor(Colours.light);
//        Draw.drawSize(batch, Images.crystal, getX(), getY(), getWidth(), getHeight());


        float ballWidth = Images.ball.getWidth();
        float scaleIncrease = 10f;
        batch.setColor(Colours.blue_dark);
//        Draw.drawScaled(batch, Images.ball, getX()-scaleIncrease/2, getY()-scaleIncrease/2, (getWidth()+scaleIncrease)/ballWidth, (getHeight()+scaleIncrease)/ballWidth);
         scaleIncrease = 6f;
        batch.setColor(Colours.dark);
//        Draw.drawScaled(batch, Images.ball, getX()-scaleIncrease/2, getY()-scaleIncrease/2, (getWidth()+scaleIncrease)/ballWidth, (getHeight()+scaleIncrease)/ballWidth);
        batch.setShader(shader);
        shader.setUniformf(test, testValue);
        shader.setUniformf(slide, fate/24f + 0.5f);
        batch.end();
        batch.begin();

//        Draw.fillEllipse(batch, getX(), getY(), getWidth(), getHeight());
//        Draw.fillActor(batch,this);
        Draw.drawScaled(batch, Images.ball, getX(), getY(), getWidth()/ballWidth, getHeight()/ballWidth);
        batch.end();
        batch.begin();
        batch.setShader(SpriteBatch.createDefaultShader());


        batch.setColor(Colours.blue_light);
        for(int i=0;i<fate;i++){
            drawStar(batch, i);
        }
        batch.setColor(Colours.red);
        for(int i=0;i>fate;i--){
            drawStar(batch, i);
        }


        super.draw(batch, parentAlpha);
    }

    private static float starSize = 10;
    private static float radius = WIDTH/2 + 10;
    private void drawStar(Batch batch, float i){
        Draw.fillEllipse(batch,
                (float)(getX()+WIDTH/2 + radius * Math.sin(i*Math.PI/6f))-starSize/2,
                (float)(getY()+HEIGHT/2 +radius * Math.cos(i*Math.PI/6f))-starSize/2,
                starSize, starSize);
    }
}
