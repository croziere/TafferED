package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.Uint32;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;

class DarkDBQuestVar implements Treeable {
	
	private DarkInputStream stream;

	private Uint32 nameLength;

	private String name;
	
	private int value;
	
	
	public DarkDBQuestVar(DarkInputStream stream) {
		this.stream = stream;
		try {
			readData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readData() throws IOException {
		
		nameLength = stream.readUint32();
		name = stream.readStaticString((int) nameLength.getValue());
		value = stream.readLittleInt();
	}



	@Override
	public DefaultMutableTreeNode getTreeNode() {
		return new DefaultMutableTreeNode(name + " = " + value);
	}

}
