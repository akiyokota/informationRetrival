package CS172_Info_Ret.webCrawler;

import java.net.MalformedURLException;

import CS172_Info_Ret.webCrawler.Objects.NormalizedUrl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws MalformedURLException
    {
    	try {
    		
    		
    		
    		
        	String url = "http://www.about.com/";
        	Crawler c = new Crawler (url);
        	
        	
        	
        	c.linkExtraction() ;
			
			//print them out
			for(NormalizedUrl Nurl : c.getNormalizedUrlList()) {
				Nurl.print();
			}
			
        	c.ParseRobots();
		} catch (Exception e) {			
			e.printStackTrace();
		}
    	
    }
}
