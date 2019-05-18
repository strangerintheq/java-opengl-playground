#version 400
layout (location = 0) out vec4 FragColor;
uniform vec2 resolution;
uniform float time;

void main(void) {
    vec2 t = vec2(1. + cos(time), 1. + sin(time))*123.;
    vec2 p = gl_FragCoord.xy;
    vec2 q = (p + p - resolution.xy) / resolution.x;
    for(int i = 0; i < 13; i++) {
        q = abs(q)/dot(q,q) -  t.xy/resolution.xy;
    }
    FragColor = vec4(q, q.x/q.y, 1.0);
}