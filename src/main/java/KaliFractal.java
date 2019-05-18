import com.jogamp.opengl.util.Animator;

import javax.media.opengl.GL2;
import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.BorderLayout;

public class KaliFractal extends GLCanvas implements GLEventListener{

    long start = System.currentTimeMillis();

    Program program;
    Uniform.Uniform2f resolution;
    Uniform.Uniform1f time;
    VBO unitQuad;

    KaliFractal() {
        super(new GLCapabilities(GLProfile.getDefault()));
        addGLEventListener(this);
    }

    public static void main(String[] args) {
        KaliFractal fullScreenTriangle = new KaliFractal();
        JFrame frame = new JFrame();
        frame.add(fullScreenTriangle, BorderLayout.CENTER);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
        new Animator(fullScreenTriangle).start();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL4 gl = glAutoDrawable.getGL().getGL4();
        program = new Program(gl, "vertex", "kali");
        resolution = program.u2f("resolution");
        time = program.u1f("time");
        unitQuad = new VBO(gl, VBO.UNIT_QUAD);
        unitQuad.bind(2);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        time.set(((float) (System.currentTimeMillis() - start))/1000f);
        unitQuad.draw();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        resolution.set(width, height);
    }
}