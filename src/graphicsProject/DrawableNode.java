package graphicsProject;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class DrawableNode implements Entity {

	private EntityType entityType;
	private List<DrawableNode> children;
	private List<Triangle> triangles;
	private float[] translationVector;
	private float[] rotationVector;
	
	public DrawableNode(List<Triangle> triangles, float[] translationVector, float[] rotationVector){
		this.triangles = triangles;
		this.translationVector = translationVector;
		this.rotationVector = rotationVector;
		this.children = new LinkedList<DrawableNode>();
	}
	
	public void addNode(DrawableNode node){
		children.add(node);
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void render(GL2 gl) {
		
		//transformation
		gl.glTranslatef(getTranslationVector()[0], getTranslationVector()[1], getTranslationVector()[2]);
		gl.glRotatef(getRotationVector()[0], getRotationVector()[1], getRotationVector()[2], getRotationVector()[3]);
		
		gl.glBegin(GL.GL_TRIANGLES);
		
		for(Triangle triangle: triangles){
			triangle.render(gl);
		}
		gl.glEnd();
		for(DrawableNode child: children){
			child.render(gl);
		}
		
		//reverse transformation
		gl.glRotatef(-getRotationVector()[0], getRotationVector()[1], getRotationVector()[2], getRotationVector()[3]);
		gl.glTranslatef(-getTranslationVector()[0], -getTranslationVector()[1], -getTranslationVector()[2]);
	}

	public List<Triangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(List<Triangle> triangles) {
		this.triangles = triangles;
	}

	public float[] getTranslationVector() {
		return translationVector;
	}

	public void setTranslationVector(float[] translationVector) {
		this.translationVector = translationVector;
	}

	public float[] getRotationVector() {
		return rotationVector;
	}

	public void setRotationVector(float[] rotationVector) {
		this.rotationVector = rotationVector;
	}

}
