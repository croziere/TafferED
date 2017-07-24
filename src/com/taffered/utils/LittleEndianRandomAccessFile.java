package com.taffered.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This class is a RAF stream with Little Endianess reading method
 */
public class LittleEndianRandomAccessFile extends RandomAccessFile {
    private byte[] buffer;

    LittleEndianRandomAccessFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
        buffer = new byte[8];
    }

    public int readLittleInt() throws IOException {
        read(buffer, 0, 4);

        return (buffer[3]) << 24 | (buffer[2] & 0xff) << 16 | (buffer[1] & 0xff) << 8 | (buffer[0] & 0xff);
    }

    public short readLittleShort() throws IOException {
        read(buffer, 0, 2);

        return (short) ((buffer[1] << 8) | (buffer[0] & 0xff));
    }

    public float readLittleFloat() throws IOException {
        int a = readLittleInt();

        return Float.intBitsToFloat(a);
    }
}
