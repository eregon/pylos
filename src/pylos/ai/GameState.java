package pylos.ai;

import java.util.Enumeration;
import java.util.List;

import pylos.model.Ball;

public class GameState implements Enumeration {
	
	List<Ball> balls;
	List<Ball> onBoard;
	List<GameState> possibleMoves;
	private int score;
	
	public GameState(List<Ball> balls) {
		for (Ball ball : balls) {
			if(ball.onBoard)
				onBoard.add(ball);
			else
				this.balls.add(ball);
		}
	}
	
	public GameState(List<Ball> onBoard, List<Ball> onSide) {
		balls = onSide;
		this.onBoard = onBoard;
	}
	
	public int getScore() {
		return score;
	}
	
	public void add(int a) {
		score += a;
	}

	@Override
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GameState nextElement() {
		// TODO Auto-generated method stub
		return null;
	}
}
