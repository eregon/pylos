package pylos.view.appstate;

import pylos.Pylos;
import pylos.view.View;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

public class KeysManager extends AbstractAppState implements ActionListener {
	static final String Quit = "Quit";

	View view;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		view = Pylos.view;

		InputManager input = view.getInputManager();
		input.addMapping(Quit, new KeyTrigger(KeyInput.KEY_Q));
		input.addListener(this, Quit);
	}

	public void onAction(String action, boolean pressed, float tpf) {
		if (action == Quit)
			view.stop();
	}
}
