package pylos.view;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

public class View extends SimpleApplication {
	public View() {
		super();
		showSettings = false;
		settings = new AppSettings(true);
		settings.setResolution(800, 600);
		settings.setTitle("Pylos");
	}

	@Override
	public void simpleInitApp() {

	}

	public void show() {
		start();
	}
}
