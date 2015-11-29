package CS172_Info_Ret.SearchEngine.Searcher;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;

public class searcher {
	public static final String INDEX_DIR = "./index/";
	
	public static Query buildQuery (String s) {
		Query q =  null;
		try {
			q = new QueryParser("title", new StandardAnalyzer()).parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return q;
	}
	
	public static ScoreDoc[] search( Query q) {
		try {
			Path index_path = Paths.get(INDEX_DIR);
			
			int hitsPerPage = 10;
			IndexReader reader = DirectoryReader.open(FSDirectory.open(index_path));
			IndexSearcher searcher = new IndexSearcher(reader);
			TopScoreDocCollector collector = 
					TopScoreDocCollector.create(hitsPerPage);
			searcher.search(q, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			
			return hits;
		} catch( Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void display( ScoreDoc[] hits) {
		try {
			Path index_path = Paths.get(INDEX_DIR);
			
			IndexReader reader = DirectoryReader.open(FSDirectory.open(index_path));
			IndexSearcher searcher = new IndexSearcher(reader);
			
			System.out.println("Found " + hits.length + " hits.");
			for (int i = 0; i < hits.length; ++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				System.out.println((i + 1) + ". " + d.get("url") );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String ask() {
		System.out.print("$ ");
		return new Scanner( System.in ).nextLine();
	}
	
	public static void main(String[] args) {
		//display(search(buildQuery(ask())));
		display(search(buildQuery(ask())));
	}
}
