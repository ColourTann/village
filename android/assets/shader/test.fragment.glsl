#ifdef GL_ES 
precision mediump float;
#endif

uniform vec3 u_colorU;
uniform vec3 u_colorV;
uniform float flo;
uniform float pos;

varying vec2 v_texCoord0;

void main() {
	float mode = mod(flo+pos, 2.0);
	float modf = mod(flo*1.2+pos, 2.0);
	float modg = mod(flo*1.5+pos, 2.0);
	
	
    gl_FragColor = vec4(
    mode - max(0, mode-1)*2, 
    modf - max(0, modf-1)*2, 
    modg - max(0, modg-1)*2, 
    1.0);
}