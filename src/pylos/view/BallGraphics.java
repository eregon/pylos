package pylos.view;

import pylos.model.Ball;
import pylos.model.Model;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class BallGraphics extends Geometry {
	public final static float DIAMETER = 1.38f;
	Ball model;

	public BallGraphics(Ball model) {
		super(model.toString(), new Sphere(50, 50, DIAMETER / 2));
		this.model = model;
	}

	public void create(View view) {
		Material mat;
		if (model.owner == Model.player1) {
			mat = new Material(view.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
		} else {
			mat = new Material(view.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
			mat.setFloat("Shininess", 0.5f);
		}
		setMaterial(mat);
	}
}
