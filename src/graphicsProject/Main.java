package graphicsProject;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

public class Main{

	public static void main(String[] args) {
		final GameWorld gameWorld = new GameWorld();
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities glc = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(glc);
		Frame frame = new Frame("Dung");
		frame.setSize(600, 600);
		frame.add(canvas);
		frame.setVisible(true);
		canvas.addMouseListener(gameWorld);
		canvas.addMouseMotionListener(gameWorld);
		
		canvas.addKeyListener(gameWorld);
		frame.addKeyListener(gameWorld);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		canvas.addGLEventListener(gameWorld);
		FPSAnimator thread = new FPSAnimator(canvas,120);
		thread.start();
		
	}

}
