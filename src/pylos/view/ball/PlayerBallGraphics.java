package pylos.view.ball;

import pylos.model.Ball;
import pylos.view.BoardGraphics;
import pylos.view.View;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class PlayerBallGraphics extends Geometry {
	Ball model;

	public PlayerBallGraphics(Ball model) {
		super(model.toString(), new Sphere(BoardGraphics.BALL_SAMPLES, BoardGraphics.BALL_SAMPLES, BoardGraphics.BALL_DIAMETER / 2));
		this.model = model;
	}

	public void create(View view) {
		model.owner.graphics.create(view);
		setMaterial(model.owner.graphics.ballMaterial);
	}
}
