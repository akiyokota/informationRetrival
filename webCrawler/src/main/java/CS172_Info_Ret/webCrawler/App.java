package CS172_Info_Ret.webCrawler;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import CS172_Info_Ret.webCrawler.Objects.NormalizedUrl;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Queue<String> urlQueue = new LinkedList<String>();
	private static HashMap<String,Boolean> alreadyFound = new HashMap<String,Boolean>();
	private static Long numPages = 1500L;
	private static Long numHops = 10L;
	
	public static void parseUrl (String url) {
		Crawler c = new Crawler (url);
    	
    	c.ParseRobots();
    	c.linkExtraction();
    	
		//print them out
		for(NormalizedUrl Nurl : c.getNormalizedUrlList()) {
			//Nurl.print();
			if(!alreadyFound.containsKey(Nurl.generateCleanUrl()) /*or excluded by robots.txt*/ ){
				urlQueue.add(Nurl.generateCleanUrl());
				alreadyFound.put(Nurl.generateCleanUrl(), true);
			}
		}
	}
	
    public static void main( String[] args ) throws MalformedURLException
    {
    	
    	try {
    		
    		//Add seed to queue
        	String url = "http://www.about.com/";
        	urlQueue.add(url);
        	alreadyFound.put(url,true);
        	
			Long numPagesCounter = 0L;
			
			while(!urlQueue.isEmpty() && numPagesCounter < numPages) {
				numPagesCounter++;
				url = urlQueue.remove();
				System.out.print(numPagesCounter);
				System.out.println("\t" + url);
				parseUrl(url);
			}
        	
		} catch (Exception e) {			
			e.printStackTrace();
		}
    	
    }
}
