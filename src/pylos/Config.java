package pylos;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
	public static boolean LOW_GRAPHICS = false; // Set this to true if it is too slow

	public static void configureProject() {
		Logger.getLogger("com.jme3").setLevel(Level.WARNING);
		Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
	}
}
