package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.UInt;

import javax.media.j3d.BranchGroup;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;

public class DarkDBChunkFILETYPE extends DarkDBChunk {

	
	private static final long FILE_TYPE_VBR = 0x00000500;
	private static final long FILE_TYPE_SAV = 0x00011900;
	private static final long FILE_TYPE_MIS = 0x00031900;
	private static final long FILE_TYPE_GAM = 0x00040200;
	private static final long FILE_TYPE_COW = 0x00071F00;
	private static final long FILE_TYPE_MASK_ARCHAIC = 0xFFFF0000;
	
	private UInt type;
	
	public DarkDBChunkFILETYPE(DarkInputStream in, UInt offset, UInt len) {
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
		tn.add(new DefaultMutableTreeNode("File Type : " + FileTypeToName(type.getValue()) + " (" + type.toHexFString() + ")"));
		
		return tn;
	}

	@Override
	protected void readChunk() {
		
		try {
			type = getIn().readUint32();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String FileTypeToName(long type){
		if(type == FILE_TYPE_COW)
			return "COW File";
		
		if(type == FILE_TYPE_GAM)
			return "GAM File";
		
		if(type == FILE_TYPE_MASK_ARCHAIC)
			return "Archaic Mask File";
		
		if(type == FILE_TYPE_MIS)
			return "MISS File";
		
		if(type == FILE_TYPE_SAV)
			return "SAV File";
		
		if(type == FILE_TYPE_VBR)
			return "VBR File";
		
		return "Uknown DarkDBFile Type";
	}

	@Override
	public BranchGroup render() {
		// TODO Auto-generated method stub
		return null;
	}
}
