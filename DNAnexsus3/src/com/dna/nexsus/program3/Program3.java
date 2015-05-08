package com.dna.nexsus.program3;
import java.io.IOException;

/* @Author : Utsav Popli
 * An Implementation to optimize the File IO 
 * This class has the 'main' method
 * The program creates an index on the Chunk File 
 * and keeps track of every chunk with the Hash File ;;
 * Modify the location for hashFile, chunkFile accordingly in 
 * variables hashFile , outputFileName
 * 
 * **Assuming : line index and file number are provided 
 *   from the command line while running this program
 * 		in the format  1. Filname 2 . Line Index 
 * */
public class Program3 {

	// Define the number of parts into which file has to be divided
	private static final Integer chunks = 2;

	public static void main(String[] args) {

		if (args.length == 2) {
			Integer searchIndex = Integer.parseInt(args[1]);  //get the line index 
			FileIO inputFile = new FileIO(args[0]);			//get the fileName
			FileIO hashFile = new FileIO(				
					"/home/utsav/Documents/DNAnexus/hash.idx.txt");			
			Integer startLine = 0, linesPerChunk = inputFile.getLineCount()
					/ chunks, totalLines = inputFile.getLineCount();
			Integer endLine = linesPerChunk, lineNumber = 0, offset = 0;
			if (hashFile.readFromFile() == null) {		//if the hashFile is empty 
				System.out.println(args[0]);
				FileIO chunk[] = new FileIO[chunks];		//create a temporary chunk files
				hashFile.createNewFile();
				String outputFileName;
				for (int i = 0; i < chunks; i++) {
					offset = 0;
					outputFileName = "/home/utsav/Documents/DNAnexus/output.idx"
							+ i + ".txt";
					chunk[i] = new FileIO(outputFileName);
					chunk[i].createNewFile();
					// System.out.println("chunk file "+i );
					// System.out.println("start line " +startLine);
					// System.out.println("end line " + endLine);
					for (int j = startLine; j < endLine; j++) {
						lineNumber++;

						String line = inputFile.readFromFile();

						chunk[i].writeToFile(line, lineNumber);
						offset++;
					}
					hashFile.writeToFile(chunk[i].getFileName(), lineNumber);
					chunk[i].closeFile();

					if (endLine < totalLines) {
						startLine = endLine;
						endLine = linesPerChunk + startLine;

					} else {
						break; // break out of loop

					}
				}
				hashFile.closeFile();
			} //endIf;;  hash file exists
			else {
				hashFile.seekToStart();
				String fileName = null;
				for (int i = 0; i < chunks; i++) {
					String line = hashFile.readFromFile();
					String[] lineIndex = line.split(":");
					String index = lineIndex[0];
					fileName = lineIndex[1].trim();
					// System.out.println(fileName);
					if (searchIndex <= Integer.parseInt(index)) {
						break;
					}
				}
				FileIO searchFile = new FileIO(fileName);
				searchFile.seekToStart();
				for (int i = 0; i < linesPerChunk; i++) {
					// System.out.println(fileName);
					if (searchFile.searchIndex(searchIndex) != null) {
						break;
					}
				}
			} //endElse ; search index 

		}// endIf; not valid arguments

		else {

			System.out.println("Usage : 1. FileName 2. Index of Line");
		}

	}

}
