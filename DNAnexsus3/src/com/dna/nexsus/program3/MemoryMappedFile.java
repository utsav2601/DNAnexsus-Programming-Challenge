package com.dna.nexsus.program3;
/* @Author : Utsav Popli
 * Java class to optimize the performance of File IO 
 * This is an imlementation of Memory mapped file
 * for better performace over traditional File IO
 * 
 * 
 * 
 * 
 * */

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class MemoryMappedFile {
	private MappedByteBuffer out;
	private RandomAccessFile ra;
	private String fileName;
	public MappedByteBuffer getMappedFile() {
		return this.out;
	}
	
	public long getMappedFileLength()
	{
		long length = 0;
		try {
			return this.getRa().length();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return length;
	}
	public MemoryMappedFile(String fileName) {
		this.setFileName(fileName);
		System.out.println("Input File Name" + this.getFileName());
		try {
			this.setRa(new RandomAccessFile(this.getFileName(), "r"));
			this.setOut(ra.getChannel().map(FileChannel.MapMode.READ_ONLY, 0,
					ra.length()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error in mapping input file , 0x1234");
			e.printStackTrace();
		}

	}
	
	//return line 
	public String readFromFile()
	{
	
		//String s = new String (new byte[] {this.out.get(index)});
	try {
		return this.getRa().readLine();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	}
	
	public MappedByteBuffer getOut() {
		return out;
	}

	public void setOut(MappedByteBuffer out) {
		this.out = out;
	}

	public RandomAccessFile getRa() {
		return ra;
	}

	public void setRa(RandomAccessFile ra) {
		this.ra = ra;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void closeFile()
	{
	try {
		this.getRa().close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("Error in closing mapped file " + this.getClass().toString());
		e.printStackTrace();
	}	
	}
	
	public int getLineCount()
	{
		int lineCount =0;
		try {
			while(this.getRa().readLine() != null)
			{
				lineCount ++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lineCount;
	}
	

} // /:~
