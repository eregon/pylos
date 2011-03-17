package pylos.view;

import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class Lights {
	DirectionalLight light;

	public Lights(View view) {
		light = new DirectionalLight();
		light.setDirection((new Vector3f(-0.1f, -0.7f, -1.0f)).normalize());
		view.getRootNode().addLight(light);
	}
}
