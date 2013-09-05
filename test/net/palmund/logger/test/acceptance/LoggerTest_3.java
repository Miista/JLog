package net.palmund.logger.test.acceptance;

import static net.palmund.logger.Logger.JLog;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class LoggerTest_3 {
	@Test
	public void test() {
		JLog(LoggerTest_3.class, "Hej med dig");
	}
}