package net.palmund.logger;

public final class LogMessage {
	private Class<?> loggingClass;
	private String message;
	private long timestamp;

	protected LogMessage(Class<?> loggingClass, String message) {
		this(loggingClass, message, System.currentTimeMillis());
	}
	
	public LogMessage(Class<?> loggingClass, String message, long timestamp) {
		this.loggingClass = loggingClass;
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public Class<?> getLoggingClass() {
		return loggingClass;
	}
}