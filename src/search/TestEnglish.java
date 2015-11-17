package search;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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


public class TestEnglish {
	public static void main(String[] args) {
		String indexPath = "index";
		
		try {
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			//Analyzer analyzer = new StandardAnalyzer();
			Analyzer analyzer = new Analyzer() {
				@Override
				protected TokenStreamComponents createComponents(String arg0) {
					// TODO Auto-generated method stub
					Tokenizer tokenizer = new Tokenizer() {
						protected CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);
						@Override
						public boolean incrementToken() throws IOException {
							// TODO Auto-generated method stub
							charTermAttribute.setEmpty();
							clearAttributes();
							int current = input.read();
							while (current != -1 && !Character.isLetter(current))
								current = input.read();
							if (current == -1)
								return false;
							char c = (char) current;
							System.out.println(c);
							if (c == 'e') {
								charTermAttribute.append("T");
								return true;
							}
							charTermAttribute.append("F");
							return true;
						}
					};
					//TokenFilter filter = new LowerCaseFilter(tokenizer);
					return new TokenStreamComponents(tokenizer);
				}
			};
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			IndexWriter writer = new IndexWriter(dir, iwc);
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			
			File file = new File("WEB-INF/src/test.xml");
			XmlHandler sh = new XmlHandler(writer);
			parser.parse(file, sh);
			
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
