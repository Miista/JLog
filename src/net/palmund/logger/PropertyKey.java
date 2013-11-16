/*
 * Copyright 2013 (c) S¿ren Palmund
 * 
 * Licensed under the License described in LICENSE (the "License"); you may not
 * use this file except in compliance with the License.
 */

package net.palmund.logger;

enum PropertyKey {
	TIME_FORMAT("jlogger.formatter.time"),
	MESSAGE_FORMAT("jlogger.formatter.message"),
	PRINT_STREAM_CLASS("jlogger.print.class"),
	VERBOSE("jlogger.print.verbose"),
	IGNORE_CLASSES("jlogger.filter.ignoreClasses"),
	LOG_FORMATTER("jlogger.formatter.class"),
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