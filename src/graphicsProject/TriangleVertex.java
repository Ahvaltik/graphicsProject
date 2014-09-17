package graphicsProject;

public class TriangleVertex {
	private float[] textureCoords;
	private float[] coords;

	public TriangleVertex(float[] textureCoords, float[] coords) {
		this.coords = coords;
		this.textureCoords = textureCoords;
	}

	public float getTextureCoord(Dimension d) {
		if(d == Dimension.X){
			return textureCoords[0];
		} else if(d == Dimension.Y){
			return textureCoords[1];
		}
		return 0;
	}

	public float getCoord(Dimension d) {
		if(d == Dimension.X){
			return coords[0];
		} else if(d == Dimension.Y){
			return coords[1];
		} else if(d == Dimension.Z){
			return coords[2];
		}
		return 0;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public void setTextureCoords(float[] textureCoords) {
		this.textureCoords = textureCoords;
	}

	public float[] getCoords() {
		return coords;
	}

	public void setCoords(float[] coords) {
		this.coords = coords;
	}

}
