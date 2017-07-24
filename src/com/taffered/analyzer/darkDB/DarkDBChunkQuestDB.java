package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.UInt;

import javax.media.j3d.BranchGroup;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DarkDBChunkQuestDB extends DarkDBChunk {

	
	private List<DarkDBQuestVar> vars = new ArrayList<>();
	
	
	public DarkDBChunkQuestDB(DarkInputStream in, UInt offset, UInt len) {
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
		DefaultMutableTreeNode vtree = new DefaultMutableTreeNode("Quest Vars");
		
		for(DarkDBQuestVar v : vars)
			vtree.add(v.getTreeNode());
		tn.add(vtree);
		
		return tn;
	}

	@Override
	protected void readChunk() {
		try {
			while(getIn().getFilePointer() < (getOffset().getValue() + getLength().getValue())){
				vars.add(new DarkDBQuestVar(getIn()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public BranchGroup render() {
		// TODO Auto-generated method stub
		return null;
	}

}
