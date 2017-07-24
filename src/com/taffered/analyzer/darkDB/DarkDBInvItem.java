package com.taffered.analyzer.darkDB;

import com.taffered.utils.UInt;

import javax.swing.tree.DefaultMutableTreeNode;

public class DarkDBInvItem implements Treeable {

	private String name;
	private UInt offset;
	private UInt length;
	
	
	
	public DarkDBInvItem(String name, UInt offset, UInt length) {
		this.name = name;
		this.offset = offset;
		this.length = length;
	}



	@Override
	public DefaultMutableTreeNode getTreeNode() {
		DefaultMutableTreeNode tn = new DefaultMutableTreeNode(name);
		
		tn.add(new DefaultMutableTreeNode("Offset : " + offset.toHexFString()));
		tn.add(new DefaultMutableTreeNode("Length : " + length.toHexFString()));
		return tn;
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the offset
	 */
	public UInt getOffset() {
		return offset;
	}

	/**
	 * @return the length
	 */
	public UInt getLength() {
		return length;
	}
	
	
}
