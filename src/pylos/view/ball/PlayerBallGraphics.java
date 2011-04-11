package pylos.view.ball;

import pylos.model.Ball;
import pylos.view.View;

public class PlayerBallGraphics extends BallGraphics {
	public Ball model;

	public PlayerBallGraphics(Ball model) {
		super(model.toString());
		this.model = model;
	}

	public void create(View view) {
		model.owner.graphics.create(view);
		setMaterial(model.owner.graphics.ballMaterial);
	}
}
