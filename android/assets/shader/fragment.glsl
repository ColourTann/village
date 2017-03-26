#ifdef GL_ES 
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision mediump float;
#else
#define MED
#define LOWP
#define HIGH
#endif

varying MED vec2 v_diffuseUV;
varying MED vec2 gl_PointCoord;
uniform sampler2D u_texture;
uniform sampler2D u_texture2;
varying vec4 v_color;
varying vec3 v_normal;
uniform float f1;
uniform float f2;
void main() {
	
	float x=v_color.r +v_diffuseUV.x/8.0;
	float y=v_color.g +v_diffuseUV.y/8.0;  
	vec4 diffuse = texture2D(u_texture, vec2(x,y));

	


	

	gl_FragColor.rgb = (diffuse.rgb);
	gl_FragColor.rgb+= v_normal;


	

	gl_FragColor.a = 1.0;
	
	
	

	
}	

