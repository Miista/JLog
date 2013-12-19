package net.palmund.logger.test.acceptance;

import java.util.Collection;

import net.palmund.logger.LogMessage;
import net.palmund.logger.Logger;

import org.junit.Test;

public class LoggerTest_4 {
	@Test
	public void test() {
		Collection<LogMessage> m = Logger.getAllLoggedMessages();
		for (LogMessage logMessage : m) {
			System.out.println(logMessage.getMessage());
		}
	}
}