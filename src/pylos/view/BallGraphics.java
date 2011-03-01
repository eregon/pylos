package pylos.view;

import pylos.Pylos;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class BallGraphics {
	public final static float DIAMETER = 1.2f;
	public float x, y, z;
	Geometry geometry;


	public BallGraphics() {
		Sphere s = new Sphere(50, 50, DIAMETER/2);
		geometry = new Geometry("s", s);
		Material mat = new Material(Pylos.view.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
		mat.setColor("Color", ColorRGBA.randomColor());
		geometry.setMaterial(mat);
	}

	public void setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void move() {
		geometry.move(x, y, z);
	}
}
