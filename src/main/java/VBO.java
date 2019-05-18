import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL2;
import javax.media.opengl.GL4;

public class VBO {

    static float[] UNIT_QUAD = {
            -1, -1,
            -1, +2,
            +1, -1,
            +1, +1
    };

    int id;
    GL4 gl;
    int len;

    VBO(GL4 gl, float[] vertices) {
        this.gl = gl;
        int[] id = new int[1];
        gl.glGenBuffers(1, id, 0);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, id[0]);
        gl.glBufferData(GL4.GL_ARRAY_BUFFER,(len = vertices.length) * 4,
                Buffers.newDirectFloatBuffer(vertices).rewind(),GL4.GL_STATIC_DRAW);
        this.id = id[0];
    }

    void bind(int components) {
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, id);
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer( 0, components, GL4.GL_FLOAT,  false, 0, 0 );
    }

    void draw() {
        gl.glDrawArrays(GL2.GL_TRIANGLE_STRIP, 0, len);
    }
}
