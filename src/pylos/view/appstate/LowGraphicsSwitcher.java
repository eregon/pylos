package pylos.view.appstate;

import pylos.Config;
import pylos.Pylos;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.system.Timer;

public class LowGraphicsSwitcher extends AbstractAppState {
	static final int MinFPSForLowGraphics = 30;

	Timer timer;
	private int frame = 0;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		timer = Pylos.view.getTimer();
	}

	@Override
	public void update(float tpf) {
		frame++;
		if (!Config.LOW_GRAPHICS && (1 / tpf) < MinFPSForLowGraphics && timer.getTimeInSeconds() >= 3) {
			Config.LOW_GRAPHICS = true;
			Pylos.view.lights.switchLightMode(Config.LOW_GRAPHICS);
			Pylos.logger.info("Switching to low graphics because fps was " + (1 / tpf) + " at frame " + frame);
		}
	}
}
