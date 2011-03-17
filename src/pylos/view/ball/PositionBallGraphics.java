package pylos.view.ball;

import pylos.model.Position;
import pylos.view.BoardGraphics;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class PositionBallGraphics extends Geometry {
	public Position position;

	public PositionBallGraphics(Position model) {
		super(model.toString(), new Sphere(BoardGraphics.BALL_SAMPLES, BoardGraphics.BALL_SAMPLES, BoardGraphics.BALL_DIAMETER / 2));
		position = model;
	}
}
