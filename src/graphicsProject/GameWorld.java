package graphicsProject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

@SuppressWarnings("unused")
public class GameWorld implements GLEventListener, MouseMotionListener, MouseListener, KeyListener{
	GL2 gl;
	Camera camera;
	private String vertexShaderName;
	private String fragmentShaderName;
	private DrawableNode root;
	private GLU glu;
	
	public DrawableNode getRoot() {
		return root;
	}

	public void setRoot(DrawableNode root) {
		this.root = root;
	}

	public GameWorld(){
		camera = new Camera(
				new float[]{0, 0.6f, 0},
				new float[]{0, 0.6f, -1});
		vertexShaderName = null;
		fragmentShaderName = null;
		//loading textures;
		TriangleTexture wall = new TriangleTexture("wall.bmp");
		TriangleTexture ceiling = new TriangleTexture("ceiling.bmp");
		TriangleTexture floor = new TriangleTexture("floor.bmp");
		List<Triangle> triangles = new LinkedList<Triangle>();
		//constructing triangle list;
		triangles.add(new Triangle(new float[]{		// coords
				0, 0,
				3.5f, 1, -3.5f,
				0, 7,
				3.5f, 1, 3.5f,
				7, 0,
				-3.5f, 1, -3.5f
		}, ceiling));								//texture
		triangles.add(new Triangle(new float[]{		// coords
				7, 7,
				-3.5f, 1, 3.5f,
				0, 7,
				3.5f, 1, 3.5f,
				7, 0,
				-3.5f, 1, -3.5f
		}, ceiling));								//texture
		triangles.add(new Triangle(new float[]{		// texture coords
				0, 0,
				3.5f, 0, -3.5f,
				0, 7,
				3.5f, 0, 3.5f,
				7, 0,
				-3.5f, 0, -3.5f
		}, floor));								//texture
		triangles.add(new Triangle(new float[]{		// texture coords
				7, 7,
				-3.5f, 0, 3.5f,
				0, 7,
				3.5f, 0, 3.5f,
				7, 0,
				-3.5f, 0, -3.5f
		}, floor));								//texture
		for(int ii = 0; ii < 4; ii++)
			for(int jj = 0; jj < 6; jj++){
				triangles.add(new Triangle(new float[]{		// texture coords
								0, 0,
								-3.5f + ii*2, 1, -2.5f + jj,
								0, 1,
								-3.5f + ii*2, 0, -2.5f + jj,
								1, 0,
								-2.5f + ii*2, 1, -2.5f + jj
				}, wall));
				triangles.add(new Triangle(new float[]{		// texture coords
								1, 1,
								-2.5f + ii*2, 0, -2.5f + jj,
								0, 1,
								-3.5f + ii*2, 0, -2.5f + jj,
								1, 0,
								-2.5f + ii*2, 1, -2.5f + jj
				}, wall));
			}
		for(int ii = 0; ii < 4; ii++)
			for(int jj = 0; jj < 6; jj++){
				triangles.add(new Triangle(new float[]{		// texture coords
								0, 0,
								-2.5f + jj, 1, -3.5f + ii*2,
								0, 1,
								-2.5f + jj, 0, -3.5f + ii*2,
								1, 0,
								-2.5f + jj, 1, -2.5f + ii*2
				}, wall));
				triangles.add(new Triangle(new float[]{
								1, 1,
								-2.5f + jj, 0, -2.5f + ii*2,
								0, 1,
								-2.5f + jj, 0, -3.5f + ii*2,
								1, 0,
								-2.5f + jj, 1, -2.5f + ii*2
				}, wall));
			}
		root = new DrawableNode(triangles, new float[]{0, 0, 0}, new float[]{0, 0, 0, 0});
	}
	
	public void movePlayer(float forward, float right, float velocity){
		float[] changePosition = new float[3];
		float[] newPosition = new float[3];
		changePosition[0] = (camera.cameraFocusPosition[0] - camera.cameraPosition[0])*forward*velocity;
		changePosition[1] = 0;
		changePosition[2] = (camera.cameraFocusPosition[2] - camera.cameraPosition[2])*forward*velocity;
		
		changePosition[0] += -(camera.cameraFocusPosition[2] - camera.cameraPosition[2])*right*velocity;
		changePosition[2] += (camera.cameraFocusPosition[0] - camera.cameraPosition[0])*right*velocity;
		
		newPosition[0] = camera.cameraPosition[0] + changePosition[0];
		newPosition[1] = camera.cameraPosition[1] + changePosition[1];
		newPosition[2] = camera.cameraPosition[2] + changePosition[2];
		System.out.println("New camera position " + Float.toString(newPosition[0]) + " " + Float.toString(newPosition[1]) + " " + Float.toString(newPosition[2]));
		//looping space
		if(entityAllowed(EntityType.CAMERA, newPosition[0], newPosition[1], newPosition[2])){
			if(newPosition[0] >= 1){
				moveCamera(-2, 0, 0);
			}
			if(newPosition[0] < -1){
				moveCamera(2, 0, 0);
			}
			if(newPosition[2] >= 1){
				moveCamera(0, 0, -2);
			}
			if(newPosition[2] < -1){
				moveCamera(0, 0, 2);
			}
			moveCamera(changePosition[0], changePosition[1], changePosition[2]);
		}
	}
	
