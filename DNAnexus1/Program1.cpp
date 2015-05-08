/* @Author : Utsav Popli
 * DNAnexus Programming Challenge
 * To run this program , Provide the input file name and L from the command line
 * Usage://
 * g++ Program1.cpp -o Program1
 * Program1 7 '/media/utsav/Documents/input'
 * ///
 * Assumptions: Input file is binary and not corrupted
 * Change the location of output file in ofp
 * Input binary File should have atleast 1 byte of data
 * */

#include <iostream>
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
using namespace std;
//@Author : Utsav Popli Function to read the bytes from the file
void readBytesFromFile(int L);

////@Author : Utsav Popli Function to decode bits into proper format
void decodeBits(unsigned char bytes[], int L, int index);

////@Author : Utsav Popli Function to write decoded characters into file
void writeToFile(unsigned char *decodedChar, char * signature, int);

FILE *ifp;		//pointer to input file
FILE *ofp;		//pointer to output file
int main(int argc, char * argv[]) {
	//Given: Encoded binary File, argv[1]
	//

	if (argc != 3) {
		cout << "Usage : Provide 1.value for L  2. FilePath" << endl;
		cout << "ex. Program1 2 '/media/utsav/Documents/input' " << endl;
		return 0;

	}
	//Check if the FileName and L is provided as an argument or not
	if (argv[1] == '\0' || argv[2] == '\0') {
		cout << "Please Enter the value of L and FileName" << endl;
		cout << "Usage : Provide 1.value for L  2. FilePath" << endl;
		cout << "ex. Program1 2 '/media/utsav/Documents/input' " << endl;
		return 0;
	}
	cout << "Filename = " << argv[2] << endl; //FileName
	int L = atoi(argv[1]);
	cout << "L =" << L << endl;
	char * fileName = argv[2];
	ifp = fopen(fileName, "rb");   //input file
	ofp = fopen("/home/utsav/Documents/DNAnexus/output.txt", "w"); //output file name

	if (ifp == '\0') {
		cout << "Unable to open file, Please check the filename again" << endl;
		return 0;
	}
	if (ofp == '\0') {
		printf("Error writing to file!\n");
		return 0;
	}
	readBytesFromFile(L);
	//close the file stream
	fclose(ifp);
	fclose(ofp);
}

void readBytesFromFile(int L) {
	unsigned char bytes[L];
	int currentOffset = 0;
	int index = 1; // to keep the track of line numbers
	fseek(ifp, currentOffset, SEEK_END); //GET THE LENGTH of file BY TRAVERSING To the end
	size_t length = ftell(ifp);
	cout << "Length of file " << length << endl;
	fseek(ifp, currentOffset, SEEK_SET);	//SEEK TO THE BEGINNING
	while (currentOffset != length) {
		//read all the bytes of a file
		fread(bytes, L, 1, ifp);
		//Check to display hex digits of byte read
		for (int i = 0; i < L; i++) {
			printf("%x\n", bytes[i]);
		}
		cout << endl;
		//Decode the L bits
		decodeBits(bytes, L, index);
		currentOffset = currentOffset + L;	//Move the pointer to next byte;;
		fseek(ifp, currentOffset, SEEK_SET);
		index++;
	}
}

void decodeBits(unsigned char *bytes, int L, int index) {
	char *signature = "@READ_";
	char *qualitySignature = "+READ_";
	unsigned char decodeBit = 0;
	unsigned char qualityChar[L];		// last 6 bits
	unsigned char decodedChar[L];		//First 2 bits
	for (int i = 0; i < L; i++) {
		decodeBit = (bytes[i] >> 6);
		qualityChar[i] = (unsigned char) ((bytes[i] & 0x3F) + 0x21); //Mask to get only 6 bits
		if ((decodeBit ^ 0) == 0) {
			decodedChar[i] = 'A';
		} else if ((decodeBit ^ 1) == 0) {
			decodedChar[i] = 'C';
		} else if ((decodeBit ^ 2) == 0) {
			decodedChar[i] = 'G';
		} else if ((decodeBit ^ 3) == 0) {
			decodedChar[i] = 'T';
		}
	}
	writeToFile(decodedChar, signature, index);
	writeToFile(qualityChar, qualitySignature, index);
}

void writeToFile(unsigned char *decodedChar, char * signature, int index) {

	fprintf(ofp, "%s%d\n", signature, index);
	fprintf(ofp, "%s\n", decodedChar);

}
