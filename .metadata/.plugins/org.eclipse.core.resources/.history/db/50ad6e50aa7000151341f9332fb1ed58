package CS172_Info_Ret.webCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import CS172_Info_Ret.webCrawler.Objects.NormalizedUrl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String filename = "sample.dld";
    	String url = "http://www.cs.ucr.edu";
    	
    	Crawler c = new Crawler ();
    	try {
    		//download the page into sample.dld
			c.downloadPage("./", filename, url);
			
			//parse out links
			List<URL> urlList = new LinkedList<URL> ();
			Document doc = Jsoup.connect(url).get();
			urlList = new ParseHTML().parseHTML(doc);
			
			//put them into normalized object
			List <NormalizedUrl> normalizedUrlList = new LinkedList<NormalizedUrl> ();
			for (URL u : urlList) {
				NormalizedUrl nUrl = new NormalizedUrl();
				nUrl.UrlNormalization(u);
				normalizedUrlList.add(nUrl);
			}
			
			//print them out
			for(NormalizedUrl Nurl : normalizedUrlList) {
				Nurl.print();
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
    	
    }
}
