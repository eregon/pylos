package pylos.view.ball;

import pylos.model.Position;

public class PositionBallGraphics extends BallGraphics {
	public Position position;

	public PositionBallGraphics(Position position) {
		super(position.toString());
		this.position = position;
	}
}
