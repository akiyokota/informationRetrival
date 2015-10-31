package CS172_Info_Ret.webCrawler.Objects;

import java.util.HashMap;
import java.util.Map;

public class robot {
	// crawler name
	private String UserAgent;
	
	private Map<String, META> metaContents;
	// the craw-delay
	private int Crawl_Delay;
	
	public robot () {
		this.UserAgent= "";
		this.metaContents = new HashMap<String, META> ();
		this.Crawl_Delay = 0;
	}

	public Map<String, META> getMetaContents() {
		return metaContents;
	}

	public void setMetaContents(Map<String, META> metaContents) {
		this.metaContents = metaContents;
	}

	public int getCrawl_Delay() {
		return Crawl_Delay;
	}

	public void setCrawl_Delay(int crawl_Delay) {
		Crawl_Delay = crawl_Delay;
	}

	public String getUserAgent() {
		return UserAgent;
	}

	public void setUserAgent(String userAgent) {
		UserAgent = userAgent;
	}
	
}
