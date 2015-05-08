package com.dna.nexsus.program3;
/* @Author : Utsav Popli
 * This file provides basic functionality like read, write, seek, search, close, 
 *  create new file etc. for a given file 
 * 
 * 
 * 
 * 
 * */
import java.io.*;
import java.nio.*;

public class FileIO {

	private String fileName;
	private RandomAccessFile outputFile;
	private BufferedOutputStream outputWrite;
	private File file;

	public void writeToFile(String line, Integer lineNumber) {
		String data = lineNumber + ":" + " " + line;
		try {
			this.getOutputFile().write(data.getBytes());
			this.getOutputFile().write(System.lineSeparator().getBytes());

		} catch (IOException e) {
			System.out.println("0x1234 Error in writing to chunk file "
					+ this.getClass().toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public FileIO(String fileName) {
		this.setFileName(fileName);
		this.setFile(new File(getFileName()));

		try {
			this.setOutputFile(new RandomAccessFile(this.getFile(), "rw"));
			this.getOutputFile().seek(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public BufferedOutputStream getOutputWrite() {
		return outputWrite;
	}

	public void setOutputWrite(BufferedOutputStream outputWrite) {
		this.outputWrite = outputWrite;
	}

	public String readFromFile() {
		String line = null;
		// String s = new String (new byte[] {this.out.get(index)});
		try {
			if ((line = this.getOutputFile().readLine()) != null) {
				return line;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public RandomAccessFile getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(RandomAccessFile outputFile) {
		this.outputFile = outputFile;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean fileExits() {
		boolean flag = false;
		try {
			flag = getFile().exists();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public void seekToStart() {
		try {
			this.getOutputFile().seek(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String searchIndex(Integer lineNumber) {
		try {
			String line = this.getOutputFile().readLine();
			if (line.contains(lineNumber.toString() + ":")) {
				System.out.println(line);
				return line;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getLineCount() {
		int lineCount = 0;
		try {
			this.getOutputFile().seek(0);
			while (this.getOutputFile().readLine() != null) {
				lineCount++;
			}
			this.getOutputFile().seek(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lineCount;
	}

	public void closeFile() {
		try {
			this.getOutputFile().close();
			// this.getOutputWrite().close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in closing file "
					+ this.getClass().toString());
			e.printStackTrace();
		}
	}

	public void createNewFile() {
		try {
			this.getFile().createNewFile();

			// this.setOutputWrite(new BufferedOutputStream ( new
			// FileOutputStream(this.getFileName())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
