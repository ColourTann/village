#version 100

in fData
{
    vec3 normal;
    vec4 color;
};

void main()
{
    gl_FragColor = frag.color;
}