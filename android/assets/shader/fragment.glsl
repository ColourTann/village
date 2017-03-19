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
uniform float f1;
uniform float f2;
void main() {
	
	vec4 diffuse = texture2D(u_texture, v_diffuseUV)*1 + texture2D(u_texture2, v_diffuseUV)*(0.0001);
	gl_FragColor.rgb = (diffuse.rgb);
	gl_FragColor.a = 1.0;
}	