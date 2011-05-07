package pylos.ai;

import java.util.List;

import pylos.model.Ball;


public class EvaluateGame {
	public void evaluate(GameState gs) {
		countBallInHandPoint(gs);
		countRemovableBallsPoint(gs);
		countRawsPoint(gs);
	}

	private void countRawsPoint(GameState gs) {
		
	}

	private void countRemovableBallsPoint(GameState gs) {
		for (Ball ball : gs.onBoard) {
			if(ball.isRemovable())
				gs.add(1);
		}
	}

	private void countBallInHandPoint(GameState gs) {
		for (Ball ball : gs.onBoard) {
			if(!ball.onBoard) {
				gs.add(5);
				gs.onBoard.remove(ball);
			}
		}
	}
}
