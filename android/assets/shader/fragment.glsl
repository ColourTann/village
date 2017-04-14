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

uniform vec3 colourArray[5]=vec3[5](
	vec3(0.54901963,0.08627451,0.08627451),
	vec3(0.6117647,0.4117647,0.20784314),
	vec3(0.8980392,0.7921569,0.9843137),
	vec3(0.23137255,0.654902,0.7254902),
	vec3(0.36862746,0.63529414,0.15294118) );

varying vec2 v_diffuseUV; // default diffuse
uniform sampler2D u_texture; // main big texture
varying vec4 v_color; // r
varying vec3 v_normal;
uniform int side;
uniform float v_glow;
void main() {

	// setup tex location vec2s //

	// ------------------------ //
	vec2 UV = v_diffuseUV/16.0;
	
	float faceFloat = float(floor(v_color.x*256.0));
	float fSmall = mod(faceFloat, 16.0);
	float fBig = (faceFloat-fSmall)/16.0;
	vec2 face = vec2(fSmall/16.0, fBig/16.0);

	float highlightFloat = float(floor(v_color.y*256.0));
	float hSmall = mod(highlightFloat, 16.0);
	float hBig = (highlightFloat-hSmall)/16.0;
	vec2 highlight = vec2(hSmall/16.0, hBig/16.0);

	float lapelFloat = float(floor(v_color.z*256.0));
	float lSmall = mod(lapelFloat, 16.0);
	float lBig = (lapelFloat-lSmall)/16.0;
	vec2 lapel = vec2(lSmall/16.0, lBig/16.0);
	// ------------------------ //

	// base colour 
	gl_FragColor.rgba =  vec4(.1333,.1569,.1725,1);

	// add lapels
	vec3 lapelColour = colourArray[floor(v_color.a*5)].rgb;
	vec4 colour = texture2D(u_texture, lapel+UV);
	gl_FragColor.rgb =  gl_FragColor.rgb *(1-colour.a) +lapelColour.rgb*(colour.a);
	
	// add face image	
	colour = texture2D(u_texture, face+UV);
	gl_FragColor.rgb =  gl_FragColor.rgb *(1-colour.a) +colour.rgb*(colour.a);

	// weird stuff to avoid conditionals
	float correctSide = (side == floor(v_normal.x+0.1))?1:0;
	float wrongSide = (side == -1) ?0:(1-correctSide);
	
	// draw highlight face if applicable
	colour = texture2D(u_texture, highlight+UV);
	float alpha = colour.a * v_glow * correctSide * 0.5;
	gl_FragColor.rgb +=  colour * alpha * v_glow * correctSide;

	// draw grey face if applicable
	colour =texture2D(u_texture, face+UV);
	colour.rgb=0.3;
	colour.a = colour.a*wrongSide;
	gl_FragColor.rgb =  gl_FragColor.rgb *(1-colour.a) +colour.rgb*(colour.a);
}	

