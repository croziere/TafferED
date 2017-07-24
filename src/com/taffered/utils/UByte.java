package com.taffered.utils;

/**
 * Wraps a unsigned int of 8 bits size
 */
public class UByte {

	public static final int BYTE_SIZE = 1;

	/**
	 * Real value stored as a byte
	 */
	private byte value;
	
	
	public UByte(byte value) {
		this.value = value;
	}
	
	public int getValue() {
		
		return Byte.toUnsignedInt(value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Integer.toString(getValue());
	}
	
	
	
}
