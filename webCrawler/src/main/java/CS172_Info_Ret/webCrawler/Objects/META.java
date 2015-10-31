package CS172_Info_Ret.webCrawler.Objects;

public class META {
	private boolean noindex;
	private boolean index;
	private boolean nofollow;
	private boolean follow;
	
	public META () {
		noindex = false;
		index = false;
		nofollow = false;
		follow = false;
	}

	public boolean isNoindex() {
		return noindex;
	}

	public void setNoindex(boolean noindex) {
		this.noindex = noindex;
	}

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

	public boolean isNofollow() {
		return nofollow;
	}

	public void setNofollow(boolean nofollow) {
		this.nofollow = nofollow;
	}

	public boolean isFollow() {
		return follow;
	}

	public void setFollow(boolean follow) {
		this.follow = follow;
	}
}
