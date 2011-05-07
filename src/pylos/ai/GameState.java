package pylos.ai;

import java.util.List;

import pylos.model.Ball;

public class GameState {
	
	List<Ball> balls;
	List<Ball> onBoard;
	private int score;
	
	public GameState(List<Ball> balls) {
		this.balls = balls;
		onBoard = balls;
	}
	
	public int getScore() {
		return score;
	}
	
	public void add(int a) {
		score += a;
	}
}
