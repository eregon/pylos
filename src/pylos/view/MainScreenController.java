package pylos.view;

import pylos.Pylos;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MainScreenController implements ScreenController {
	Nifty nifty;
	Screen screen;
	TextRenderer statusText;
	TextRenderer fpsText;

	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
		Pylos.view.registerScreenController(this);
	}

	public void onEndScreen() {
	}

	public void onStartScreen() {
		Element bottomPanel = screen.findElementByName("layer").findElementByName("panel");
		statusText = bottomPanel.findElementByName("status").getRenderer(TextRenderer.class);
		fpsText = bottomPanel.findElementByName("fps").getRenderer(TextRenderer.class);
	}

	public void setStatus(String status) {
		statusText.changeText(status);
	}

	public void setFPS(int fps) {
		fpsText.changeText("FPS: " + fps);
	}
}
