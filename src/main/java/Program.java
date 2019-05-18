import javax.media.opengl.GL4;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Program {

    GL4 gl;
    int id;

    Program(GL4 gl, String vs, String fs){
        this.gl = gl;
        id = gl.glCreateProgram();
        shader(GL4.GL_VERTEX_SHADER, Files.read(vs + ".glsl"));
        shader(GL4.GL_FRAGMENT_SHADER, Files.read(fs + ".glsl"));
        gl.glLinkProgram(id);
        validateProgram(gl);
        use();
    }

    private void validateProgram(GL4 gl) {
        gl.glValidateProgram(id);
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(id, GL4.GL_LINK_STATUS, intBuffer);
        if (intBuffer.get(0) != GL4.GL_TRUE) {
            if (id > 0) gl.glDeleteProgram(id);
            throw new IllegalStateException();
        }
    }

    void shader(int type, String src) {
        int id = gl.glCreateShader(type);
        gl.glShaderSource(id, 1, new String[] {src}, null, 0);
        gl.glCompileShader(id);
        validateShader(id);
        gl.glAttachShader(this.id, id);
    }

    void validateShader(int id) {
        int status = getIntFromShader(id, GL4.GL_COMPILE_STATUS);
        if (status == 0) {
            int logLength = getIntFromShader(id, GL4.GL_INFO_LOG_LENGTH);
            ByteBuffer infoLog = ByteBuffer.allocate(logLength);
            IntBuffer intValue = IntBuffer.allocate(1);
            gl.glGetShaderInfoLog(id, logLength, intValue, infoLog);
            byte[] infoBytes = new byte[intValue.get()];
            infoLog.get(infoBytes);
            if (id > 0) gl.glDeleteShader(id);
            throw new IllegalStateException(new String(infoBytes));
        }
    }

    int getIntFromShader(int shaderId, int parameter) {
        IntBuffer intValue = IntBuffer.allocate(1);
        gl.glGetShaderiv(shaderId, parameter, intValue);
        return intValue.get();
    }

    void use() {
        gl.glUseProgram(id);
    }

    Uniform.Uniform2f u2f(String name) {
        return new Uniform.Uniform2f(gl, id, name);
    }

    Uniform.Uniform1f u1f(String name) {
        return new Uniform.Uniform1f(gl, id, name);
    }

    Uniform.Uniform1fv u1fv(String name) {
        return new Uniform.Uniform1fv(gl, id, name);
    }
}
