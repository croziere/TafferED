package com.taffered.analyzer;

import com.taffered.analyzer.darkDB.DarkDBFile;
import com.taffered.analyzer.darkDB.InvalidDarkDBFileException;
import com.taffered.analyzer.darkDB.MissFile;

import java.io.File;
import java.io.FileNotFoundException;

public class Decompiler {
	
	private String m_FilePath;
	private String m_MissName;
	private String m_TempPath;
	private boolean m_isDecompiled;
	
	private DarkDBFile miss;
	
	/**
	 * @return the m_FilePath
	 */
	public String getFilePath() {
		return m_FilePath;
	}

	/**
	 * @return the m_TempPath
	 */
	public String getTempPath() {
		if(this.isDecompiled())
			return m_TempPath;
		else
			return null;
	}


	/**
	 * @return the m_isDecompiled
	 */
    private boolean isDecompiled() {
		return m_isDecompiled;
	}
	
	

	private Decompiler(String path)
	{
		this.m_FilePath = (path.trim()).toLowerCase();
		this.m_MissName = (path.trim()).replace(".mis", "");
		this.m_isDecompiled = false;
		System.out.println("Decompiler ready : \nFile : " + m_FilePath + "\nMiss Name : " + m_MissName);
	}
	
	public Decompiler(File selectedFile) {
		this(selectedFile.getAbsolutePath());
	}

	public void decompile() throws NoMapSpecifiedException, DecompilingException, InvalidDarkDBFileException
	{
		if(this.m_FilePath == null || this.m_FilePath.isEmpty())
		{
			throw new NoMapSpecifiedException();
		}		
		
		this.m_TempPath = "input/"+this.m_MissName+".tmp";
		
		try {
			miss = new MissFile(this.m_FilePath);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	public DarkDBFile getMissFile() {
		if(miss != null) {
			return miss;
		}
		else
			throw new IllegalStateException();
	}
	
	@SuppressWarnings("serial")
	public class NoMapSpecifiedException extends Exception
	{

		@Override
		public String getMessage() {
			return "You tried to use decompile() without specifing a map file";
		}
		
				
	}
	
	@SuppressWarnings("serial")
	public class DecompilingException extends Exception
	{
		@Override
		public String getMessage() {
			return "There was an error using lsbrush.exe, see log trace :";
		}
	}
}