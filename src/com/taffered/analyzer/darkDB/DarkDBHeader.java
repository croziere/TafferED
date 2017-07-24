package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.UByte;
import com.taffered.utils.UInt;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;

/**
 * Wrap a DarkDBFile Header
 * Contains offset to the file inventory
 * Check the DEAD BEEF presence
 */
public class DarkDBHeader implements Treeable {

	/**
	 * Inventory offset
	 */
	private UInt inv_offset;
	
	private UInt zero;
	
	private UInt one;
	
	private UByte[] zeros;

	/**
	 * Check the file
	 */
	private UInt dead_beef;
	
	private static final int ZEROS_SIZE = 256;
	
	public static final int HEADER_SIZE = 32+32+32+(8*256)+32;

	/**
	 * DEAD BEEF constant value
	 */
	private static final int DEAD_BEEF = 0xEFBEADDE;
	
	public DarkDBHeader(DarkInputStream darkInputStream) throws InvalidDarkDBFileException {

		zeros = new UByte[ZEROS_SIZE];
		try {
			this.inv_offset = darkInputStream.readUint32();
			
			this.zero = darkInputStream.readUint32();
			
			this.one = darkInputStream.readUint32();
			
			for(int i = 0; i < ZEROS_SIZE; i++) {
				zeros[i] = darkInputStream.readUint8();
			}
			
			this.dead_beef = darkInputStream.readUint32();
			
			if(!Integer.toHexString(DEAD_BEEF).equals(Long.toHexString(dead_beef.getValue()))) {
				throw new InvalidDarkDBFileException("DEADBEEF not found");
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public UInt getInvOffset() {
		return inv_offset;
	}


	@Override
	public String toString() {
		String s = "Header : \n";
		s += "Offset : " + inv_offset.toHexFString();
		s += "\nDead Beef : " + dead_beef.toHexFString();
		
		return s;
	}


	@NotNull
	@Override
	public DefaultMutableTreeNode getTreeNode() {
		DefaultMutableTreeNode tn = new DefaultMutableTreeNode("DarkDBHeader");
		
		tn.add(new DefaultMutableTreeNode("Offset : " + inv_offset.toHexFString()));
		tn.add(new DefaultMutableTreeNode("Dead Beef : " + dead_beef.toHexFString()));
		
		return tn;
	}
}