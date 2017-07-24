package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.UInt;

import javax.media.j3d.BranchGroup;
import javax.swing.tree.DefaultMutableTreeNode;

public class DarkDBChunkGAMFILE extends DarkDBChunk {
	
	private String gamFileName;

	public DarkDBChunkGAMFILE(DarkInputStream in, UInt offset, UInt len) {
		super(in, offset, len);
		readChunk();
	}

	@Override
	public DefaultMutableTreeNode getTreeNode() {
		DefaultMutableTreeNode tn =  super.getTreeNode();

		tn.add(new DefaultMutableTreeNode("Gam File : " + this.gamFileName));

		return tn;
	}

	@Override
	protected void readChunk() {
		try{
			gamFileName = getIn().readStaticString((int) getLength().getValue());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public BranchGroup render() {
		// TODO Auto-generated method stub
		return null;
	}

}
