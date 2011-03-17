package pylos.view.ball;

import pylos.view.BoardGraphics;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public abstract class BallGraphics extends Geometry {
	public BallGraphics(String description) {
		super(description, new Sphere(BoardGraphics.BALL_SAMPLES, BoardGraphics.BALL_SAMPLES, BoardGraphics.BALL_DIAMETER / 2));
	}
}
