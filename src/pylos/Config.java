package pylos;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
	public static boolean LOW_GRAPHICS = false; // Set this to true if it is too slow

	public static void configureProject() {
		Logger.getLogger("").setLevel(Level.WARNING); // Hide everything under Warning by default
		Logger.getLogger("pylos").setLevel(Level.CONFIG);
	}
}
