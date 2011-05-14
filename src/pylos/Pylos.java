package pylos;

import java.io.File;
import java.util.logging.Logger;

import pylos.controller.Controller;
import pylos.model.Model;
import pylos.model.Position;
import pylos.network.Network;
import pylos.view.View;

public class Pylos {
	public static View view;
	public static Network network;

	// Ugly hack to get root path: this is Java ...
	public static final String rootPath = new File(Pylos.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
	public static final String assetsPath = rootPath + "/assets";
	public static final Logger logger = Logger.getLogger("pylos");
	public static final Logger AIlogger = Logger.getLogger("pylos.ai");

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

		network = new Network();
	}

	public static void stop() {
		view.stop();
		network.stop();
	}
}
