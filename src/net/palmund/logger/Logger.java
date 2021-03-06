/*
 * Copyright 2013 (c) S�ren Palmund
 * 
 * Licensed under the License described in LICENSE (the "License"); you may not
 * use this file except in compliance with the License.
 */

package net.palmund.logger;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
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
 * 	<li>jlogger.print.verbose: whether messages should be printed</li>
 * 	<li>jlogger.formatter.class: a class that extends {@link MessageFormatter} to use for formatting log messages</li>
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
 * @author S�ren Palmund
 */
public final class Logger {
	private static final Map<Class<?>, Logger> loggerMap = new HashMap<Class<?>, Logger>();
	private static final List<LogMessage> messageHistory = new Vector<LogMessage>();
	private static final LoggerFactory factory = LoggerFactory.getInstance();
	
	private static <T> Logger getLogger(Class<T> klass) {
		if (!loggerMap.containsKey(klass)) {
			Logger logger = factory.createLogger(klass);
			loggerMap.put(klass, logger);
		}
		return loggerMap.get(klass);
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
	
	public static Collection<LogMessage> getMessageHistory() {
		return messageHistory;
	}
	
	private final LogMessagePrinter messagePrinter;
	private final LogFilter filter;
	private final Class<?> klass;
	private final boolean printLogMessages;
	
	Logger(Class<?> klass, Properties properties, LogFilter filter, LogMessagePrinter messagePrinter) {
		this.printLogMessages = Boolean.parseBoolean(properties.getProperty(PropertyKey.VERBOSE.getPropertyKeyPath()));
		this.klass = klass;
		this.filter = filter; //createLogFilter(properties);
		this.messagePrinter = messagePrinter;
//		MessageFormatter formatter = createMessageFormatter(properties);
//		PrintStream out = getPrintStream(properties);
//		
//		this.messagePrinter = new LogMessagePrinter(out, formatter);
	}
	
//	private LogFilter createLogFilter(Properties properties) {
//		LogFilter filter;
//		String ignoreClasses = properties.getProperty(PropertyKey.IGNORE_CLASSES.getPropertyKeyPath());
//		if (ignoreClasses == null) {
//			filter = new LogFilter() {
//				@Override
//				public <T> boolean shouldAllowLoggingForClass(Class<T> klass) {
//					return true;
//				}
//			};
//		} else {
//			String[] chunks = ignoreClasses.split(",");
//			final ArrayList<Class<?>> ignoreClassesList = new ArrayList<Class<?>>();
//			for (String string : chunks) {
//				try {
//					String trimmedClassName = string.trim();
//					Class<?> klass = Class.forName(trimmedClassName);
//					ignoreClassesList.add(klass);
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//			filter = new LogFilter() {
//				@Override
//				public <T> boolean shouldAllowLoggingForClass(Class<T> klass) {
//					return !(ignoreClassesList.contains(klass));
//				}
//			};
//		}
//		return filter;
//	}
//
//	@SuppressWarnings("unchecked")
//	private MessageFormatter createMessageFormatter(Properties properties) {
//		String formatterClassName = properties.getProperty(PropertyKey.LOG_FORMATTER.getPropertyKeyPath());
//		MessageFormatter formatter;
//		try {
//			Class<MessageFormatter> formatterClass = (Class<MessageFormatter>) Logger.class.getClassLoader().loadClass(formatterClassName);
//			formatter = formatterClass.newInstance();
//		} catch (Exception e) {
//			formatter = new LogFormatter();
//		}
//		return formatter;
//	}
//	
//	private PrintStream getPrintStream(Properties properties) {
//		String printStreamClassName = properties.getProperty(PropertyKey.PRINT_STREAM_CLASS.getPropertyKeyPath());
//		PrintStream printStream;
//		try {
//			if (printStreamClassName == null) {
//				printStream = System.out;
//			} else {
//				if (printStreamClassName.startsWith("java.lang.System")) {
//					String systemPrintStream = printStreamClassName.replace("java.lang.System", "");
//					printStream = getSystemPrintStream(systemPrintStream);
//				} else {
//					@SuppressWarnings("unchecked")
//					Class<? extends PrintStream> outKlass = (Class<? extends PrintStream>) Class.forName(printStreamClassName);
//					printStream = outKlass.newInstance();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			/*
//			 * InstantiationException
//			 * IllegalAccessException
//			 * ClassNotFoundException
//			 * NullPointerException
//			 */
//			/*
//			 * If all fails fall back to System.out
//			 */
//			printStream = System.out;
//		}
//		return printStream;
//	}
//	
//	private PrintStream getSystemPrintStream(String streamIdentifier) {
//		if (streamIdentifier.equals("err")) {
//			return System.err;
//		} else {
//			return System.out;
//		}
//	}

	private void log(String formattedMessage) {
		LogMessage message = new LogMessage(klass, formattedMessage);
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
	
	LogMessagePrinter getMessagePrinter() {
		return messagePrinter;
	}
}