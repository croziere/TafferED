package com.taffered.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Stream with utility method to read all DarkDB files
 */
public class DarkInputStream extends LittleEndianRandomAccessFile {

	public DarkInputStream(File file, String mode) throws FileNotFoundException {
		super(file, mode);
	}

    /**
     * Read one UInt at the given position
     * @return UInt The read value
     * @throws IOException If a stream error occurs
     */
	public UInt readUint32() throws IOException {

		return new UInt(this.readLittleInt());
	}

    /**
     * Read one UShort at the given position
     * @return UShort The read value
     * @throws IOException If a stream error occurs
     */
	public UShort readUint16() throws IOException {
		return new UShort(this.readLittleShort());
	}

	public UByte readUint8() throws IOException {
		return new UByte(this.readByte());
	}

	public String readStaticString(int size) throws IOException {
        byte buf[] = new byte[size];
        this.read(buf, 0, size);
        return new String(buf);
    }

}
