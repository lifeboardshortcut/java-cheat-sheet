package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadWebsite {
	public static void main(String[] args){
		//String url = "http://www.google.com";
		try{
			URL url = new URL("http://google.com");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while((line = br.readLine()) != null)
				{ System.out.println(line); }
			br.close();
		}
		catch(MalformedURLException ex){ }
		catch(IOException ex) {}
	}
}
