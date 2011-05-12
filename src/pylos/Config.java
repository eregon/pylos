package pylos;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Config {
	public static int RMI_PORT = 1723; // Registry.REGISTRY_PORT
	public static boolean LOW_GRAPHICS = false; // Set this to true if it is too slow
	public static final long CREATE_RMI_REGISTRY_TIMEOUT = 5000; // ms
	public static final boolean CAN_MOVE_OTHER = false; // if one player can play for the other
	public static final int[] RESOLUTION = { 1280, 750 }; // 1280, 750

	public static void configureProject() {
		configureLogger();
	}

	public static void configureLogger() {
		// Hide everything under Warning for default handlers
		for (Handler handler : Logger.getLogger("").getHandlers()) {
			handler.setLevel(Level.WARNING);
		}

		createFileLogger(Pylos.logger);
		createFileLogger(Logger.getLogger("com.jme3"));
		createFileLogger(Logger.getLogger("de.lessvoid.nifty"));
	}

	public static void createFileLogger(Logger logger) {
		try {
			FileHandler fh = new FileHandler("log/" + logger.getName() + ".log");
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
		} catch (Exception e) {
			ConsoleHandler ch = new ConsoleHandler();
			logger.addHandler(ch);
			e.printStackTrace();
		}
	}
}
