package net.palmund.logger;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

/**
 * 
 * <ul>
 * 	<li>jlogger.print.class: a class that extends {@link PrintStream} to use instead of System.*</li>
 * 	<li>jlogger.print.doPrint: whether logged messages should be printed</li>
 * 	<li>jlogger.print.useSystem = [<i>out</i> | <i>err</i> ]</li>
 * 	<li>jlogger.formatter.time: the format to apply to the date and time. See {@link SimpleDateFormat}</li>
 * 	<li>jlogger.formatter.message: the format to apply to the text when printing the logged message.
 * 		When setting the format for printing log messages, the following variables are available:
 * 		<ul>
 * 			<li>$date: the time and date the message was logged</li>
 * 			<li>$class_name: the name of the class that logged the message</li>
 * 			<li>$class_full: the full path of the class that logged the message</li>
 * 			<li>$message: the message that was logged</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * 
 * @author S¿ren Palmund
 */
public final class Logger {
	private static final String PROPERTY_FILE = "/jlogger.properties";
	private static final Map<Class<?>, Logger> loggerMap = new HashMap<Class<?>, Logger>();
	private static final List<LogMessage> messageHistory = new Vector<LogMessage>();
	
	private static Properties properties;
	
	public static <T> Logger getLogger(Class<T> klass) {
		if (!loggerMap.containsKey(klass)) {
			Logger logger = new Logger(klass, getProperties());
			loggerMap.put(klass, logger);
		}
		return loggerMap.get(klass);
	}
	
	private static Properties getProperties() {
		if (properties == null) {
			@SuppressWarnings("serial")
			Map<String, String> defaults = new HashMap<String, String>() {{
				put(PropertyKey.VERBOSE.getPropertyKeyPath(), "true");
				put(PropertyKey.TIME_FORMAT.getPropertyKeyPath(), "d/M/yyyy kk:mm:ss.SSS");
				put(PropertyKey.MESSAGE_FORMAT.getPropertyKeyPath(), "[$date] $message");
				put(PropertyKey.PRINT_STREAM_CLASS.getPropertyKeyPath(), "java.lang.System.out");
			}};			
			PropertyManager manager = PropertyManager.getInstance(PROPERTY_FILE, defaults);
			properties = manager.getProperties();
			String redirectPropertyFile = properties.getProperty(PropertyKey.REDIRECT.getPropertyKeyPath());
			if (redirectPropertyFile != null) {
				manager = PropertyManager.getInstance(redirectPropertyFile, defaults);
				properties = manager.getProperties();
			}
		}
		return properties;
	}
	
	public static <T> void JLog(Class<T> klass, Object object) {
		JLog(klass, object.toString());
	}
	
	public static <T> void JLog(Class<T> klass, String format, Object ... args) {
		String formattedMessage = String.format(format, args);
		JLog(klass, formattedMessage);
	}
	
	public static <T> void JLog(Class<T> klass, String message) {
		Logger logger = getLogger(klass);
		logger.log(message);
	}
	
	
	public static Collection<LogMessage> getAllLoggedMessages() {
		return messageHistory;
	}
	
	private final LogMessagePrinter messagePrinter;
	private final LogFilter filter;
	private final Class<?> klass;
	private final boolean printLogMessages;
	
	private Logger(Class<?> klass, Properties properties) {
		this.printLogMessages = Boolean.parseBoolean(properties.getProperty(PropertyKey.VERBOSE.getPropertyKeyPath()));
		this.klass = klass;
		this.filter = createLogFilter(properties);
		MessageFormatter formatter = new LogFormatter(	properties.getProperty(PropertyKey.TIME_FORMAT.getPropertyKeyPath()),
														properties.getProperty(PropertyKey.MESSAGE_FORMAT.getPropertyKeyPath())
													);
		
		PrintStream out = null;
		try {
			String printStreamClassName = properties.getProperty(PropertyKey.PRINT_STREAM_CLASS.getPropertyKeyPath());
			if (printStreamClassName == null) {
				out = System.out;
			} else {
				if (printStreamClassName.startsWith("java.lang.System")) {
					String systemPrintStream = printStreamClassName.replace("java.lang.System", "");
					out = getSystemPrintStream(systemPrintStream);
					System.out.println("here2");
				} else {
					@SuppressWarnings("unchecked")
					Class<? extends PrintStream> outKlass = (Class<? extends PrintStream>) Class.forName(printStreamClassName);
					out = outKlass.newInstance();
					System.out.println("here1");
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
			 * If all fails fall back to whatever default System.* PrintStream is set.
			 */
			out = System.out;
		} finally {
			this.messagePrinter = new LogMessagePrinter(out, formatter);
		}
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

	private PrintStream getSystemPrintStream(String streamIdentifier) {
		if (streamIdentifier.equals("err")) {
			return System.err;
		} else {
			return System.out;
		}
	}

	void log(String formattedMessage) {
		LogMessage message = new LogMessage(klass, formattedMessage); //.createLoggedMessage(klass, formattedMessage);
		addMessageToHistory(message);
		printLoggedMessage(message);
	}

	private void addMessageToHistory(LogMessage message) {
		messageHistory.add(message);
	}
	
	private void printLoggedMessage(LogMessage message) {
		if (printLogMessages && filter.shouldAllowLoggingForClass(message.getLoggingClass())) {
			messagePrinter.printMessage(message);
		}
	}
	
	public LogMessagePrinter getMessagePrinter() {
		return messagePrinter;
	}
}