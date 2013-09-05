package net.palmund.logger;

enum PropertyKey {
	TIME_FORMAT("jlogger.formatter.time"),
	MESSAGE_FORMAT("jlogger.formatter.message"),
	PRINT_USE_SYSTEM("jlogger.print.useSystem"),
	PRINT_STREAM_CLASS("jlogger.print.class"),
	VERBOSE("jlogger.print.doPrint"),
	IGNORE_CLASSES("jlogger.filter.ignoreClasses"),
	REDIRECT("jlogger.redirect"),
	;
	
	private String propertyKey;

	private PropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getPropertyKeyPath() {
		return propertyKey;
	}
	
	@Override
	public String toString() {
		return propertyKey;
	}
}