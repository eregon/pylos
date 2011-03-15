package pylos.controller;

import pylos.model.Model;
import pylos.model.PositionBall;
import pylos.view.View;

public class Controller {
	protected Model model;
	protected View view;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		view.controller = this;
	}

	public void initTurn() {
		view.placePositionBalls();
	}

	public void placePlayerBall(PositionBall positionBall) {
		Model.currentPlayer.putBallOnBoard(positionBall);
		nextTurn();
	}

	private void nextTurn() {
		Model.currentPlayer = (Model.currentPlayer == Model.player1) ? Model.player2 : Model.player1;
		view.board.drawBalls();
	}
}
