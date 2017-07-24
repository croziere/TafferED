package com.taffered.utils;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Console extends JFrame {
	
	private static Console INSTANCE = new Console();
	
	private Console() {
		super("Console");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JTextArea cTxtArea = new JTextArea();
		cTxtArea.setLineWrap(true);
		cTxtArea.setEditable(false);
		JScrollPane sp = new JScrollPane(cTxtArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(sp);
		
		System.setOut(new PrintStream(new ConsoleOutputStream(cTxtArea)));
		//System.setErr(new PrintStream(new ConsoleOutputStream(cTxtArea)));
		
		this.setSize(1080, 720);
		this.setVisible(true);
	}
	
	public static Console getConsole() {
		return INSTANCE;
	}
	
	private class ConsoleInputStream extends InputStream {

		@Override
		public int read() throws IOException {
			
			return 0;
		}
		
	}
	
	private class ConsoleOutputStream extends OutputStream { //Implementer une meilleur console (flux avec gestion de la longueur/scroll)
		
		ArrayList<Byte> data = new ArrayList<>();
		
		private JTextArea output;
		
		public ConsoleOutputStream(JTextArea output) {
			this.output = output;
		}
		
		private void fireDataWritten() {
			int lines = 0;
			for (int i = 0; i < data.size(); i++) {
				byte b = data.get(i);
				
				if(b == 10) {
					lines++;
				}
				
				if(lines >= 250) {
					data = (ArrayList<Byte>)data.subList(i, data.size());
				}
			}
			
			StringBuilder sb = new StringBuilder();
			
			for(byte b : data) {
				sb.append((char) b);
			}
			
			output.setText(sb.toString());
		}
		
		@Override
		public void write(int i) throws IOException {
			data.add((byte) i);
			
			fireDataWritten();
			
		}
		
	}
	
	
}
