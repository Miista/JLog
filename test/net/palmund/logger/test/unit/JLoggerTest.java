package net.palmund.logger.test.unit;

import static org.junit.Assert.*;

import java.util.Collection;

import net.palmund.logger.LogMessage;
import net.palmund.logger.Logger;
import static net.palmund.logger.Logger.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class JLoggerTest {
	static Stream p;
	
	Logger logger;
	
	@BeforeClass
	public static void setup() {
		try {
			p = (Stream) Logger.getLogger(JLoggerTest.class).messagePrinter.getPrintStream();
		} catch (ClassCastException e) {
			fail("Could not inject "+Stream.class+" into Logger!");
		}
//		System.setOut(p);
	}
	
	@Test
	public void testSimpleString() {
//		JLog(JLoggerTest.class, "hej med dig");
		JLog(JLoggerTest.class, "hej med dig");
//		AbstractLogger.getLogger(JLoggerTest.class).log("hej med dig");
//		Stream stream = Logger.getPrintStream(JLoggerTest.class);
//		System.out.println(stream);
		assertTrue(p.getLatest().contains("hej med dig")); //matches("\\[.+\\]\\s"+"hej med dig"));
	}
	
	@Test
	public void testTwoIntegers() {
//		JLog(JLoggerTest.class, "klokken er %d:%d", 12, 12);
//		AbstractLogger.getLogger(JLoggerTest.class).log("klokken er %d:%d", 12, 12);
		JLog(JLoggerTest.class, "klokken er %d:%d", 12, 12);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"klokken er 12:12"));
		assertTrue(p.getLatest().contains("klokken er 12:12"));
	}
	
	@Test
	public void testMultipleIntegers() {
//		JLog(JLoggerTest.class, "%d+%d!=%d", 2, 2, 4);
//		AbstractLogger.getLogger(JLoggerTest.class).log("%d+%d!=%d", 2, 2, 4);
		JLog(JLoggerTest.class, "%d+%d!=%d", 2, 2, 4);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"2\\+2\\!=4"));
		assertTrue(p.getLatest().contains("2+2!=4"));
	}
	
	@Test
	public void testBoolean() {
//		JLog(JLoggerTest.class, "denne variable er %s", Boolean.TRUE);
		JLog(JLoggerTest.class, "denne variable er %s", Boolean.TRUE);
		assertTrue(p.getLatest().contains("denne variable er true"));
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"denne variable er true"));
	}
	
	@Test
	public void testMultipeStrings() {
//		JLog(JLoggerTest.class, "hej %s & %s", "hans", "bent");
		JLog(JLoggerTest.class, "hej %s & %s", "hans", "bent");
		assertTrue(p.getLatest().contains("hej hans & bent"));
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"hej hans & bent"));
	}
	
	@Test
	public void testAddingTwoIntegers() {
//		JLog(JLoggerTest.class, "%d", 2+2);
		JLog(JLoggerTest.class, "%d", 2+2);
		assertTrue(p.getLatest().endsWith("4"));
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"4"));
	}
	
	@Test
	public void testFloat() {
//		JLog(JLoggerTest.class, "floating point: %f", 2f);
		JLog(JLoggerTest.class, "floating point: %f", 2f);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"floating point: 2,000000"));
		assertTrue(p.getLatest().contains("floating point: 2,000000"));
	}
	
	@Test
	public void testLong() {
		JLog(JLoggerTest.class, "long: %d", 1L);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"long: 1"));
		assertTrue(p.getLatest().contains("long: 1"));
	}
	
	@Test
	public void testSimpleString_NoClass() {
//		JLog(JLoggerTest.class, "hej med dig");
		JLog(JLoggerTest.class, "hej med dig");
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"hej med dig"));
		assertTrue(p.getLatest().contains("hej med dig"));
	}
	
	@Test
	public void testTwoIntegers_NoClass() {
//		JLog(JLoggerTest.class, "klokken er %d:%d", 12, 12);
		JLog(JLoggerTest.class, "klokken er %d:%d", 12, 12);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"klokken er 12:12"));
		assertTrue(p.getLatest().contains("klokken er 12:12"));
	}
	
	@Test
	public void testMultipleIntegers_NoClass() {
//		JLog(JLoggerTest.class, "%d+%d!=%d", 2, 2, 4);
		JLog(JLoggerTest.class, "%d+%d!=%d", 2, 2, 4);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"2\\+2\\!=4"));
		assertTrue(p.getLatest().contains("2+2!=4"));
	}
	
	@Test
	public void testBoolean_NoClass() {
		JLog(JLoggerTest.class, "denne variable er %s", Boolean.TRUE);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"denne variable er true"));
		assertTrue(p.getLatest().contains("denne variable er true"));
	}
	
	@Test
	public void testMultipeStrings_NoClass() {
//		JLog(JLoggerTest.class, "hej %s & %s", "hans", "bent");
		JLog(JLoggerTest.class, "hej %s & %s", "hans", "bent");
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"hej hans & bent"));
		assertTrue(p.getLatest().contains("hej hans & bent"));
	}
	
	@Test
	public void testAddingTwoIntegers_NoClass() {
//		JLog(JLoggerTest.class, "%d", 2+2);
		JLog(JLoggerTest.class, "%d", 2+2);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"4"));
		assertTrue(p.getLatest().endsWith("4"));
	}
	
	@Test
	public void testFloat_NoClass() {
//		JLog(JLoggerTest.class, "floating point: %f", 2f);
		JLog(JLoggerTest.class, "floating point: %f", 2f);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"floating point: 2,000000"));
		assertTrue(p.getLatest().contains("floating point: 2,000000"));
	}
	
	@Test
	public void testLong_NoClass() {
//		JLog(JLoggerTest.class, "long: %d", 1L);
		JLog(JLoggerTest.class, "long: %d", 1L);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"long: 1"));
		assertTrue(p.getLatest().contains("long: 1"));
	}
	
	@Test
	public void testString_IgnoreClass() {
//		JLog(JLoggerTest.class, "long: %d", 1L);
		String expectedMessage = "this is a string";
		String actualMessage = null;
		JLog(String.class, expectedMessage);
//		assertTrue(p.getLatest().matches("\\[.+\\]\\s"+"long: 1"));
		Collection<LogMessage> messages = Logger.getAllLoggedMessages();
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
		assertEquals(expectedMessage, actualMessage); // Has the message been posted to the history?
		assertTrue(p.getLatest().contains("long: 1")); // Has it been printed?
	}
}