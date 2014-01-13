package net.palmund.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 
 * @author S¿ren Palmund
 */
final class LoggerFactory {
	private static LoggerFactory instance = null;

	public static LoggerFactory getInstance() {
		if (instance == null) {
			instance = new LoggerFactory();
		}
		return instance;
	}

	private final String propertyFile = "/jlogger.properties";
	private final Properties defaultProperties;
	
	private LoggerFactory() {
		this.defaultProperties = new Properties();
		defaultProperties.put(PropertyKey.VERBOSE.getPropertyKeyPath(), "true");
		defaultProperties.put(PropertyKey.TIME_FORMAT.getPropertyKeyPath(), "d/M/yyyy kk:mm:ss.SSS");
		defaultProperties.put(PropertyKey.MESSAGE_FORMAT.getPropertyKeyPath(), "[$date] $message");
		defaultProperties.put(PropertyKey.PRINT_STREAM_CLASS.getPropertyKeyPath(), "java.lang.System.out");
		defaultProperties.put(PropertyKey.LOG_FORMATTER.getPropertyKeyPath(), "net.palmund.logger.LogFormatter");
	}

	public <T> Logger createLogger(Class<T> klass) {
		Properties properties = loadProperties();
		return new Logger(klass, properties);
	}
	
	private Properties loadProperties() {
		Properties properties = new Properties(defaultProperties);
		InputStream in = getClass().getResourceAsStream(this.propertyFile);
		try {
			properties.load(in);
			in.close();
		}
		catch (IOException e) {
		}
		catch (NullPointerException e) {
		}
		return properties;
	}
}