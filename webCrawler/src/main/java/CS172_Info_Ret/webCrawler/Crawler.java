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

import CS172_Info_Ret.webCrawler.Objects.NormalizedUrl;

public class Crawler {
	
	private List <NormalizedUrl> normalizedUrlList;
	
	
	public Crawler () {
		normalizedUrlList = new LinkedList<NormalizedUrl> ();
	}

	
	
	public  String downloadPage(String path, String fileName, String url) throws IOException, MalformedURLException {
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

	}



	public List<NormalizedUrl> getNormalizedUrlList() {
		return normalizedUrlList;
	}



	public void setNormalizedUrlList(List<NormalizedUrl> normalizedUrlList) {
		this.normalizedUrlList = normalizedUrlList;
	}
}
