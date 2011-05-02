package pylos.view.appstate;

import pylos.Pylos;
import pylos.view.View;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.system.Timer;

public class FPSDisplayer extends AbstractAppState {
	View view;
	Timer timer;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		view = Pylos.view;
		timer = view.getTimer();
	}

	@Override
	public void update(float tpf) {
		view.screenController.setFPS((int) timer.getFrameRate());
	}
}
