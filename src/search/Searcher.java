package search;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.StringTokenizer;

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
		
		// get parsed query words
		StringTokenizer queryToks = new StringTokenizer(query.toString());
		LinkedList<String> queryWords = new LinkedList<>();
		while (queryToks.hasMoreTokens()) {
			String tok = queryToks.nextToken();
			queryWords.add(tok.substring(8, tok.length()));
		}
		
		// debug
		System.out.println("Searching for:" + queryString);

		TopDocs rsDocs = searcher.searchAfter(after, query, 10);
		LinkedList<String> ret = new LinkedList<>();
		int length = rsDocs.scoreDocs.length;
		for (int i = 0; i < length; i++) {
			Document hitDoc = searcher.doc(rsDocs.scoreDocs[i].doc);
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("<br/>");
			
			// handle news title
			String titleStr = hitDoc.getField("title").stringValue();
			for (String word : queryWords) {
				System.out.println(word);
				titleStr = titleStr.replaceAll(word, "<b class=\"queryword\">" + word + "</b>");
			}
			sBuffer.append("<a href=\"" + 
					hitDoc.getField("url").stringValue() + "\">"+ 
					titleStr + "<a>" + "<br/>");
			
			// handle key word
			sBuffer.append("keywords: " + hitDoc.getField("keywords").stringValue() + "<br/>");
			
			// handle news content
			String contetnStr = hitDoc.getField("content").stringValue();
			if (contetnStr.length() > 200)
				contetnStr = contetnStr.substring(0, 200);
			for (String word : queryWords) {
				System.out.println(word);
				contetnStr = contetnStr.replaceAll(word, "<b class=\"queryword\">" + word + "</b>");
			}
			sBuffer.append(contetnStr+ "<br/>");
			
			sBuffer.append("<br/>");
			sBuffer.append(hitDoc.getField("url").stringValue() + "<br/>");
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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
