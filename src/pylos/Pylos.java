package pylos;

import java.io.File;
import java.util.logging.Logger;

import pylos.controller.Controller;
import pylos.model.Model;
import pylos.model.Position;
import pylos.view.View;

public class Pylos {
	public static View view;

	// Ugly hack to get root path: this is Java ...
	public static final String rootPath = new File(Pylos.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
	public static final String assetsPath = rootPath + "/assets";
	public static final Logger logger = Logger.getLogger(Pylos.class.getName());

	/**
	 * Initialize the environment.
	 * This should be called first.
	 */
	public static void initialize() {
		Position.createPositions();
	}

	/**
	 * Pylos' Main: Start the game
	 */
	public static void main(String[] args) {
		initialize();
		System.out.println("Welcome to Pylos!");
		logger.config("root path is " + rootPath);

		Config.configureProject();

		Model.initialize();
		view = new View();
		Controller.initialize(view);

		view.show();
	}
}
