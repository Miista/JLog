package net.palmund.logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 
 * 
 * @author S¿ren Palmund
 */
public final class LoggerFactory {
	private static LoggerFactory instance = null;

	public static LoggerFactory getInstance() {
		if (instance == null) {
			instance = new LoggerFactory();
		}
		return instance;
	}

	private final String propertyFile = "/jlogger.properties";
	private final Properties defaultProperties;
	
	private PrintStream printStream;
	private MessageFormatter messageFormatter;
	
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
		LogFilter filter = createLogFilter(properties);

		MessageFormatter formatter = createMessageFormatter(properties);
		PrintStream out = getPrintStream(properties);
		LogMessagePrinter messagePrinter = new LogMessagePrinter(out, formatter);
		return new Logger(klass, properties, filter, messagePrinter);
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
	
	private LogFilter createLogFilter(Properties properties) {
		LogFilter filter;
		String ignoreClasses = properties.getProperty(PropertyKey.IGNORE_CLASSES.getPropertyKeyPath());
		if (ignoreClasses == null) {
			filter = new LogFilter() {
				@Override
				public <T> boolean shouldAllowLoggingForClass(Class<T> klass) {
					return true;
				}
			};
		} else {
			String[] chunks = ignoreClasses.split(",");
			final ArrayList<Class<?>> ignoreClassesList = new ArrayList<Class<?>>();
			for (String string : chunks) {
				try {
					String trimmedClassName = string.trim();
					Class<?> klass = Class.forName(trimmedClassName);
					ignoreClassesList.add(klass);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			filter = new LogFilter() {
				@Override
				public <T> boolean shouldAllowLoggingForClass(Class<T> klass) {
					return !(ignoreClassesList.contains(klass));
				}
			};
		}
		return filter;
	}

	@SuppressWarnings("unchecked")
	private MessageFormatter createMessageFormatter(Properties properties) {
		String formatterClassName = properties.getProperty(PropertyKey.LOG_FORMATTER.getPropertyKeyPath());
		if (this.messageFormatter != null &&
				this.messageFormatter.getClass().getName().equals(formatterClassName)) {
			return this.messageFormatter;
		}
		
		MessageFormatter formatter;
		try {
			Class<MessageFormatter> formatterClass = (Class<MessageFormatter>) Logger.class.getClassLoader().loadClass(formatterClassName);
			formatter = formatterClass.newInstance();
		} catch (Exception e) {
			formatter = new LogFormatter();
		}
		return formatter;
	}
	
	private PrintStream getPrintStream(Properties properties) {
		String printStreamClassName = properties.getProperty(PropertyKey.PRINT_STREAM_CLASS.getPropertyKeyPath());
		if (this.printStream != null &&
				this.printStream.getClass().getName().equals(printStreamClassName)) {
			return this.printStream;
		}
		
		PrintStream printStream;
		try {
			if (printStreamClassName == null) {
				printStream = System.out;
			} else {
				if (printStreamClassName.startsWith("java.lang.System")) {
					String systemPrintStream = printStreamClassName.replace("java.lang.System", "");
					printStream = getSystemPrintStream(systemPrintStream);
				} else {
					@SuppressWarnings("unchecked")
					Class<? extends PrintStream> outKlass = (Class<? extends PrintStream>) Class.forName(printStreamClassName);
					printStream = outKlass.newInstance();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * InstantiationException
			 * IllegalAccessException
			 * ClassNotFoundException
			 * NullPointerException
			 */
			/*
			 * If all fails fall back to System.out
			 */
			printStream = System.out;
		}
		return printStream;
	}
	
	private PrintStream getSystemPrintStream(String streamIdentifier) {
		if (streamIdentifier.equals("err")) {
			return System.err;
		} else {
			return System.out;
		}
	}

	public void setPrintStream(PrintStream printStream) {
		this.printStream = printStream;
	}
	
	public void setMessageFormatter(MessageFormatter messageFormatter) {
		this.messageFormatter = messageFormatter;
	}
}