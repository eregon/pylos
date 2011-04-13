package pylos;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
	public static boolean LOW_GRAPHICS = false;

	public static void configureProject() {
		Logger.getLogger("com.jme3").setLevel(Level.WARNING);
	}
}
