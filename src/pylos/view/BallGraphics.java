package pylos.view;

import pylos.Pylos;
import pylos.model.Ball;
import pylos.model.Model;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class BallGraphics {
	public final static float DIAMETER = 1.38f;
	Ball model;
	Geometry geometry;

	public BallGraphics(Ball model) {
		this.model = model;
	}

	public void create() {
		Material mat;
		Sphere s = new Sphere(50, 50, DIAMETER / 2);
		geometry = new Geometry(model.toString(), s);
		if (model.owner == Model.player1) {
			mat = new Material(Pylos.view.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
		} else {
			mat = new Material(Pylos.view.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
			mat.setFloat("Shininess", 0.5f);
		}
		geometry.setMaterial(mat);
	}
}
