package pylos.view;

import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class Lights {
	final static int radius = 20;

	public Lights(View view) {
		PointLight light;
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				light = new PointLight();
				light.setColor(ColorRGBA.White);
				light.setRadius(radius);
				light.setPosition(new Vector3f((x == 0 ? 8 : -8), 4, (y == 0 ? 8 : -8)));
				view.getRootNode().addLight(light);
			}
		}
	}
}
