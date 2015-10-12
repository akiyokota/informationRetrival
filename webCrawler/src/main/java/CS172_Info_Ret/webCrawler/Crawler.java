package CS172_Info_Ret.webCrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Crawler {
	public static String downloadFile() throws IOException, MalformedURLException {
		String url = "http://www.about.com/robots.txt";
		URL urlObj = new URL(url);
		
		BufferedReader x = new BufferedReader (new InputStreamReader(urlObj.openConnection().getInputStream()));
		
		String fileName = "sample.dld";
		
		BufferedWriter fos = new BufferedWriter(new FileWriter( "./" + fileName));
		
		while(x.ready()) {
			String line = x.readLine();
			fos.write(line);
			fos.write("\n");
		}
		
		x.close();
		fos.close();
		
		return fileName;
	}
	
	
	public static String downloadPage(String path, String fileName, String url) throws IOException, MalformedURLException {
		URL urlObj = new URL(url);
		
		BufferedReader x = new BufferedReader (new InputStreamReader(urlObj.openConnection().getInputStream()));
		
		
		BufferedWriter fos = new BufferedWriter(new FileWriter( path + fileName));
		
		while(x.ready()) {
			String line = x.readLine();
			fos.write(line);
			fos.write("\n");
		}
		
		x.close();
		fos.close();
		
		return fileName;
	}
	
	public static void main(String [] args) {
		System.out.println("hello world");
		
		
		try {
			//downloadFile();
			downloadPage("./", "sample.dld", "http://www.about.com/robots.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
