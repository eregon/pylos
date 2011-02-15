package pylos.controller;

import pylos.model.Board;
import pylos.view.View;

public class Controller {
	protected Board board;
	protected View  view;

	public Controller(Board board, View view) {
		this.board = board;
		this.view = view;
	}

}
