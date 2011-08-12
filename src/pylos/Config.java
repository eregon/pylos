package pylos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Config {
	public static int[] RESOLUTION = { 800, 640 };
	public static int AI_DEPTH = 4;
	public static boolean LOW_GRAPHICS = false;
	public static int RMI_PORT = 1723;
	public static long CREATE_RMI_REGISTRY_TIMEOUT = 5000;
	public static boolean CAN_MOVE_OTHER = false;
	public static boolean FIRE = false;

	static final File logDir = new File(Pylos.rootPath + "/log");
	static final File defaultPropertiesFile = new File(Pylos.rootPath + "/assets/Configuration/config.properties");
	static final File propertiesFile = new File(Pylos.rootPath + "/config.properties");

	public static void configureProject() {
		Properties properties = new Properties();

		if (!propertiesFile.exists()) {
			FileChannel input = null, output = null;
			try {
				input = new FileInputStream(defaultPropertiesFile).getChannel();
				output = new FileOutputStream(propertiesFile).getChannel();
				input.transferTo(0, input.size(), output);
			} catch (Exception e) {
				System.err.println("Could not copy default configuration file: " + e);
			} finally {
				try {
					if (input != null)
						input.close();
					if (output != null)
						output.close();
				} catch (IOException e) {
				}
			}
		}

		try {
			properties.load(new FileInputStream(propertiesFile));
			RESOLUTION[0] = Integer.valueOf(properties.getProperty("screen.width", Integer.toString(RESOLUTION[0])));
			RESOLUTION[1] = Integer.valueOf(properties.getProperty("screen.height", Integer.toString(RESOLUTION[1])));
			AI_DEPTH = Integer.valueOf(properties.getProperty("ai.depth", Integer.toString(AI_DEPTH)));
			LOW_GRAPHICS = Boolean.valueOf(properties.getProperty("graphics.low", Boolean.toString(LOW_GRAPHICS)));
			RMI_PORT = Integer.valueOf(properties.getProperty("rmi.port", Integer.toString(RMI_PORT)));
			CREATE_RMI_REGISTRY_TIMEOUT = Integer.valueOf(properties.getProperty("rmi.timeout", Long.toString(CREATE_RMI_REGISTRY_TIMEOUT)));
			CAN_MOVE_OTHER = Boolean.valueOf(properties.getProperty("game.canMoveOther", Boolean.toString(CAN_MOVE_OTHER)));
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
