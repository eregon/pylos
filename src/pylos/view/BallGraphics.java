package pylos.view;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

import pylos.Pylos;
import pylos.model.Ball;

public class BallGraphics {

	private Ball model;
	private Geometry geometry;

	public BallGraphics(Ball model) {
		this.model = model;
		Sphere s = new Sphere(50, 50, 0.5f);
		geometry = new Geometry("s", s);
		Material mat = new Material(Pylos.view.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
		mat.setColor("Color", ColorRGBA.randomColor());
		geometry.setMaterial(mat);
	}

	public Geometry getGeometry() {
		return geometry;
	}
}
