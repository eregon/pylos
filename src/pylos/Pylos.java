package pylos;

import java.io.File;
import java.util.logging.Logger;

import pylos.controller.Controller;
import pylos.model.Model;
import pylos.view.View;

public class Pylos {
	public static View view;

	// Ugly hack to get root path: this is Java ...
	public static final File rootPath = new File(Pylos.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
	public static final Logger logger = Logger.getLogger(Pylos.class.getName());

	public static void main(String[] args) {
		System.out.println("Welcome to Pylos!");
		logger.config("root path is " + rootPath);

		Config.configureProject();

		Model.initialize();
		view = new View();
		Controller.initialize(view);

		view.show();
	}
}
