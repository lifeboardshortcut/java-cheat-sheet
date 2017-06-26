package io;

import java.util.Scanner;

public class ReadStandardInput {
	public static void main(String[] args){
		String s = "";
		Scanner scanner = new Scanner(System.in);
		s = scanner.nextLine();
		System.out.println(s);
		scanner.close();
	}
}
