package net.palmund.logger;

interface LogFilter {
	<T> boolean shouldAllowLoggingForClass(Class<T> klass);
}