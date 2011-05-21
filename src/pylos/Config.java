package pylos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Config {
	public static int[] RESOLUTION = new int[2];
	public static int AI_DEPTH;
	public static boolean LOW_GRAPHICS;
	public static int RMI_PORT;
	public static long CREATE_RMI_REGISTRY_TIMEOUT;
	public static boolean CAN_MOVE_OTHER;
	public static boolean FIRE;

	static final File logDir = new File(Pylos.rootPath + "/log");
	static final File propertiesFile = new File(Pylos.rootPath + "/config.properties");

	public static void configureProject() {
		Properties properties = new Properties();

		try {
			propertiesFile.createNewFile(); // Ensure to have the properties file
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			properties.load(new FileInputStream(propertiesFile));
			RESOLUTION[0] = Integer.valueOf(properties.getProperty("screen.width", "800"));
			RESOLUTION[1] = Integer.valueOf(properties.getProperty("screen.height", "640"));
			AI_DEPTH = Integer.valueOf(properties.getProperty("ai.depth", "4"));
			LOW_GRAPHICS = Integer.valueOf(properties.getProperty("graphics.low", "0")) == 1;
			RMI_PORT = Integer.valueOf(properties.getProperty("rmi.port", "1723"));
			CREATE_RMI_REGISTRY_TIMEOUT = Integer.valueOf(properties.getProperty("rmi.timeout", "5000"));
			CAN_MOVE_OTHER = Integer.valueOf(properties.getProperty("game.canMoveOther", "0")) == 1;
			// Not quite ready yet
			FIRE = false; // Integer.valueOf(properties.getProperty("extra.fire", "0")) == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		configureLogger();
	}

	public static void configureLogger() {
		// Hide everything under Warning for default handlers
		for (Handler handler : Logger.getLogger("").getHandlers()) {
			handler.setLevel(Level.WARNING);
		}

		if (!logDir.exists()) {
			try {
				logDir.mkdir();
			} catch (Exception e) {
				System.err.println("Could not create log dir");
			}
		}

		createFileLogger(Pylos.logger);
		createFileLogger(Pylos.AIlogger);
		createFileLogger(Logger.getLogger("com.jme3"));
		Logger.getLogger("com.jme3").setLevel(Level.WARNING);
		createFileLogger(Logger.getLogger("de.lessvoid.nifty"));
	}

	public static void createFileLogger(Logger logger) {
		try {
			FileHandler fh = new FileHandler(logDir + "/" + logger.getName() + ".log");
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
		} catch (Exception e) {
			ConsoleHandler ch = new ConsoleHandler();
			logger.addHandler(ch);
			e.printStackTrace();
		}
	}
}
