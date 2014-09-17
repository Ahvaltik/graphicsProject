package graphicsProject;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GLException;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TriangleTexture {
	private Texture GLTexture;
	private String filename;
	
	public TriangleTexture(String filename){
		this.filename = filename;
	}

	public Texture getGLTexture() {
		if(GLTexture == null){
			System.out.print(filename + "\n");
			try {
				this.GLTexture = TextureIO.newTexture(new File(filename), false);
			} catch (GLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return GLTexture;
	}

}
