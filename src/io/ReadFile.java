package io;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile {
	public static void main(String[] args){
		File f = new File("list.txt");
		try{
			Scanner scan = new Scanner(f);
			String line;
			while(scan.hasNextLine()){
				line = scan.nextLine();
				System.out.println(line);
			}
			scan.close();
		}
		catch(IOException ex){}

	}
}
