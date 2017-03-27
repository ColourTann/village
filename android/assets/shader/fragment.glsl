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

varying vec2 v_diffuseUV;
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
	vec4 diffuse = texture2D(u_texture2, vec2(x,y));
	gl_FragColor.rgb = (diffuse.rgb)*0.0001;


	vec4 coll =texture2D(u_texture, vec2(v_color.r+v_diffuseUV.x/8.0, v_color.g+v_diffuseUV.y/8.0))	;
	gl_FragColor.rgb = gl_FragColor.rgb * (1.0-coll.a) + (coll.rgb * coll.a);
	


	
}	

