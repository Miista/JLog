/*
 * Copyright 2013 (c) S¿ren Palmund
 * 
 * Licensed under the License described in LICENSE (the "License"); you may not
 * use this file except in compliance with the License.
 */

package net.palmund.logger;

interface LogFilter {
	<T> boolean shouldAllowLoggingForClass(Class<T> klass);
}