package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.Uint32;

import javax.media.j3d.BranchGroup;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Arrays;

public class DarkDBChunkGeneric extends DarkDBChunk {
	
	private byte data[];

	public DarkDBChunkGeneric(DarkInputStream in, Uint32 offset, Uint32 len) {
		super(in, offset, len);
		data = new byte[(int) len.getValue()];
		readChunk();
	}

	@Override
	public DefaultMutableTreeNode getTreeNode() {
		DefaultMutableTreeNode tn =  new DefaultMutableTreeNode(super.getName());
		
		tn.add(new DefaultMutableTreeNode("Version high : " + this.getVersion_high()));
		tn.add(new DefaultMutableTreeNode("Version low : " + this.getVersion_low()));
		tn.add(new DefaultMutableTreeNode("Taille : " + this.getLength().toHexFString()));
		tn.add(new DefaultMutableTreeNode("Offset : " + this.getOffset().toHexFString()));
		
		DefaultMutableTreeNode dat = new DefaultMutableTreeNode("Data (Bytes) : Generic Chunk");
		dat.add(new DefaultMutableTreeNode(Arrays.toString(data)));
		dat.add(new DefaultMutableTreeNode(new String(data)));
		tn.add(dat);
		
		return tn;
	}

	@Override
	protected void readChunk() {
		
		try {			
			getIn().read(data, 0, (int) getLength().getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println(offset.toHexFString());
			e.printStackTrace();
		}
	}

	@Override
	public BranchGroup render() {
		// TODO Auto-generated method stub
		return null;
	}

}
