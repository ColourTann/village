package tann.village.screens.gameScreen.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import tann.village.util.Colours;
import tann.village.util.Draw;

public class CrystalBall extends Actor{

    static ShaderProgram shader;

    public CrystalBall() {
        setSize(150,150);
        ShaderProgram.pedantic = true;
        shader = new ShaderProgram(Gdx.files.internal("shader/ball_vertex.glsl"), Gdx.files.internal("shader/ball_fragment.glsl"));
        if(!shader.isCompiled()){
            System.out.println(shader.getLog());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.z_white);
        batch.setShader(shader);

        Draw.fillEllipse(batch, getX(), getY(), getWidth(), getHeight());

        batch.setShader(SpriteBatch.createDefaultShader());
        super.draw(batch, parentAlpha);
    }
}
