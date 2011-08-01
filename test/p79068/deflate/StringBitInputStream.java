package p79068.deflate;

import java.io.EOFException;
import java.io.IOException;

import nayuki.huffmancoding.BitInputStream;


final class StringBitInputStream implements BitInputStream {
	
	private String string;
	
	private int index;
	
	
	public StringBitInputStream(String str) {
		if (!string.matches("[01]*"))
			throw new IllegalArgumentException();
		string = str;
		index = 0;
	}
	
	
	
	public int read() throws IOException {
		if (index == string.length())
			return -1;
		else {
			int result = string.charAt(index) - '0';
			index++;
			return result;
		}
	}
	
	
	public int readNoEof() throws IOException {
		int result = read();
		if (result != -1)
			return result;
		else
			throw new EOFException("End of stream reached");
	}
	
	
	public void close() {
		index = string.length();
	}
	
	
	@Override
	public int getBitPosition() {
		return index % 8;
	}
	
	
	@Override
	public int readByte() throws IOException {
		while (index % 8 != 0)
			readNoEof();
		
		int result = 0;
		for (int i = 0; i < 8; i++)
			result |= readNoEof() << i;
		return result;
	}
	
}
