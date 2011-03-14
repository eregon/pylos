package pylos.view.ball;

import pylos.model.PositionBall;
import pylos.view.BoardGraphics;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class PositionBallGraphics extends Geometry {
	public PositionBall model;

	public PositionBallGraphics(PositionBall model) {
		super(model.toString(), new Sphere(50, 50, BoardGraphics.BALL_DIAMETER / 2));
		this.model = model;
	}
}
