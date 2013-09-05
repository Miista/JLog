package net.palmund.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogFormatter { //implements LogFormatter_ {
	private final SimpleDateFormat dateFormat;
	
	private Calendar cal = Calendar.getInstance();
	private String messageFormat;
	
	public LogFormatter(String dateFormat, String messageFormat) {
		this.dateFormat = new SimpleDateFormat(dateFormat);
		this.messageFormat = messageFormat;
	}
	
//	@Override
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