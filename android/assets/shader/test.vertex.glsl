uniform mat4 u_projViewTrans;
uniform mat3 u_normalMatrix;
attribute vec2 a_texCoord0;
attribute vec3 a_position;
attribute vec3 a_normal;
uniform vec4 u_diffuseUVTransform;
varying vec2 v_diffuseUV;
uniform mat4 u_worldTrans;
varying vec3 v_lightDiffuse;
uniform vec3 u_ambientCubemap[6];
struct DirectionalLight
{
	vec3 color;
	vec3 direction;
};
uniform DirectionalLight u_dirLights[numDirectionalLights];

void main() {

	v_diffuseUV = u_diffuseUVTransform.xy + a_texCoord0 * u_diffuseUVTransform.zw; // whoah this changes the texture locations!
	vec4 pos = u_worldTrans * vec4(a_position, 1.0); // this changes the actual geometry of the model
	gl_Position = u_projViewTrans * pos; // this changes the viewpoint of the models?
	vec3 normal = normalize(u_normalMatrix * a_normal); // seems to affect how much surfaces catch the light?
	vec3 ambientLight = vec3(0); // just general light level. Makes things get whiter
	vec3 squaredNormal = normal * normal; 
	vec3 isPositive  = step(0.0, normal);
	
	// just lighting stuff after here
	ambientLight += squaredNormal.x * mix(u_ambientCubemap[0], u_ambientCubemap[1], isPositive.x) +
			squaredNormal.y * mix(u_ambientCubemap[2], u_ambientCubemap[3], isPositive.y) +
			squaredNormal.z * mix(u_ambientCubemap[4], u_ambientCubemap[5], isPositive.z);
	v_lightDiffuse = ambientLight; // multiply this by stuff to make it LIGHTER OR DARKER
	for (int i = 0; i < numDirectionalLights; i++) {
		vec3 lightDir = -u_dirLights[i].direction;
		float NdotL = clamp(dot(normal, lightDir), 0.0, 1.0);
		vec3 value = u_dirLights[i].color * NdotL;
		v_lightDiffuse += value;
	}
}