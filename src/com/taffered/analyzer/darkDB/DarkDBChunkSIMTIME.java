package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.DarkTimeUtils;
import com.taffered.utils.Uint32;

import javax.media.j3d.BranchGroup;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;

public class DarkDBChunkSIMTIME extends DarkDBChunk {

	private Uint32 time;
	
	private Uint32 uknown;
	
	public DarkDBChunkSIMTIME(DarkInputStream in, Uint32 offset, Uint32 len) {
		super(in, offset, len);
		readChunk();
	}

	@Override
	public DefaultMutableTreeNode getTreeNode() {
		DefaultMutableTreeNode tn =  new DefaultMutableTreeNode(super.getName());
		
		tn.add(new DefaultMutableTreeNode("Version high : " + this.getVersion_high()));
		tn.add(new DefaultMutableTreeNode("Version low : " + this.getVersion_low()));
		tn.add(new DefaultMutableTreeNode("Taille : " + this.getLength().toHexFString()));
		tn.add(new DefaultMutableTreeNode("Offset : " + this.getOffset().toHexFString()));
		
		
		tn.add(new DefaultMutableTreeNode("Durée de la mission : " + time.getValue() + " - " + DarkTimeUtils.INSTANCE.MilisToDuration(time.getValue())));
		tn.add(new DefaultMutableTreeNode("Uknown : " + uknown.getValue()));		
		return tn;
	}
	
	@Override
	protected void readChunk() {
		try {
			time = getIn().readUint32();
			uknown = getIn().readUint32();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public BranchGroup render() {
		// TODO Auto-generated method stub
		return null;
	}

}
