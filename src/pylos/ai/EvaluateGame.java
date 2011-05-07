package pylos.ai;

import pylos.model.Ball;


public class EvaluateGame {
	public void evaluate(GameState gs) {
		countBallInHandPoint(gs);
		countRemovableBallsPoint(gs);
		countRawsPoint(gs);
	}

	private void countRawsPoint(GameState gs) {
		/**
		 * recup list des boules du joueur sur le plateau
		 * récup list des lignes/carré que forment chaque boules
		 * test si carré ou lignes partiel ! pas compter 2 fois le mm carré, et pas compté si carré occupé par autre joueur
		 */
//		List<Ball> balls = gs.onBoard;
//		List<List<Position>> lines;
//		for (Ball ball : balls) {
//			lines = ball.position.lines();
//		}
	}

	private void countRemovableBallsPoint(GameState gs) {
		for (Ball ball : gs.onBoard) {
			if(ball.isRemovable())
				gs.add(1);
		}
	}

	private void countBallInHandPoint(GameState gs) {
		gs.add(gs.onBoard.size() * 5);
	}
}
