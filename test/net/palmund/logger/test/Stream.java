package net.palmund.logger.test;


import java.io.PrintStream;
import java.util.ArrayList;

public class Stream extends PrintStream {
	private ArrayList<String> latest = new ArrayList<String>();
	
	public Stream() {
		super(System.out);
	}
	
	@Override
	public void print(String obj) {
		latest.add(obj);
		super.print(obj);
	}

	@Override
	public void write(byte[] buf, int off, int len) {
//		super.write(buf, off, len);
	}
	
	public String getLatest() {
		return latest.get(latest.size() - 1);
	}
	
	public void pop() {
		latest.remove(latest.size() - 1);
	}
	
	public void reset() {
		latest.clear();
	}
}