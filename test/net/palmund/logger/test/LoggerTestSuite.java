package net.palmund.logger.test;

import net.palmund.logger.Logger;
import net.palmund.logger.test.acceptance.LoggerTest_4;
import net.palmund.logger.test.unit.JLoggerTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	LoggerTest_4.class,
	JLoggerTest.class
})
public class LoggerTestSuite {
	@Test
	public void test() {
		System.out.println(Logger.getAllLoggedMessages());
	}
}