	public void moveCamera(float x, float y, float z){
		camera.moveCamera(x,y,z);
	}
	
	public boolean entityAllowed(EntityType type, float x, float y, float z) {
		if(type == EntityType.CAMERA){
			return (y >= 0 && y <= 2) &&
					//(Math.abs(x) <= 1) &&
					//(Math.abs(z) <= 1) &&
					((Math.abs(x) <= 0.5f) || (Math.abs(z) <= 0.5f))
					;
		}
		return false;
	}
	
	public static void loadShader(ShaderType type, String filename, GL2 gl){
		//todo
	}
	public static void turnOnLight0(GL2 gl, float[] lightPosition, float[] lightDirection) {
		gl.glEnable(GLLightingFunc.GL_LIGHTING);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPOT_DIRECTION, lightDirection, 0);
//		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, new float[]{0, 0, 200}, 0);
//		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPOT_DIRECTION, new float[]{0, 0, -1}, 0);
	}

	public static void setCamera(GL2 gl, GLU glu, float[] camPos,
			float[] camDir) {
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		
		glu.gluPerspective(45, 1, 0.01, 100);
		glu.gluLookAt(camPos[0], camPos[1], camPos[2], camDir[0], camDir[1], camDir[2], 0, 1, 0);
		
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	public void rotateCamera(float x, float y) {
		camera.cameraFocusPosition[0] = camera.cameraPosition[0] + (float)Math.sin(x*Math.PI/150);
		camera.cameraFocusPosition[1] = camera.cameraPosition[1] + (float)Math.cos(y*Math.PI/600);
		camera.cameraFocusPosition[2] = camera.cameraPosition[2] - (float)Math.cos(x*Math.PI/150);
	}

	private void renderWorld(GL2 gl) {
		gl.glClearDepth(1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		setCamera(gl, glu, camera.cameraPosition, camera.cameraFocusPosition);
		//turnOnLight0(gl, camera.cameraPosition, camera.getCameraDirection());
		turnOnLight0(gl, new float[]{ 0, 0, 0}, camera.getCameraDirection());
//		gl.glColor3f(0.9f, 0.5f, 0.2f);
//		gl.glBegin(GL.GL_TRIANGLE_FAN);
//		gl.glVertex3f(-20, -20, 0);
//		gl.glVertex3f(+20, -20, 0);
//		gl.glVertex3f(0, 20, 0);
//		gl.glEnd();
		
		root.render(gl);
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		rotateCamera(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		renderWorld(arg0.getGL().getGL2());
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	}

	@Override
	public void init(GLAutoDrawable arg0) {
		glu = new GLU();
		GL2 gl = arg0.getGL().getGL2();
		
		//gl.glEnable(GL.GL_CULL_FACE);
		//gl.glCullFace(GL.GL_FRONT);
		
		//depth buffer
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthMask(true);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glDepthRange(0, 1);
		
		//set camera
		
		setCamera(gl, (new GLU()), camera.cameraPosition, camera.cameraFocusPosition);
		
		//color blend
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		
		//lighting
		gl.glEnable(GLLightingFunc.GL_LIGHTING);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, new float[]{ 1f, 1f, 1f, 1 }, 0);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, new float[]{ 1f, 1f, 1f, 1 }, 0);
		//turnOnLight0(gl, camera.cameraPosition, camera.getCameraDirection());
		turnOnLight0(gl, new float[]{ 0, 0, 0}, camera.getCameraDirection());
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPOT_CUTOFF, new float[]{100}, 0);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_QUADRATIC_ATTENUATION, new float[]{1}, 0);
		gl.glEnable(GLLightingFunc.GL_LIGHT0);
		
		if(fragmentShaderName != null)
			loadShader(ShaderType.FRAGMENT_SHADER, fragmentShaderName, gl);
		if(vertexShaderName != null)
			loadShader(ShaderType.FRAGMENT_SHADER, vertexShaderName, gl);
		this.gl = gl;
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		GL gl = arg0.getGL();
		gl.glViewport(arg1, arg2, arg3, arg4);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_UP){
			movePlayer(1, 0, 0.1f);
		} else if(arg0.getKeyCode() == KeyEvent.VK_DOWN){
			movePlayer(-1, 0, 0.1f);
		} else if(arg0.getKeyCode() == KeyEvent.VK_RIGHT){
			movePlayer(0, 1, 0.1f);
		} else if(arg0.getKeyCode() == KeyEvent.VK_LEFT){
			movePlayer(0, -1, 0.1f);
		}// else if(arg0.getKeyCode() == KeyEvent.VK_Q){
//			moveCamera(0.1f, 0, 0);
//		} else if(arg0.getKeyCode() == KeyEvent.VK_A){
//			moveCamera(-0.1f, 0, 0);
//		} else if(arg0.getKeyCode() == KeyEvent.VK_W){
//			moveCamera(0, 0.1f, 0);
//		} else if(arg0.getKeyCode() == KeyEvent.VK_S){
//			moveCamera(0, -0.1f, 0);
//		} else if(arg0.getKeyCode() == KeyEvent.VK_E){
//			moveCamera(0, 0, 0.1f);
//		} else if(arg0.getKeyCode() == KeyEvent.VK_D){
//			moveCamera(0, 0, -0.1f);
//		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
