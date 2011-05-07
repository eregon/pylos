package pylos.ai;

import java.util.List;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;

public class Move {
	Ball ball;
	Ball secondBallToRemove;		// in case of removables, avoid to duplicate object for nothing
	Position position;
	boolean removeStep;
	List<Move> removables;
	
	public Move(Ball ball, Position pos) {
		this.ball = ball;
		position = pos;
		removeStep = false;
	}
	
	public Move(Ball ball, String remove) {
		this.ball = ball;
	}
	
	public Move(Ball ball, Ball secondBall) {
		this.ball = ball;
		secondBallToRemove = secondBall;
	}
	
	public boolean anyLineOrSquareForMove() {
		ball.placeAt(position);
		if(Model.currentPlayer.anyLineOrSquare(position)) {
			ball.removeFromBoard();
			removeStep = true;
			return removeStep;
		}
		ball.removeFromBoard();
		return removeStep;
	}
}
