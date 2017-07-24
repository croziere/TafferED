package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.UInt;
import org.jetbrains.annotations.NotNull;

import javax.media.j3d.BranchGroup;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class DarkDBChunkBRLIST extends DarkDBChunk {
	
	//Header + UByte + Array of brushes
	private Map<Integer, Brush> brushes = new TreeMap<>();
	
	
	
	public DarkDBChunkBRLIST(DarkInputStream in, UInt offset, UInt len) {
		super(in, offset, len);		
		readChunk();
	}

	@NotNull
	@Override
	public DefaultMutableTreeNode getTreeNode() {
		DefaultMutableTreeNode tn =  super.getTreeNode();
		DefaultMutableTreeNode brs = new DefaultMutableTreeNode("Data : BRLIST Chunk");
		
		Set<Entry<Integer, Brush>> brSet = brushes.entrySet();
		
		for(Entry<Integer, Brush> e : brSet) {
			brs.add(e.getValue().getTreeNode());
		}
		
		tn.add(brs);
			
		return tn;
	}

	@Override
	protected final void readChunk() {
		try {		
			getIn().seek(getOffset().getValue());
			long ptr = getOffset().getValue();
			long end = ptr + super.getLength().getValue();
			
			while(ptr < end) {
				Brush b = new Brush(getIn());
				ptr += b.getDataSize();
				brushes.put(b.getId().getValue(), b);				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public BranchGroup render() {
		BranchGroup scene = new BranchGroup();
		
		for(Entry<Integer, Brush> en : brushes.entrySet()) {			
				scene.addChild(en.getValue().render());			
		}
		
		return scene;
	}

}
