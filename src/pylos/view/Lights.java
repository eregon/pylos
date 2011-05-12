package pylos.view;

import java.util.LinkedList;
import java.util.List;

import pylos.Config;
import pylos.Pylos;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
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

		if (Config.FIRE) {
			float d = 10;
			for (int i = 0; i < 4; i++)
				createFire((i % 2 * 2 - 1) * d, (i - i % 2 - 1) * d);
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

	void createFire(float x, float y) {
		ParticleEmitter fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
		Material mat = new Material(Pylos.view.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
		mat.setTexture("Texture", Pylos.view.getAssetManager().loadTexture("Effects/Explosion/flame.png"));
		fire.setMaterial(mat);
		fire.setImagesX(2);
		fire.setImagesY(2); // 2x2 texture animation
		fire.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f)); // red
		fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
		fire.setInitialVelocity(new Vector3f(0, 2, 0));
		fire.setStartSize(1.5f);
		fire.setEndSize(0.1f);
		fire.setGravity(0);
		fire.setLowLife(0.5f);
		fire.setHighLife(3f);
		fire.setVelocityVariation(0.3f);
		fire.move(x, 0, y);
		node.attachChild(fire);
	}
}
