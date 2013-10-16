package net.palmund.logger.test.acceptance;

import net.palmund.logger.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	LoggerTest_2.class,
	LoggerTest_3.class,
	LoggerTest_4.class
})
public class LoggerTestSuite {
	@Test
	public void test() {
		System.out.println(Logger.getAllLoggedMessages());
	}
}