package CS172_Info_Ret.SearchEngine.Indexer;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLparser {
	
	public static Document parseDoc (String filePath) {
		try {
			File input = new File(filePath);
			return Jsoup.parse(input, "UTF-8", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/* Title */
	public static String parseDocTitle(Document doc) {
		return doc.title(); 
	}
	
	/* MetaData */
	public static String parseDocMeta(Document doc) {
		StringBuilder everything = new StringBuilder();
		Elements headers = doc.select("meta");
		for(Element header : headers) {
			everything.append(header.attr("name") + ":" + header.attr("content")+ " ");
		}
		return everything.toString();
	}
	
	/* Header */
	public static String parseDocHeader(Document doc) {
		StringBuilder everything = new StringBuilder();
		Elements headers = doc.select("h1, h2, h3, h4, h5, h6");
		for(Element header : headers) {
			everything.append(header.text() + " ");
		}
		return everything.toString();
	}
	
	/* Plain Text */
	public static String parseDocText(Document doc) {
		return doc.body().text(); 
	}
	
	/* Anchor Text */
	public static String parseDocAnchorText(Document doc) {
		StringBuilder everything = new StringBuilder();
		Elements headers = doc.getElementsByTag("a");
		for(Element header : headers) {
			everything.append(header.text() + " ");
		}
		return everything.toString();
	}
	
	public static List<String> StringToList (String s) {
		List<String> wordList = new LinkedList<String>();
		StringTokenizer st = new StringTokenizer(s);
		
		while(st.hasMoreTokens() ){
			wordList.add(st.nextToken());
		}
		return wordList;
	}
	
	public static void ListPrinter(List<String> list) {
		for (String s: list) {
			System.out.println(s);
		}
	}
	
	public static void textExtraction(String file) {
		ListPrinter(StringToList(parseDocText(parseDoc(file))));
	}
	
	public static void main(String[] args) {
		try {
			//textExtraction("./pages/1.html");
			System.out.println(parseDocAnchorText(parseDoc("./pages/1.html")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
