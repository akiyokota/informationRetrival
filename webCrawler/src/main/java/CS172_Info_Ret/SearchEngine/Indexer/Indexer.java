package CS172_Info_Ret.SearchEngine.Indexer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import CS172_Info_Ret.webCrawler.CrawlerUtilities;

public class Indexer {
	public static final String INDEX_DIR = "./index/";
	public static final CrawlerUtilities utility = new CrawlerUtilities();
	
	private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
		Document doc = new Document ();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		w.addDocument(doc);
	}
	
	public static void indexDoc(IndexWriter writer, String url, String title, String meta, String header, String text, String anchorText) {
		try {
			Document doc = new Document ();
			doc.add( new Field("url", url, Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add( new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
			doc.add( new Field("header", header, Field.Store.YES, Field.Index.ANALYZED));
			doc.add( new Field("body", text, Field.Store.NO, Field.Index.ANALYZED));
			doc.add( new Field("anchor", anchorText, Field.Store.YES, Field.Index.NOT_ANALYZED));
			writer.addDocument(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static IndexWriter getIndexWriter() {
		try {
			Path index_loc = Paths.get(INDEX_DIR);
			IndexWriter writer = null;
			
			StandardAnalyzer analyzer = new StandardAnalyzer();
			//Directory index = new RAMDirectory();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(FSDirectory.open( index_loc), config);
			
			return writer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void indexing() throws CorruptIndexException, IOException{
		try {
			Path index_loc = Paths.get(INDEX_DIR);
			IndexWriter writer = null;
			
			StandardAnalyzer analyzer = new StandardAnalyzer();
			//Directory index = new RAMDirectory();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(FSDirectory.open( index_loc), config);
					
			
					
			System.out.println("Indexing to directory '" + index_loc + "'...");	
			
			addDoc(writer, "Lucene in action", "193398817");
			addDoc(writer, "Managing Gigabytes", "55063554A");
						
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void indexing2() throws CorruptIndexException, IOException{
		try {
			Path index_loc = Paths.get(INDEX_DIR);
			IndexWriter writer = null;
			
			StandardAnalyzer analyzer = new StandardAnalyzer();
			//Directory index = new RAMDirectory();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(FSDirectory.open( index_loc), config);
					
			
					
			System.out.println("Indexing to directory '" + index_loc + "'...");	
			
			addDoc(writer, "Lucene for Dummies", "55320055Z");
			addDoc(writer, "The Art of Computer Science", "9900333X");
						
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws CorruptIndexException, IOException {
		indexing();
		indexing2();
		
	}
}
