#version 400
layout (location = 0) out vec4 FragColor;
uniform vec2 iResolution;
uniform float iTime;
uniform float x;
uniform float v[36];
// signed distance to a 2D rounded box
float sdRoundBox( in vec2 p, in vec2 b, in float r )
{
    vec2 q = abs(p) - b;
    return min(max(q.x,q.y),0.0) + length(max(q,0.0)) - r;
}

void main(void)
{

    vec2 p = (2.0*gl_FragCoord.xy-iResolution.xy)/iResolution.y;

	vec2 ra = 0.4 + 0.3*cos( iTime + vec2(0.0,1.57) + 0.0 );
    float px = clamp(x/iResolution.x, 0.05, 0.95)-0.5;
	float d = sdRoundBox( vec2(p.x-px*2.,p.y+0.9), vec2(0.1,0.02), 0.03 );
	for (int i=0; i<36;i++) {
	    d = min(d, sdRoundBox( vec2(p.x+0.75-(i%6)*0.3,p.y-0.15-(i/6)*0.13)*v[i], vec2(0.1,0.02), 0.03 ));
	}
    vec3 col = vec3(1.0) - sign(d)*vec3(0.1,0.4,0.7);
	col *= cos(iTime) - exp(-4.0*abs(d));
	col *= sin(iTime) + 0.2*cos(120.0*d);
	col = mix( col, vec3(1.0), 1.0-smoothstep(0.0, 0.01, abs(d)) );

	FragColor = vec4(col,ra.x);
}