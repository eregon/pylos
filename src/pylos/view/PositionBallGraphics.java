package pylos.view;

import pylos.Pylos;
import pylos.model.Model;
import pylos.model.PositionBall;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class PositionBallGraphics {
	public final static float DIAMETER = BallGraphics.DIAMETER;
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
		Sphere s = new Sphere(50, 50, DIAMETER / 2);
		geometry = new Geometry(model.toString(), s);
		Material mat = new Material(Pylos.view.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
		mat.setColor("Color", ColorRGBA.Gray);
		geometry.setMaterial(mat);
	}

	public void place() {
		float offset = (Model.ballsBySideAtLevel(model.level) - 1) * DIAMETER / 2;
		geometry.center().move(
				model.x * DIAMETER - offset,
				BoardGraphics.BOARD_HEIGHT + model.level * DIAMETER,
				model.y * DIAMETER - offset
				);
	}
}
