package net.palmund.logger.test;

import java.util.ArrayList;

import net.palmund.logger.LogMessage;
import net.palmund.logger.MessageFormatter;


public class TestFormatter implements MessageFormatter {
	private ArrayList<String> latest = new ArrayList<String>();
	
	public String getLatest() {
		return latest.get(latest.size() - 1);
	}
	
	@Override
	public String format(LogMessage message) {
		latest.add(message.getMessage());
		return message.getMessage();
	}
}