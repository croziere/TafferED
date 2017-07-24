package com.taffered.utils;

/**
 * Wraps a unsigned int of 32bit size
 */
public class UInt {

	public static final int BYTE_SIZE = 4;

	/**
	 * Real value stored as an int
	 */
	private int value;
	
	public UInt(int value) {
		this.value = value;
	}
	
	public long getValue() {
		return Integer.toUnsignedLong(value);
	}
	
	public String toHexFString() {
		return "0x" + Long.toHexString(getValue()).toUpperCase();
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Long.toString(getValue());
	}
	
	
}
