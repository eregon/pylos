package pylos.controller;

import pylos.Pylos;
import pylos.model.Model;
import pylos.model.Position;
import pylos.view.View;

public class Controller {
	protected Model model;
	protected View view;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		view.controller = this;
	}

	public void updateView() {
		view.board.drawBalls();
		view.updatePositionBalls();
	}

	public void finishTurn() {
		if (model.isWinner()) {
			updateView();
			Pylos.logger.info(Model.currentPlayer + " won");
		} else {
			nextTurn();
		}
	}

	public void placePlayerBall(Position position) {
		Model.currentPlayer.putBallOnBoard(position);
		finishTurn();
	}

	private void nextTurn() {
		Model.currentPlayer = (Model.currentPlayer == Model.player1) ? Model.player2 : Model.player1;
		updateView();
	}

	public void risePlayerBall(Position position) {
		// TODO
	}
}
