package net.palmund.logger.test.acceptance;

import static net.palmund.logger.Logger.JLog;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class LoggerTest_2 {
	@Test
	public void test() {
		JLog(LoggerTest_2.class, "Hej med dig");
	}
}