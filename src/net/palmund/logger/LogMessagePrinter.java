package net.palmund.logger;

import java.io.PrintStream;

public class LogMessagePrinter {
	private PrintStream printStream;
	private LogFormatter formatter;
	
	public LogMessagePrinter(PrintStream out, LogFormatter formatter) {
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
	
//	@Deprecated
//	public void setFormatter(LogFormatter formatter) {
//		this.formatter = formatter;
//	}
}