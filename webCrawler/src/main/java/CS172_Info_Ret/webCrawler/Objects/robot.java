package CS172_Info_Ret.webCrawler.Objects;

import java.util.LinkedList;
import java.util.List;

public class robot {
	// crawler name
	private String UserAgent;
	// can-follow-path
	private List<String> AllowList;
	// don't-follow-path
	private List<String> DisallowList;
	// the craw-delay
	private int Crawl_Delay;
	
	public robot () {
		this.UserAgent= "";
		this.AllowList= new LinkedList<String> ();
		this.DisallowList = new LinkedList<String> ();
		this.Crawl_Delay = 0;
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

	public List<String> getAllowList() {
		return AllowList;
	}

	public void setAllowList(List<String> allowList) {
		AllowList = allowList;
	}

	public List<String> getDisallowList() {
		return DisallowList;
	}

	public void setDisallowList(List<String> disallowList) {
		DisallowList = disallowList;
	}
	
}
