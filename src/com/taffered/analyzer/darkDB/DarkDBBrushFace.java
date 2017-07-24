package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.UShort;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;

class DarkDBBrushFace implements Treeable {

	private short texture;
	
	private UShort rotation;
	
	private UShort scale;
	
	private UShort offset[];
	
	private DarkInputStream stream;
	
	public DarkDBBrushFace(DarkInputStream stream) {
		this.stream = stream;
		offset = new UShort[2];
		try {
			readData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readData() throws IOException {
		texture = stream.readLittleShort();
		rotation = stream.readUint16();
		scale = stream.readUint16();
		offset[0] = stream.readUint16();
		offset[1] = stream.readUint16();
	}

	@Override
	public DefaultMutableTreeNode getTreeNode() {
		DefaultMutableTreeNode tn = new DefaultMutableTreeNode("Face");
		
		tn.add(new DefaultMutableTreeNode("Texture : " + texture));
		tn.add(new DefaultMutableTreeNode("Rotation : " + rotation.getValue()));
		tn.add(new DefaultMutableTreeNode("Scale : " + scale.getValue()));
		tn.add(new DefaultMutableTreeNode("Offset : (" + offset[0].getValue() + "-" + offset[1].getValue() + ")"));
		
		return tn;
	}
	
}
