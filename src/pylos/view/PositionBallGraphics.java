package pylos.view;

import pylos.Pylos;
import pylos.model.PositionBall;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class PositionBallGraphics {
	PositionBall model;
	public float x, y, z;
	Geometry geometry;

	public PositionBallGraphics(PositionBall model) {
		this.model = model;
	}

	public void setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void move() {
		geometry.move(x, y, z);
	}

	public void create() {
		Sphere s = new Sphere(50, 50, BallGraphics.DIAMETER / 2);
		geometry = new Geometry(model.toString(), s);
		Material mat = new Material(Pylos.view.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
		mat.setColor("Color", ColorRGBA.Gray);
		geometry.setMaterial(mat);
	}
}
