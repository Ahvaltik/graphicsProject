package graphicsProject;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Triangle {
	private TriangleVertex[] vertices;
	private TriangleTexture texture;
	
	public Triangle(TriangleVertex[] vertices, TriangleTexture texture){
		this.vertices = vertices;
		this.setTexture(texture);
	}
	public Triangle(float[] coords, TriangleTexture texture){
		this.vertices = new TriangleVertex[]{
				new TriangleVertex(new float[]{		// texture coords
						coords[0], coords[1]
				}, new float[]{						// coords
						coords[2], coords[3], coords[4]
				}),
				new TriangleVertex(new float[]{		// texture coords
						coords[5], coords[6]
				}, new float[]{						// coords
						coords[7], coords[8], coords[9]
				}),
				new TriangleVertex(new float[]{		// texture coords
						coords[10], coords[11]
				}, new float[]{						// coords
						coords[12], coords[13], coords[14]
				})
		};
		this.setTexture(texture);
	}

	public TriangleVertex[] getVertices() {
		return vertices;
	}

	public void setVertices(TriangleVertex[] vertices) {
		this.vertices = vertices;
	}
	
	public void render(GL2 gl){
		gl.glColor3f(0.1f, 0.2f, 0.3f);
		gl.glEnable(GL.GL_TEXTURE_2D);
		texture.getGLTexture().setTexParameteri(gl, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		texture.getGLTexture().setTexParameteri(gl, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
		texture.getGLTexture().setTexParameteri(gl, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		texture.getGLTexture().setTexParameteri(gl, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		texture.getGLTexture().bind(gl);
		gl.glBegin(GL.GL_TRIANGLES);
		for(int ii = 0; ii < 3; ii++){
			gl.glTexCoord2f(
					vertices[ii].getTextureCoord(Dimension.X), 
					vertices[ii].getTextureCoord(Dimension.Y));
			gl.glVertex3f(
					vertices[ii].getCoord(Dimension.X), 
					vertices[ii].getCoord(Dimension.Y), 
					vertices[ii].getCoord(Dimension.Z));
		}
		gl.glEnd();
	}

	public TriangleTexture getTexture() {
		return texture;
	}

	public void setTexture(TriangleTexture texture) {
		this.texture = texture;
	}
}
