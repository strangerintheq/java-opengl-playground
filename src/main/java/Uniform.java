import javax.media.opengl.GL4;
import java.nio.FloatBuffer;

class Uniform {

    int location;
    GL4 gl;

    Uniform(GL4 gl, int programId, String name) {
        this.gl = gl;
        location = gl.glGetUniformLocation(programId, name);
        if (location == -1) throw new IllegalStateException(name + " uniform not found");
    }

    static class Uniform2f extends Uniform {
        Uniform2f(GL4 gl, int programId, String name) {
            super(gl, programId, name);
        }

        void set(float a, float b) {
            gl.glUniform2f(location, a, b);
        }
    }

    static class Uniform1f extends Uniform {
        Uniform1f(GL4 gl, int programId, String name) {
            super(gl, programId, name);
        }

        void set(float a) {
            gl.glUniform1f(location, a);
        }
    }
    static class Uniform1fv extends Uniform {
        Uniform1fv(GL4 gl, int programId, String name) {
            super(gl, programId, name);
        }

        void set(FloatBuffer a) {
            gl.glUniform1fv(location, a.capacity(), a);
        }
    }
}
