package pylos.controller.screen;

import pylos.Pylos;
import pylos.view.View;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class StartScreenController implements ScreenController {
	Nifty nifty;
	Screen screen;
	View view;

	Element multiButtonsPanel;

	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
		view = Pylos.view;
		multiButtonsPanel = ScreenControllerUtils.find(screen, "layer/multi/multiButtons");
		multiButtonsPanel.hideWithoutEffect();
	}

	public void onEndScreen() {
	}

	public void onStartScreen() {
	}

	public void singlePlayer() {
		System.out.println("Needs AI implementation ;)");
	}

	public void multiPlayer() {
		multiButtonsPanel.show();
	}

	public void startLocalGame() {
		view.initGame();
	}

	public void startNetworkGame() {
		Pylos.network.createConnections();
		view.initGame();
	}
}
