package pylos.view;

import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

public class Lights {
	final static int radius = 25;
	final static int lights = 4;
	final static float distance = 11.5f;
	final static float height = 4;

	public Lights(View view) {
		PointLight light;
		float angle = FastMath.PI / lights;

		for (int i = 0; i < lights; i++) {
			angle += 2 * FastMath.PI / lights;
			light = new PointLight();
			light.setColor(ColorRGBA.White);
			light.setRadius(radius);
			light.setPosition(new Vector3f(distance * FastMath.cos(angle), height, distance * FastMath.sin(angle)));
			view.getRootNode().addLight(light);
		}
	}
}
