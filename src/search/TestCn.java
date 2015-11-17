package search;
import java.io.File;
import java.nio.file.Paths;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.*;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class TestCn {
	public static void main(String[] args) {
		String indexPath = "indexCnTest";
		
		try {
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			//Analyzer analyzer = new SmartChineseAnalyzer();
			Analyzer analyzer = new ChAnalyser();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			IndexWriter writer = new IndexWriter(dir, iwc);
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			
			File file = new File("test_2013_1.xml");
			XmlHandler sh = new XmlHandler(writer);
			parser.parse(file, sh);
			
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
