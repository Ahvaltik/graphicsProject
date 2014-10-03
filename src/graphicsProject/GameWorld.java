package graphicsProject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GL2ES2;
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
		//fragmentShaderName = "simple.frag";
		fragmentShaderName = null;
		//loading textures;
		TriangleTexture wall = new TriangleTexture("wall.bmp");
		TriangleTexture ceiling = new TriangleTexture("ceiling.bmp");
		TriangleTexture floor = new TriangleTexture("floor.bmp");
		List<Triangle> triangles = new LinkedList<Triangle>();
		//constructing triangle list;
//		triangles.add(new Triangle(new float[]{		// coords
//				0, 0,
//				3.5f, 1, -3.5f,
//				0, 7,
//				3.5f, 1, 3.5f,
//				7, 0,
//				-3.5f, 1, -3.5f
//		}, ceiling));								//texture
//		triangles.add(new Triangle(new float[]{		// coords
//				7, 7,
//				-3.5f, 1, 3.5f,
//				0, 7,
//				3.5f, 1, 3.5f,
//				7, 0,
//				-3.5f, 1, -3.5f
//		}, ceiling));								//texture
//		triangles.add(new Triangle(new float[]{		// texture coords
//				0, 0,
//				3.5f, 0, -3.5f,
//				0, 7,
//				3.5f, 0, 3.5f,
//				7, 0,
//				-3.5f, 0, -3.5f
//		}, floor));								//texture
//		triangles.add(new Triangle(new float[]{		// texture coords
//				7, 7,
//				-3.5f, 0, 3.5f,
//				0, 7,
//				3.5f, 0, 3.5f,
//				7, 0,
//				-3.5f, 0, -3.5f
//		}, floor));								//texture
		for(int ii = 0; ii < 7; ii++)
			for(int jj = 0; jj < 7; jj++){
				triangles.add(new Triangle(new float[]{		// texture coords
								0, 0,
								-3.5f + ii, 0, -3.5f + jj,
								0, 1,
								-3.5f + ii, 0, -2.5f + jj,
								1, 0,
								-2.5f + ii, 0, -3.5f + jj
				}, floor));
				triangles.add(new Triangle(new float[]{		// texture coords
								1, 1,
								-2.5f + ii, 0, -2.5f + jj,
								0, 1,
								-3.5f + ii, 0, -2.5f + jj,
								1, 0,
								-2.5f + ii, 0, -3.5f + jj
				}, floor));
			}
		for(int ii = 0; ii < 7; ii++)
			for(int jj = 0; jj < 7; jj++){
				triangles.add(new Triangle(new float[]{		// texture coords
								0, 0,
								-3.5f + ii, 1, -3.5f + jj,
								0, 1,
								-3.5f + ii, 1, -2.5f + jj,
								1, 0,
								-2.5f + ii, 1, -3.5f + jj
				}, ceiling));
				triangles.add(new Triangle(new float[]{		// texture coords
								1, 1,
								-2.5f + ii, 1, -2.5f + jj,
								0, 1,
								-3.5f + ii, 1, -2.5f + jj,
								1, 0,
								-2.5f + ii, 1, -3.5f + jj
				}, ceiling));
			}
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
		changePosition[0] = camera.getCameraDirection()[0]*forward*velocity;
		changePosition[1] = 0;
		changePosition[2] = camera.getCameraDirection()[2]*forward*velocity;
		
		changePosition[0] += camera.getCameraDirection()[2]*right*velocity;
		changePosition[2] += -camera.getCameraDirection()[0]*right*velocity;
		
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
			moveCamera(changePosition[0], 0, changePosition[2]);
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
	
	public void loadShader(ShaderType type, String filename, GL2 gl){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			String content = "";
			int shaderId = 0;
			if(type == ShaderType.FRAGMENT_SHADER){
				shaderId = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
			} else if(type == ShaderType.VERTEX_SHADER){
				shaderId = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
			}
			
			while((line=reader.readLine())!=null){
				content += line + "/n";
			}
			reader.close();
			String[] src = new String[]{content};
			gl.glShaderSource(shaderId, 1, src, null, 0);
			gl.glCompileShader(shaderId);
			int shaderProgram = gl.glCreateProgram();
			gl.glAttachShader(shaderProgram, shaderId);
			gl.glLinkProgram(shaderProgram);
			gl.glValidateProgram(shaderProgram);
			gl.glUseProgram(shaderProgram);
			gl.glUseProgram(0);
			System.out.println("Shader loaded " + filename + " " + Integer.toString(shaderId) + " " + Integer.toString(shaderProgram));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public static void turnOnLight0(GL2 gl, float[] lightPosition, float[] lightDirection) {
		gl.glEnable(GLLightingFunc.GL_LIGHTING);
		gl.glEnable(GLLightingFunc.GL_LIGHT0);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, lightPosition, 0);
//		gl.glLightf(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPOT_CUTOFF, 20);
//		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPOT_DIRECTION, lightDirection, 0);
	}

	public void setCamera(GL2 gl, GLU glu) {
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		
		glu.gluPerspective(45, 1, 0.01, 2.5);
//		glu.gluLookAt(camera.cameraPosition[0], camera.cameraPosition[1], camera.cameraPosition[2], camera.cameraFocusPosition[0], camera.cameraFocusPosition[1], camera.cameraFocusPosition[2], 0, 1, 0);
		glu.gluLookAt(0, 0, 0, 0, 0, -1, 0, 1, 0);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		
		gl.glRotated(-Math.asin(camera.getCameraDirection()[1])*180/Math.PI, 1, 0, 0);
		gl.glRotated(Math.atan(camera.getCameraDirection()[2]/camera.getCameraDirection()[0])*180/Math.PI+(camera.getCameraDirection()[0]>0?90:-90), 0, 1, 0);
		gl.glTranslatef(-camera.cameraPosition[0], -camera.cameraPosition[1], -camera.cameraPosition[2]);
		
	}
	
	private float[] normalize(float[] x) {
		return new float[]{
				(float) (x[0]/Math.sqrt(x[0]*x[0]+x[1]*x[1]+x[2]*x[2])),
				(float) (x[1]/Math.sqrt(x[0]*x[0]+x[1]*x[1]+x[2]*x[2])),
				(float) (x[2]/Math.sqrt(x[0]*x[0]+x[1]*x[1]+x[2]*x[2]))
		};
	}

	private float[] cross(float[] x, float[] y) {
		return new float[]{
				x[1]*y[2]-y[1]*x[2],
				x[2]*y[0]-y[0]*x[2],
				x[0]*y[1]-y[0]*x[1]
		};
	}

	public void rotateCamera(float x, float y) {
		camera.cameraFocusPosition[0] = camera.cameraPosition[0] + (float)Math.sin(x*Math.PI/200);
		camera.cameraFocusPosition[1] = camera.cameraPosition[1] + (float)Math.cos(y*Math.PI/600);
		camera.cameraFocusPosition[2] = camera.cameraPosition[2] - (float)Math.cos(x*Math.PI/200);
	}

	private void renderWorld(GL2 gl) {
		gl.glClearDepth(1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		setCamera(gl, glu);
		//turnOnLight0(gl, camera.cameraPosition, camera.getCameraDirection());
		turnOnLight0(gl, new float[]{ 0.1f, 0.1f, 0.1f}, new float[]{ -0.1f, -0.1f, -0.1f});
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
		
//		gl.glEnable(GL.GL_CULL_FACE);
//		gl.glCullFace(GL.GL_FRONT);
		
		//depth buffer
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthMask(true);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glDepthRange(0, 1);
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		//set camera
		
		setCamera(gl, (new GLU()));
		
		//color blend
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		
		//lighting
		gl.glEnable(GLLightingFunc.GL_LIGHTING);
		gl.glEnable(GLLightingFunc.GL_LIGHT0);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, new float[]{ 0.2f, 0.2f, 0.2f, 1 }, 0);
		gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, new float[]{ 0.1f, 0.1f, 0.1f, 1 }, 0);
//		turnOnLight0(gl, camera.cameraPosition, camera.getCameraDirection());
//		turnOnLight0(gl, new float[]{ 0, 0, 0}, camera.getCameraDirection());

		//fog
		gl.glEnable(GL2.GL_FOG);
		gl.glFogi(GL2.GL_FOG_MODE, GL2.GL_LINEAR);
		gl.glFogfv(GL2.GL_FOG_COLOR, new float[]{ 0f, 0f, 0f, 1}, 0);
		//gl.glFogf(GL2ES1.GL_FOG_DENSITY, 0.9f);
		gl.glFogf(GL2.GL_FOG_START, 2f);
		gl.glFogf(GL2.GL_FOG_END, 2.5f);
		gl.glHint(GL2.GL_FOG_HINT, GL2.GL_NICEST);
		
		
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
		}
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN){
			movePlayer(-1, 0, 0.1f);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT){
			movePlayer(0, 1, 0.1f);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT){
			movePlayer(0, -1, 0.1f);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

}
