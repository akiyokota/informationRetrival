package CS172_Info_Ret.webCrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import CS172_Info_Ret.webCrawler.Objects.NormalizedUrl;

public class Crawler {
	
	private List <NormalizedUrl> normalizedUrlList;
	private Document doc;
	
	/**
	 * Constructor
	 */
	public Crawler (String url) {
		try {
			this.normalizedUrlList = new LinkedList<NormalizedUrl> ();
			this.doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse out all links from the webpage, and put these links into NormalizedUrl object
	 */
	public void linkExtraction () {
		try {
			List<URL> urlList = new LinkedList<URL> ();
			urlList = new ParseHTML().parseHTML(doc);
			
			for (URL u : urlList) {
				NormalizedUrl nUrl = new NormalizedUrl();
				nUrl.UrlNormalization(u);
				this.normalizedUrlList.add(nUrl);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Download a webcontent the primative way
	 * @param path : the path of the folder
	 * @param fileName : name of the file that saves all the web content
	 * @return url : the url you wish to download
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

	public List<NormalizedUrl> getNormalizedUrlList() {
		return normalizedUrlList;
	}



	public void setNormalizedUrlList(List<NormalizedUrl> normalizedUrlList) {
		this.normalizedUrlList = normalizedUrlList;
	}
}
