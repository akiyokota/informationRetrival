package CS172_Info_Ret.webCrawler.Objects;

public class Pair {
    private String url = "";
    private Integer depth = 0;

    public Pair(String x, Integer y)
    {
        this.url = x;
        this.depth = y;
    }

    public Pair()
    {

    }

    public String getURL() {
        return this.url;
    }

    public void setURL(String x) {
        this.url = x;
    }

    public Integer getDepth() {
        return this.depth;
    }

    public void setDepth(Integer y) {
        this.depth = y;
    }


}
