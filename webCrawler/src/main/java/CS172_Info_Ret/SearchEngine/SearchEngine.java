package CS172_Info_Ret.SearchEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.lucene.index.IndexWriter;
import org.jsoup.nodes.Document;

import CS172_Info_Ret.SearchEngine.Indexer.HTMLparser;
import CS172_Info_Ret.SearchEngine.Indexer.Indexer;
import CS172_Info_Ret.SearchEngine.Searcher.searcher;

public class SearchEngine {
	
	public static final Indexer indexer= new Indexer();
	public static final searcher searcher = new searcher();
	public static final HTMLparser htmlParser = new HTMLparser();
	
	private static final String page_loc = "./pages/";
	private static final String mapper_loc = "./history/mapping.txt";
	
	private Map<String, String> mapping;
	
	private Map<String, String> pageToUrlMapping() {		
		//split by new line
		StringTokenizer lines = new StringTokenizer(indexer.utility.readFile(mapper_loc), "\n");
		while(lines.hasMoreElements()) {
			StringTokenizer line = new StringTokenizer (lines.nextToken(), ":");
			
			String url = "";
			String loc = "";
			if(line.hasMoreElements()) {
				url = line.nextToken() + ":" + line.nextToken();
			}
			if(line.hasMoreElements()) {
				loc =  line.nextToken();
			}
			if(!url.equals("") && !loc.equals("")) {
				this.mapping.put(loc, url);
			}
		}
		return mapping;
	}
	
	private void printParsedData(String url, String title, String meta, String header, String text, String anchorText) {
		System.out.println("URL:\n" + url);
		System.out.println("title:\n" + title);
		System.out.println("meta:\n" + meta);
		System.out.println("header:\n" + header);
		System.out.println("text:\n" + text);
		System.out.println("anchorText:\n" + anchorText);
	}
	
	private void indexAllPages() {
		try {
//			String pg = "./pages//9037.html";
//			Document doc = htmlParser.parseDoc(pg);
//			IndexWriter w = indexer.getIndexWriter();
//			if(w!=null) {
//				String url = mapping.get(pg);
//				String title = htmlParser.parseDocTitle(doc);
//				String metadata = htmlParser.parseDocMeta(doc);
//				String header = htmlParser.parseDocHeader(doc);
//				String plainText = htmlParser.parseDocText(doc);
//				String anchorText = htmlParser.parseDocAnchorText(doc);
//				printParsedData(url, title, metadata, header, plainText, anchorText);
//				indexer.indexDoc(w, url, title, metadata, header, plainText, anchorText);
//				w.close();
//			}
			
			for (String pageLoc : mapping.keySet()) {
				if(indexer.utility.fileExists(pageLoc)) {
					Document doc = htmlParser.parseDoc(pageLoc);
					IndexWriter w = indexer.getIndexWriter();
					if(w!=null) {
						String url = mapping.get(pageLoc);
						String title = htmlParser.parseDocTitle(doc);
						String metadata = htmlParser.parseDocMeta(doc);
						String header = htmlParser.parseDocHeader(doc);
						String plainText = htmlParser.parseDocText(doc);
						String anchorText = htmlParser.parseDocAnchorText(doc);
						indexer.indexDoc(w, url, title, metadata, header, plainText, anchorText);
						w.close();
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//constructor
	public SearchEngine() {
		//initialize all variables
		this.mapping = new HashMap<String, String> ();
		
		//index all files in pages
		pageToUrlMapping();		//create file to url mapping list
		indexAllPages(); 	//index every page in pages folder
	}
	
	public static void main(String[] args) {
		SearchEngine s = new SearchEngine();
	}
}
