package pylos.controller;

import pylos.Pylos;
import pylos.ai.AlphaBeta;
import pylos.exception.PylosError;
import pylos.model.Ball;
import pylos.model.Model;
import pylos.model.Player;
import pylos.model.Position;
import pylos.view.View;

/**
 * The Pylos Controller.
 * Implements the singleton pattern by having everything static and no instance (the object is the class).
 */
public abstract class Controller {
	static View view;

	static int ballRemoved = 0;

	public static void initialize(View view) {
		Controller.view = view;
		initTurn();
	}

	public static void updateView() {
		if (view.board != null) {
			view.updatePositionBalls();
			view.board.drawBalls();
		}
	}

	public static void initTurn() {
		Model.currentPlayer.resetAction();
		ballRemoved = 0;
		if (Model.currentPlayer.allBallsOnBoard()) {
			nextTurn();
		} else {
			if (Model.currentPlayer.Ai()) {
				AlphaBeta.AI();
				finishTurn();
			}
			setPlayerStatus("Place or mount a ball (right click)");
		}
	}

	public static void finishTurn() {
		if (Model.isWinner()) {
			updateView();
			Player winner = Model.currentPlayer, loser = winner.other();
			Pylos.logger.info(winner + " won");
			if (winner.local() != loser.local()) {
				if (winner.local())
					view.setStatus("You have won ! (Player " + winner.toByte() + ")");
				else
					view.setStatus("You have lost ! (Player " + loser.toByte() + ")");
			} else {
				view.setStatus("Player " + winner.toByte() + " won !");
			}
		} else {
			nextTurn();
		}
	}

	private static void nextTurn() {
		Model.currentPlayer = Model.otherPlayer();
		initTurn();
		updateView();
	}

	private static void removeBalls() {
		Model.currentPlayer.removeBalls();
		setPlayerStatus("Remove 1 or 2 balls (remove only one by a right click)");
	}

	private static void setPlayerStatus(String status) {
		if (Model.currentPlayer.canMove()) {
			view.setStatus(status);
		} else {
			view.setStatus("Waiting opponent");
		}
	}

	public static void placeAiBall(Position position, Position[] removables, boolean mount) {
		Model.currentPlayer.putBallOnBoard(position);
		updateView();
		if (!mount)
			System.out.println("AI place a ball at : " + position);

		if (Model.currentPlayer.anyLineOrSquare(position) && !Model.otherPlayer().allBallsOnBoard() || mount && !Model.otherPlayer().allBallsOnBoard()) {
			for (Position removable : removables) {
				if (mount) {
					System.out.println("AI mout a ball from : " + removable + " to :" + position);
					mount = false;
				} else
					System.out.println("AI removes a ball at : " + removable);
				removeAiBall(removable);
			}
		}

	}

	private static void removeAiBall(Position position) {
		Model.currentPlayer.removeBall(Model.board.ballAt(position));
		updateView();
	}

	public static void placePlayerBall(Position position) {
		Model.currentPlayer.putBallOnBoard(position);
		updateView();

		if (Model.currentPlayer.anyLineOrSquare(position) && !Model.otherPlayer().allBallsOnBoard())
			removeBalls();
		else
			finishTurn();
	}

	public static void mountPlayerBall(Ball ball) {
		if (Model.getPositionsToMount(ball).isEmpty()) {
			throw new PylosError("Can not mount ball, there is no place to mount it.");
		} else {
			Model.currentPlayer.mountBall(ball);
			view.updatePositionsToMount(ball);
			updateView();
			setPlayerStatus("Choose where to place the ball");
		}
	}

	public static void removePlayerBall(Ball ball, boolean lastRemoved) {
		Model.currentPlayer.removeBall(ball);
		updateView();

		if (Model.currentPlayer.getRemovableBalls().isEmpty())
			finishTurn();

		ballRemoved += 1;
		if (lastRemoved || ballRemoved == 2) // finished removing
			finishTurn();
	}
}
