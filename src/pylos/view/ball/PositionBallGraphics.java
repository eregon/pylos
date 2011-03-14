package pylos.view.ball;

import pylos.model.PositionBall;
import pylos.view.BoardGraphics;
import pylos.view.View;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class PositionBallGraphics extends Geometry {
	public PositionBall model;

	public PositionBallGraphics(PositionBall model) {
		super(model.toString(), new Sphere(50, 50, BoardGraphics.BALL_DIAMETER / 2));
		this.model = model;
	}

	public void create(View view) {
		Material mat = new Material(view.getAssetManager(), "Common/MatDefs/Misc/SolidColor.j3md");
		mat.setColor("Color", ColorRGBA.Gray);
		// setMaterial(mat);
	}
}
