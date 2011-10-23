package pylos.controller.screen;

import pylos.Pylos;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MainScreenController implements ScreenController {
	Nifty nifty;
	Screen screen;
	TextRenderer statusText;
	TextRenderer fpsText;

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
		Pylos.view.registerScreenController(this);
	}

	@Override
	public void onEndScreen() {
	}

	@Override
	public void onStartScreen() {
		statusText = ScreenControllerUtils.find(screen, "layer/panel/status").getRenderer(TextRenderer.class);
		fpsText = ScreenControllerUtils.find(screen, "layer/panel/fps").getRenderer(TextRenderer.class);
	}

	public void setStatus(String status) {
		statusText.setText(status);
	}

	public void setFPS(int fps) {
		fpsText.setText("FPS: " + fps);
	}
}
