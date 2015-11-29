package CS172_Info_Ret.SearchEngine.Indexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IndexerUtilities {
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
}
