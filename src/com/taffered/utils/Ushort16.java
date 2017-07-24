package com.taffered.utils;

/**
 * Wraps an unsigned int of 16 bit size
 */
public class Ushort16 {

	public static final int BYTE_SIZE = 2;

	/**
	 * Real value stored as a char
	 */
	private char value;
	
	public Ushort16(char value) {
		this.value = value;
	}
	
	public Ushort16(short value) {
		this.value = (char) value;
	}

	public int getValue() {
		return (int) value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Integer.toString(getValue());
	}
	
	
}
