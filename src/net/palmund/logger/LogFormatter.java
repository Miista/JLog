/*
 * Copyright 2013 (c) S�ren Palmund
 * 
 * Licensed under the License described in LICENSE (the "License"); you may not
 * use this file except in compliance with the License.
 */

package net.palmund.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

final class LogFormatter implements MessageFormatter {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy kk:mm:ss.SSS");
	private final String messageFormat = "[$date] <$class_name> $message";
	
	private final Calendar cal = Calendar.getInstance();
	
	@Override
	public String format(LogMessage message) {
		return replaceFields(messageFormat, message);
	}
	
	private String replaceFields(String formattedMessage, LogMessage message) {
		cal.setTimeInMillis(message.getTimestamp());
		Date date = cal.getTime();
		String formattedDate = dateFormat.format(date);
		formattedMessage = formattedMessage.replace("$date", formattedDate);
		formattedMessage = formattedMessage.replace("$class_full", message.getLoggingClass().getName());
		formattedMessage = formattedMessage.replace("$class_name", message.getLoggingClass().getSimpleName());
		formattedMessage = formattedMessage.replace("$message", message.getMessage());
		return formattedMessage;
	}
}