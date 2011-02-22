package pylos;

import java.io.File;

import pylos.controller.Controller;
import pylos.model.Board;
import pylos.view.View;

public class Pylos {
	public static Board board;
	public static View view;
	public static Controller controller;

	public static File rootPath = new File(".");

	public static void main(String[] args) {
		System.out.println("Welcome to Pylos!");

		Config.configureProject();

		board = new Board();
		view = new View();
		controller = new Controller(board, view);

		view.show();
	}
}
