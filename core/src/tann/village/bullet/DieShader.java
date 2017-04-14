package tann.village.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import tann.village.Images;
import tann.village.screens.gameScreen.villager.die.Die;

public class DieShader implements Shader{
	ShaderProgram program;
	Camera camera;
	RenderContext context;
	int u_projTrans;
	int u_worldTrans;
	int u_diffuseTexture;
	int v_data;
	int side;
	int glow;
	@Override
	public void init() {
        String vert = Gdx.files.internal("shader/vertex.glsl").readString();
        String frag = Gdx.files.internal("shader/fragment.glsl").readString();
        program = new ShaderProgram(vert, frag);
        if (!program.isCompiled())
            throw new GdxRuntimeException(program.getLog());
        u_projTrans = program.getUniformLocation("u_projViewTrans");
        u_worldTrans = program.getUniformLocation("u_worldTrans");
        u_diffuseTexture = program.getUniformLocation("u_diffuseTexture");
        side = program.getUniformLocation("side");
        v_data = program.getUniformLocation("v_data");
        glow = program.getUniformLocation("v_glow");
	}
	
	@Override
	public void dispose() {
		program.dispose();
	}


	@Override
	public int compareTo(Shader other) {
		return 0;
	}

	@Override
	public boolean canRender(Renderable instance) {
		return true;
	}

	float f =0;
	@Override
	public void begin(Camera camera, RenderContext context) {
		this.camera = camera;
		this.context = context;
		program.begin();
		program.setUniformMatrix(u_projTrans, camera.combined);
		Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);
		
		
		
		Images.side_brain.getTexture().bind(0);
		program.setUniformi("u_texture", 0);
		
		
		
		context.setDepthTest(GL20.GL_LEQUAL);
		context.setCullFace(GL20.GL_BACK);
	}

	@Override
	public void render(Renderable renderable) {
//		program.setUniformf(v_data, new Vector3(1,0,1));
//		System.out.println(renderable.userData);

		Die d = (Die)renderable.userData;
		program.setUniformf(glow, d.getGlow());
		program.setUniformi(side, d.getSide());
		program.setUniformMatrix(u_worldTrans, renderable.worldTransform);
		renderable.meshPart.render(program, true);
	}

	@Override
	public void end() {
		program.end();
	}

}
