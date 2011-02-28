package pylos;

import java.io.File;

import pylos.controller.Controller;
import pylos.model.Model;
import pylos.view.View;

public class Pylos {
	public static Model model;
	public static View view;
	public static Controller controller;

	public static File rootPath = new File(".");

	public static void main(String[] args) {
		System.out.println("Welcome to Pylos!");

		Config.configureProject();

		model = new Model();
		view = new View();
		controller = new Controller(model, view);

		view.show();
	}
}
