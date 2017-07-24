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
     * Read one Uint32 at the given position
     * @return Uint32 The read value
     * @throws IOException If a stream error occurs
     */
	public Uint32 readUint32() throws IOException {

		return new Uint32(this.readLittleInt());
	}

    /**
     * Read one Ushort16 at the given position
     * @return Ushort16 The read value
     * @throws IOException If a stream error occurs
     */
	public Ushort16 readUint16() throws IOException {
		return new Ushort16(this.readLittleShort());
	}

	public Ubyte8 readUint8() throws IOException {
		return new Ubyte8(this.readByte());
	}

	public String readStaticString(int size) throws IOException {
        byte buf[] = new byte[size];
        this.read(buf, 0, size);
        return new String(buf);
    }

}
