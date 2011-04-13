package pylos.view;

import java.util.LinkedList;
import java.util.List;

import pylos.Config;

import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Lights {
	final static int radius = 48;
	final static int n_lights = 4;
	final static float distance = 15.5f;
	final static float height = 4;

	final Node node;
	final List<Light> lights = new LinkedList<Light>();

	public Lights(Node lightNode) {
		node = lightNode;
		switchLightMode(Config.LOW_GRAPHICS);
	}

	public void switchLightMode(boolean low_graphics) {
		if (low_graphics)
			createLowGraphicsLights();
		else
			createHighGraphicsLights();
	}

	void createHighGraphicsLights() {
		removeLights();

		PointLight light;
		float angle = FastMath.PI / n_lights;

		for (int i = 0; i < n_lights; i++) {
			angle += 2 * FastMath.PI / n_lights;
			light = new PointLight();
			light.setColor(ColorRGBA.White);
			light.setRadius(radius);
			light.setPosition(new Vector3f(distance * FastMath.cos(angle), height, distance * FastMath.sin(angle)));
			lights.add(light);
		}

		addLights();
	}

	void createLowGraphicsLights() {
		removeLights();

		DirectionalLight light = new DirectionalLight();
		light.setDirection((new Vector3f(0.1f, -1, 0.2f)).normalizeLocal());
		lights.add(light);

		addLights();
	}

	void removeLights() {
		for (Light light : lights) {
			node.removeLight(light);
		}
		lights.clear();
	}

	void addLights() {
		for (Light light : lights) {
			node.addLight(light);
		}
	}
}
