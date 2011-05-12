package pylos.controller.screen;

import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;

public abstract class ScreenControllerUtils {
	public static Element find(Element element, String path) {
		for (String name : path.split("/")) {
			element = element.findElementByName(name);
		}
		return element;
	}

	public static Element find(Screen screen, String path) {
		return find(screen.getRootElement(), path);
	}
}
