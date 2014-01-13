package net.palmund.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

final class LoggerProperties extends Properties {
	private static final long serialVersionUID = 3645628006537968494L;
	
	private static final String PROPERTY_FILE = "/jlogger.properties";
	@SuppressWarnings("serial")
	private static final Properties DEFAULTS = mapToProperties(new HashMap<String, String>() {
		{
			put(PropertyKey.VERBOSE.getPropertyKeyPath(), "true");
			put(PropertyKey.TIME_FORMAT.getPropertyKeyPath(), "d/M/yyyy kk:mm:ss.SSS");
			put(PropertyKey.MESSAGE_FORMAT.getPropertyKeyPath(), "[$date] $message");
			put(PropertyKey.PRINT_STREAM_CLASS.getPropertyKeyPath(), "java.lang.System.out");
			put(PropertyKey.LOG_FORMATTER.getPropertyKeyPath(), "net.palmund.logger.LogFormatter");
		}
	});

	private static LoggerProperties instance = null;

	public static LoggerProperties getInstance() {
		if (instance == null) {
			instance = new LoggerProperties(DEFAULTS);
		}
		return instance;
	}

	private LoggerProperties(Properties defaults) {
		super(defaults);
		InputStream in = getClass().getResourceAsStream(PROPERTY_FILE);
		try {
			load(in);
			in.close();
		}
		catch (IOException e) {
		}
		catch (NullPointerException e) {
		}
	}

	private static Properties mapToProperties(Map<? extends Object, ? extends Object> map) {
		Properties properties = new Properties();
		for (Map.Entry<? extends Object, ? extends Object> entry : map.entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}
		return properties;
	}
}