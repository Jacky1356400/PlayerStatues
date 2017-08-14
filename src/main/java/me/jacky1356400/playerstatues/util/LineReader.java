package me.jacky1356400.playerstatues.util;

import java.io.*;

public class LineReader extends BufferedReader {
	String	filename;
	int		line	= 0;
	
	public LineReader(File file) throws FileNotFoundException {
		super(new FileReader(file));
		
		filename = file.getName();
	}
	
	@Override public String readLine() {
		line++;
		
		try {
			return super.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	String where(){
		return filename+", line "+line;
	}
	
	public void die(String cause) throws IOException{
		throw new IOException(where()+": "+cause);
	}
}
