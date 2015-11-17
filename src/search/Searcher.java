package search;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
	
	private IndexReader reader;
	private IndexSearcher searcher;
	private Analyzer analyzer;
	private QueryParser qParser;
	
	private ScoreDoc lastScore;
	
	public Searcher(String indexPath) {
		// TODO Auto-generated constructor stub
		try {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
			searcher = new IndexSearcher(reader);
			analyzer = new ChAnalyser();
			qParser = new QueryParser("content", analyzer);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public LinkedList<String> search(String queryString, ScoreDoc after) throws Exception {
		Query query = qParser.parse(queryString);
		System.out.println("Searching for:" + queryString);
		//TopDocs rsDocs = searcher.search(query, 10);
		TopDocs rsDocs = searcher.searchAfter(after, query, 10);
		LinkedList<String> ret = new LinkedList<>();
		int length = rsDocs.scoreDocs.length;
		for (int i = 0; i < length; i++) {
			Document hitDoc = searcher.doc(rsDocs.scoreDocs[i].doc);
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("========================================================== Score:" + rsDocs.scoreDocs[i].score + "<br/>");
			sBuffer.append("<a href=\"" + 
					hitDoc.getField("url").stringValue() + "\">"+ 
					hitDoc.getField("title").stringValue() + "<a>" + "<br/>");
			sBuffer.append("keywords: " + hitDoc.getField("keywords").stringValue() + "<br/>");
			sBuffer.append("content: " + hitDoc.getField("content").stringValue()+ "<br/>");
			sBuffer.append("<br/>");
			ret.add(sBuffer.toString());
		}
		if (length > 0)
			lastScore = rsDocs.scoreDocs[length-1];
		return ret;
	}
	
	// must be called right after search
	public ScoreDoc getLastScore() {
		return lastScore;
	}
	
	public static void main(String[] args) {
		String indexPath = "indexCnTest";
		try {
			Searcher searcher = new Searcher(indexPath);
			searcher.search("哈利波特");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
