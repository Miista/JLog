package net.palmund.logger;

import java.io.PrintStream;

public final class LogMessagePrinter {
	private PrintStream printStream;
	private MessageFormatter formatter;
	
	public LogMessagePrinter(PrintStream out, MessageFormatter formatter) {
		this.formatter = formatter;
		this.printStream = out;
	}
	
	public void printMessage(LogMessage message) {
		String formattedMessage = formatter.format(message);
		printStream.println(formattedMessage);
	}

	public PrintStream getPrintStream() {
		return printStream;
	}
}