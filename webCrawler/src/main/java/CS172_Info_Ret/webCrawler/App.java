package CS172_Info_Ret.webCrawler;

public class App 
{
    public static void main( String[] args ) 
    {
    	if (args.length < 4) {
    		System.out.println("Too few arguments.\n" +
					"Usage:\n"+
					"1. Number of hops\n" +
					"2. Number of pages\n" +
					"3. Location to store crawled data\n" +
					"4. Location of the seeds");
    	}
    	else {
	    	Crawler crawler = new Crawler();
	    	crawler.crawl(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], args[3]);
    	}
    }
}
