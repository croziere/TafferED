package com.taffered.analyzer.darkDB;

import com.taffered.utils.Uint32;

import javax.swing.tree.DefaultMutableTreeNode;

public class DarkDBInvItem implements Treeable {

	private String name;
	private Uint32 offset;
	private Uint32 length;
	
	
	
	public DarkDBInvItem(String name, Uint32 offset, Uint32 length) {
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
	public Uint32 getOffset() {
		return offset;
	}

	/**
	 * @return the length
	 */
	public Uint32 getLength() {
		return length;
	}
	
	
}
