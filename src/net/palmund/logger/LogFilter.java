package net.palmund.logger;

public interface LogFilter {
	<T> boolean shouldAllowLoggingForClass(Class<T> klass);
}