package pylos;

import java.io.File;
import java.util.logging.Logger;

import pylos.controller.Controller;
import pylos.model.Model;
import pylos.view.View;

public class Pylos {
	public static Model model;
	public static View view;
	public static Controller controller;

	public static final File rootPath = new File(".");
	public static final Logger logger = Logger.getLogger(Pylos.class.getName());

	public static void main(String[] args) {
		System.out.println("Welcome to Pylos!");

		Config.configureProject();

		model = new Model();
		view = new View(model);
		controller = new Controller(model, view);

		view.show();
	}
}
