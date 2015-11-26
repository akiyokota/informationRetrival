package CS172_Info_Ret.webCrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

import CS172_Info_Ret.webCrawler.Objects.Pair;

public class CrawlerUtilities {
	
	/**
	 * Reads a file given the filename(path+filename)
	 * return the content of the file
	 */
	public String readFile(String filename) {
		StringBuilder content = new StringBuilder();
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(filename));
	    	String line;
	    	while((line = br.readLine())!=null) 
	    		content.append(line).append("\n");
	    	
	    	br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return content.toString();
	}
	
	/**
	 * Write the content to the given filename(path+filename)
	 */
	public void writeFile(String filename, String content) {
		try {
			BufferedWriter fos = new BufferedWriter(new FileWriter( filename));
			fos.write(content);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Append the content to the given filename(path+filename)
	 */
	public void appendToFile(String filename, String content) {
		try {
			content = content + "\n";
			Files.write(Paths.get(filename), content.getBytes(), StandardOpenOption.APPEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * perform ls non-recursively
	 * Return a list of files only with the given directory
	 */
	public List<String> ListSegments (String directory) {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		List<String> files = new LinkedList<String> ();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) 
		        files.add(listOfFiles[i].getName());
		}
		
		return files;
	}
	
	/**
	 * For a given directory, read all the files inside to extract the seeds
	 * Return a queue of url seeds
	 */
	public Queue<Pair> loadSeeds (String directory) {
		Queue<Pair> urlQueue = new LinkedList<Pair> ();
		for ( String file : ListSegments(directory)) {
			for ( String url : TokenizeByNewLine(readFile(directory + "/" +  file))) 
				urlQueue.add(new Pair(url, 0));
		}
		return urlQueue;
	}
	
	/**
	 * Tokenize the content by newline character
	 * Return a list of string of the content
	 */
	public List<String> TokenizeByNewLine(String content) {
		List <String> tokens = new LinkedList<String> ();
		
		StringTokenizer st = new StringTokenizer(content, "\n");
		
		while(st.hasMoreElements()) {
			tokens.add(st.nextElement().toString());
		}
		return tokens;
	}
	
	/**
	 * check if the given file exists
	 * Return true if file exists
	 * Return false if file doesn't exist
	 */
	public boolean fileExists(String file) {
		File f = new File(file);
		if (f.exists() ) {
		   return true;
		}
		return false;
	}
}
