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

varying vec3 v_normal;
varying MED vec2 v_diffuseUV;
uniform sampler2D u_diffuseTexture;
varying vec3 v_lightDiffuse;

void main() {
	vec3 normal = v_normal;
	vec4 diffuse = texture2D(u_diffuseTexture, v_diffuseUV);
	gl_FragColor.rgb = (diffuse.rgb * v_lightDiffuse);
	gl_FragColor.a = 1.0;
}