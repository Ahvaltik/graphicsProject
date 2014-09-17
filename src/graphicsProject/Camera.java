package graphicsProject;

public class Camera implements Entity {

	public EntityType getEntityType() {
		return EntityType.CAMERA;
	}
	
	public Camera(float[] cameraPosition, float[] cameraFocusPosition){
		this.cameraPosition = cameraPosition;
		this.cameraFocusPosition = cameraFocusPosition;
	}
	
	public float[] cameraPosition;
	public float[] cameraFocusPosition;
	public void moveCamera(float x, float y, float z) {
		cameraPosition[0] += x;
		cameraPosition[1] += y;
		cameraPosition[2] += z;
		
		cameraFocusPosition[0] += x;
		cameraFocusPosition[1] += y;
		cameraFocusPosition[2] += z;
		System.out.println("Camera position " + Float.toString(cameraPosition[0]) + " " + Float.toString(cameraPosition[1]) + " " + Float.toString(cameraPosition[2]));
		System.out.println("Camera focus " + Float.toString(cameraFocusPosition[0]) + " " + Float.toString(cameraFocusPosition[1]) + " " + Float.toString(cameraFocusPosition[2]));
	}

	public float[] getCameraDirection() {
		return new float[]{
				cameraFocusPosition[0] - cameraPosition[0],
				cameraFocusPosition[1] - cameraPosition[1],
				cameraFocusPosition[2] - cameraPosition[2]
		};
	}

}
