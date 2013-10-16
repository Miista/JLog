package net.palmund.logger.test;

import net.palmund.logger.LogMessage;
import net.palmund.logger.MessageFormatter;


public class TestFormatter implements MessageFormatter {
	@Override
	public String format(LogMessage message) {
		return message.getMessage();
	}
}