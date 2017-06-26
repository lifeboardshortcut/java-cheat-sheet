package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WriteFile {
	public static void main(String[] args){
		File f = new File("output.txt");
		try{
			PrintWriter pw = new PrintWriter(f);
			pw.println("Hello World");
			pw.close();
		} catch(FileNotFoundException ex){}

	}
}
