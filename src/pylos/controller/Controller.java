package pylos.controller;

import pylos.model.Model;
import pylos.view.View;

public class Controller {
	protected Model model;
	protected View  view;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}

}
