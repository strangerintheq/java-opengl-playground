import com.jogamp.opengl.util.Animator;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.FloatBuffer;

public class Game extends GLCanvas implements GLEventListener, MouseMotionListener{

    long start = System.currentTimeMillis();

    Program program;
    Uniform.Uniform2f resolution;
    Uniform.Uniform1f time;
    Uniform.Uniform1f x;
    VBO unitQuad;
    int mx = 0;
    Uniform.Uniform1fv v;

    Game() {
        super(new GLCapabilities(GLProfile.getDefault()));
        addGLEventListener(this);
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.add(game, BorderLayout.CENTER);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.addMouseMotionListener(game);
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
        new Animator(game).start();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL4 gl = glAutoDrawable.getGL().getGL4();
        program = new Program(gl, "vertex", "game");
        resolution = program.u2f("iResolution");
        time = program.u1f("iTime");
        x = program.u1f("x");
        v = program.u1fv("v");
        v.set(FloatBuffer.wrap(new float[] {
                1,1,1e10f,1,1e10f,1,
                1,1e10f,1,1e10f,1,1,
                1,1,1e10f,1,1,1e10f,
                1,1,1e10f,1,1,1e10f,
                1e10f,1,1,1e10f,1,1,
                1,1e10f,1,1,1e10f,1
        }));
        unitQuad = new VBO(gl, VBO.UNIT_QUAD);
        unitQuad.bind(2);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        time.set(((float) (System.currentTimeMillis() - start))/1000f);
        x.set(mx);
        unitQuad.draw();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        resolution.set(width, height);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mx = e.getX();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mx = e.getX();
    }
}