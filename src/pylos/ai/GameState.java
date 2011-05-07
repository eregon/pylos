package pylos.ai;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Position;

@SuppressWarnings("rawtypes")
public class GameState implements Enumeration {
	
	List<Ball> balls;
	List<Ball> onBoard;
	List<GameState> possibleMoves;
	List<Move> moves;
	private int score;
	
	public GameState(List<Ball> balls) {
		moves = new LinkedList<Move>();
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
	
	public void generatePosibleMove() {
		for (Ball ball : onBoard) {
			if(ball.isMountableByCurrentPlayer()) {
				for (Position pos : Model.getPositionsToMount(ball)) {
					Move move = new Move(ball, pos);
					if(move.anyLineOrSquareForMove())
						addRemovableStep(move);
					moves.add(move);
				}
			}
		}
		for (Position pos : Model.getPositionBalls()) {
			moves.add(new Move(balls.get(0), pos));
		}
	}

	private void addRemovableStep(Move move) {
		for (Ball ball : onBoard) {
			if(ball.isRemovableByCurrentPlayer())
				move.removables.add(new Move(ball, "remove"));
			for (Ball ball2 : onBoard) {
				if(ball2 != ball && ball2.isRemovableByCurrentPlayer() && ball.isRemovableByCurrentPlayer())
					move.removables.add(new Move(ball, ball2));
			}
		}
		
	}

	@Override
	public boolean hasMoreElements() {
		return !moves.isEmpty();
	}

	@Override
	public GameState nextElement() {
		List<Ball> oS = balls;
		List<Ball> oB = onBoard;
		Move move = moves.get(0);
		if(move.removeStep) {
			
		}
		moves.remove(0);
		move.ball.position = move.position;
		if(!move.ball.onBoard) {
			oS.remove(move.ball);
			oB.add(move.ball);
		}
		return new GameState(oB, oS);
	}
}
