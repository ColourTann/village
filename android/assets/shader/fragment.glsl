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
varying vec2 v_diffuseUV;
uniform sampler2D u_texture;
uniform sampler2D u_texture2;
varying vec4 v_color;
varying vec3 v_normal;
uniform float f1;
uniform float f2;
uniform int side;
uniform float v_glow;
void main() {
	vec2 UV = v_diffuseUV/16.0;
	
	float faceFloat = float(floor(v_color.x*256.0));
	float fSmall = mod(faceFloat, 16.0);
	float fBig = (faceFloat-fSmall)/16.0;

	float baseFloat = float(floor(v_color.y*256.0));
	float bSmall = mod(baseFloat, 16.0);
	float bBig = (baseFloat-bSmall)/16.0;

	float lapelFloat = float(floor(v_color.z*256.0));
	float lSmall = mod(lapelFloat, 16.0);
	float lBig = (lapelFloat-lSmall)/16.0;

	gl_FragColor.rgba =  vec4(.1333,.1569,.1725,1);


	float mult=0;
	if(side != -1){
		if (floor(v_normal.x+0.1)==side){
			mult=v_glow;
		}
		else{
			mult=-v_glow;
		}
	}

	vec4 colour =texture2D(u_texture, vec2(bSmall/16.0+UV.x, bBig/16.0+UV.y));
	gl_FragColor.rgb =  gl_FragColor.rgb *(1-colour.a) +colourArray[floor(v_color.a*5)].rgb*(colour.a);
	

	colour =texture2D(u_texture, vec2(fSmall/16.0+UV.x, fBig/16.0+UV.y));
	gl_FragColor.rgb =  gl_FragColor.rgb *(1-colour.a) +colour.rgb*(colour.a);
	
	if(mult>0){
		
		colour = texture2D(u_texture, vec2(lSmall/16.0+UV.x, lBig/16.0+UV.y));
		float alpha = colour.a * mult*0.5;
		gl_FragColor.rgb +=  colour * alpha;
	}
	else if (mult<-10){
		colour =texture2D(u_texture, vec2(fSmall/16.0+UV.x, fBig/16.0+UV.y));
		float grey = (colour.r+colour.g+colour.b)/3.0;
		colour.rgb=grey;
		gl_FragColor.rgb =  gl_FragColor.rgb *(1-colour.a) +colour.rgb*(colour.a);
	}
	else{
		

	}
		
}	

