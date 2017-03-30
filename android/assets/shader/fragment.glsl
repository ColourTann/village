#ifdef GL_ES 
#define LOWP lowp
#define MED mediump
#define HIGH highp
precision highp float;
#else
#define MED
#define LOWP
#define HIGH
#endif

varying vec2 v_diffuseUV;
uniform sampler2D u_texture;
uniform sampler2D u_texture2;
varying vec4 v_color;
varying vec3 v_normal;
uniform float f1;
uniform float f2;
void main() {
	vec2 UV = v_diffuseUV/16.0;
	
	float f = float(floor(v_color.x*256.0));
	vec4 faceColour =texture2D(u_texture, vec2(UV.x, f/16.0+UV.y));
	gl_FragColor.rgb =  faceColour.rgb*(faceColour.a);
}	

