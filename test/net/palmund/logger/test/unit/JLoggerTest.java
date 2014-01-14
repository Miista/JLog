package net.palmund.logger.test.unit;

import static net.palmund.logger.Logger.JLog;
import static org.junit.Assert.fail;

import java.util.Collection;

import net.palmund.logger.LogMessage;
import net.palmund.logger.Logger;
import net.palmund.logger.LoggerFactory;
import net.palmund.logger.test.Stream;
import net.palmund.logger.test.TestFormatter;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JLoggerTest {
	static Stream p;
	static TestFormatter formatter;
	
	@BeforeClass
	public static void setup() {
		try {
			p = new Stream();
			formatter = new TestFormatter();
			LoggerFactory factory = LoggerFactory.getInstance();
			factory.setPrintStream(p);
			factory.setMessageFormatter(formatter);
		} catch (ClassCastException e) {
			fail("Could not inject "+Stream.class+" into Logger!");
		}
	}
	
	@Test
	public void testSimpleString() {
		JLog(JLoggerTest.class, "Hello, World!");
		Assert.assertTrue(p.getLatest().contains("Hello, World!")); //matches("\\[.+\\]\\s"+"hej med dig"));
	}
	
	@Test
	public void testTwoIntegers() {
		JLog(JLoggerTest.class, "The time is %d:%d", 12, 12);
		Assert.assertTrue(p.getLatest().contains("The time is 12:12"));
	}
	
	@Test
	public void testMultipleIntegers() {
		JLog(JLoggerTest.class, "%d+%d==%d", 2, 2, 4);
		Assert.assertTrue(p.getLatest().contains("2+2==4"));
	}
	
	@Test
	public void testBoolean() {
		JLog(JLoggerTest.class, "This variable is %s", Boolean.TRUE);
		Assert.assertTrue(p.getLatest().contains("This variable is true"));
	}
	
	@Test
	public void testMultipeStrings() {
		JLog(JLoggerTest.class, "Hello %s & %s", "Hans", "Grethel");
		Assert.assertTrue(p.getLatest().contains("Hello Hans & Grethel"));
	}
	
	@Test
	public void testAddingTwoIntegers() {
		JLog(JLoggerTest.class, "%d", 2+2);
		Assert.assertTrue(p.getLatest().endsWith("4"));
	}
	
	@Test
	public void testFloat() {
		JLog(JLoggerTest.class, "floating point: %f", 2f);
		Assert.assertTrue(p.getLatest().contains("floating point: 2,000000"));
	}
	
	@Test
	public void testLong() {
		JLog(JLoggerTest.class, "long: %d", 1L);
		Assert.assertTrue(p.getLatest().contains("long: 1"));
	}
	
	@Test
	public void testString_IgnoreClass() {
		String expectedMessage = "This is a string";
		String actualMessage = null;
		JLog(String.class, expectedMessage);
		Collection<LogMessage> messages = Logger.getMessageHistory();
		int i = 0;
		for (LogMessage m : messages) {
			if (i == messages.size() - 1) {
				actualMessage = m.getMessage();
			}
			i++;
		}
		/*
		 * We expect that the actual message posted to the message history is the same as the one we posted.
		 * However we do not expect it to have been printed to the output stream.
		 */
		Assert.assertEquals(expectedMessage, actualMessage); // Has the message been posted to the history?
		Assert.assertFalse(expectedMessage.equals(p.getLatest())); // Has it been printed?
	}
}