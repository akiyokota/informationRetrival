package CS172_Info_Ret.webCrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import CS172_Info_Ret.webCrawler.Objects.META;
import CS172_Info_Ret.webCrawler.Objects.NormalizedUrl;
import CS172_Info_Ret.webCrawler.Objects.Pair;
import CS172_Info_Ret.webCrawler.Objects.robot;

public class Crawler {
	
	private String historyPath = "./history/urlHistory.txt";
	
	private List <NormalizedUrl> normalizedUrlList;
	private Document doc;
	private List<String> history;
	private Queue<Pair> urlQueue;
	private CrawlerUtilities utility;
	 /* 
	  * Constructor
	  */
	public Crawler () {
		this.utility = new CrawlerUtilities();
		this.normalizedUrlList = new LinkedList<NormalizedUrl> ();
		this.history = loadHistory();
		this.urlQueue = new LinkedList<Pair>();
	}


	private List<String> loadHistory () {
		List<String> urlHistory = new LinkedList<String> ();
		try {
			File history = new File(historyPath);
			if(!history.exists()) {
			    history.createNewFile();
			} else {
				urlHistory = utility.TokenizeByNewLine(utility.readFile(historyPath));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlHistory;
	}
 	
	private void recordHistory(String url) {
		history.add(url);
		utility.appendToFile(historyPath, url);
	}
	
	
	/**
	 * Parse out all links from the webpage, and put these links into NormalizedUrl object
	 */
	public List<NormalizedUrl> urlExtraction (String url) {
		List <NormalizedUrl> NormalizedList = new LinkedList<NormalizedUrl> ();
		try {
			List<String> urlList = new LinkedList<String> ();
			Document doc = Jsoup.connect(url).get();
			urlList = new ParseHTML().parseHTML(doc);
			
			for (String u : urlList) {
				NormalizedUrl nUrl = new NormalizedUrl();
				
				nUrl.UrlNormalization(new URL(u));
				NormalizedList.add(nUrl);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return NormalizedList;
	}
	
	
	/**
	 * Download a webcontent the primative way
	 * @param path : the path of the folder
	 * @param fileName : name of the file that saves all the web content
	 * @param url : the url you wish to download
	 */
	public  void downloadPage(String path, String fileName, String url) throws IOException, MalformedURLException {
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
	}
	
	private boolean validateUrl (String url) {
		try {
			URL urlObj = new URL(url);
			if (urlObj.openStream()!=null){
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Download a webcontent the primative way
	 * @param url : the url you wish to download
	 * @return content of the page
	 */
	private  String fetchPageToMemory(String url) throws IOException, MalformedURLException {
		try {
			URL urlObj = new URL(url);
			StringBuilder content = new StringBuilder();
			if (!validateUrl(url))
				return null;
			BufferedReader x = new BufferedReader (new InputStreamReader(urlObj.openConnection().getInputStream()));
			
			while(x.ready()) {
				content.append(x.readLine());
				content.append("\n");
			}
			
			x.close();
			
			return content.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean robotExists(NormalizedUrl nUrl) {
		try {
			String robotsUrl = nUrl.getProtocol() + "://" + nUrl.getHost() + "/robots.txt";
			if(validateUrl(robotsUrl)) 
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<robot> ParseRobots(NormalizedUrl nUrl) {
		List <robot> robotsList = new LinkedList<robot> ();
		try {
			String robotsUrl = nUrl.getProtocol() + "://" + nUrl.getHost() + "/robots.txt";

			String robots = fetchPageToMemory(robotsUrl);
						
			if(robots==null){
				return null;
			}
			StringTokenizer st = new StringTokenizer(robots, "\n");
			
			robot agent = null;
			
			while(st.hasMoreElements()) {
				String token = st.nextElement().toString();
				if(token.startsWith("#"))
					continue;
				
				StringTokenizer split = new StringTokenizer(token, ": ");
				String type = split.nextElement().toString();
				if(type.equals("User-agent")) {
					if(agent != null)
						robotsList.add(agent);
					agent = new robot();
					if(split.hasMoreElements())
						agent.setUserAgent(split.nextElement().toString());
				} else if(type.equals("Allow")) {
					if(split.hasMoreElements()){
						String subUrl = split.nextElement().toString();
						META metaContent = null;
						if(!agent.getMetaContents().containsKey(subUrl)) {
							metaContent = new META();
						} else {
							metaContent = agent.getMetaContents().get(subUrl);
						}
						metaContent.setFollow(true);
						agent.getMetaContents().put(subUrl, metaContent);
					}
				} else if(type.equals("Disallow")) {
					if(split.hasMoreElements()){
						String subUrl = split.nextElement().toString();
						META metaContent = null;
						if(!agent.getMetaContents().containsKey(subUrl)) {
							metaContent = new META();
						} else {
							metaContent = agent.getMetaContents().get(subUrl);
						}
						metaContent.setNofollow(true);
						agent.getMetaContents().put(subUrl, metaContent);
					}
				} else if(type.equals("Index")) {
					if(split.hasMoreElements()){
						String subUrl = split.nextElement().toString();
						META metaContent = null;
						if(!agent.getMetaContents().containsKey(subUrl)) {
							metaContent = new META();
						} else {
							metaContent = agent.getMetaContents().get(subUrl);
						}
						metaContent.setIndex(true);
						agent.getMetaContents().put(subUrl, metaContent);
					}
				} else if(type.equals("Noindex")) {
					if(split.hasMoreElements()){
						String subUrl = split.nextElement().toString();
						META metaContent = null;
						if(!agent.getMetaContents().containsKey(subUrl)) {
							metaContent = new META();
						} else {
							metaContent = agent.getMetaContents().get(subUrl);
						}
						metaContent.setNoindex(true);
						agent.getMetaContents().put(subUrl, metaContent);
					}
				} else if(type.equals("Crawl-Delay")) {
					if(split.hasMoreElements())
						agent.setCrawl_Delay(Integer.parseInt(split.nextElement().toString()));
				} else {
					
				}
			}
			
			robotsList.add(agent);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return robotsList;
	}
	
	public List<NormalizedUrl> getNormalizedUrlList() {
		return normalizedUrlList;
	}

	public void setNormalizedUrlList(List<NormalizedUrl> normalizedUrlList) {
		this.normalizedUrlList = normalizedUrlList;
	}
	
	public boolean isDup(String url) {
		for (String s : history) {
			if (s.equals(url)) 
				return true;
		}
		return false;
	}
	
	public void enqueueUrls(List<NormalizedUrl> fullList, List<robot> robots) {
		for(NormalizedUrl nUrl : fullList) {
			System.out.println(nUrl.generateCleanUrl());
		}
	}
	
	public void crawl(Integer numHops, Integer numPages, String filePathStore, String filePathSeeds) {
		try {
			//load seeds into the queue
			urlQueue = utility.loadSeeds(filePathSeeds);
			
			while(!urlQueue.isEmpty()) {
				NormalizedUrl nUrl = new NormalizedUrl ();
				
				//pop top of the queue
				Pair urlPair = urlQueue.remove();
				//check duplicates
				if(!isDup(urlPair.getURL())) {
					recordHistory(urlPair.getURL());
					//download page ---> storage
					utility.writeFile(filePathStore + history.size(), fetchPageToMemory(urlPair.getURL()));
					
					nUrl.UrlNormalization(new URL(urlPair.getURL()));
					List<robot> robots = null;
					if(robotExists(nUrl)) {
						robots = ParseRobots(nUrl);
					}
					//extract link
					List<NormalizedUrl> nUrlList = urlExtraction(urlPair.getURL());
					//valid url -> queue
					enqueueUrls(nUrlList, robots);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*--- all testing function goes here ---*/
	/*for testing*/
	private void printRobots(List<robot> robots) {
		for (robot r : robots) {

			System.out.println("Agent : " + r.getUserAgent());
			System.out.println("Craw Delay : " + r.getCrawl_Delay());
			for (String key : r.getMetaContents().keySet()) {
			    META value = r.getMetaContents().get(key);
			    System.out.println(key );
			    System.out.print("Follow: " + value.isFollow()+ "\t");
			    System.out.print("NoFollow: " + value.isNofollow()+ "\t");
			    System.out.print("Index: " + value.isIndex()+ "\t");
			    System.out.println("NoIndex: " + value.isNoindex());
			}
		}
	}
}
