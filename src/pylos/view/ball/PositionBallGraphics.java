package pylos.view.ball;

import pylos.model.Position;

public class PositionBallGraphics extends BallGraphics {
	public Position position;

	public PositionBallGraphics(Position model) {
		super(model.toString());
		position = model;
	}
}
