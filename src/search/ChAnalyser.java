package search;
import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.util.CharArraySet;


public class ChAnalyser extends Analyzer{
	
	@Override
	protected TokenStreamComponents createComponents(String arg0){
		// TODO Auto-generated method stub
		Tokenizer tokenizer = new ChTokenizer();
		
		CharArraySet stopSet = new CharArraySet(1208, false);
		try {
			BufferedReader br = new BufferedReader(new FileReader("/home/yaokai/workspace/WebInfoLab/stopwordlist.txt"));
			String line = br.readLine();
			while (line != null) {
				stopSet.add(line);
				line = br.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TokenStream stopFilter = new StopFilter(tokenizer, stopSet);
	
		return new TokenStreamComponents(tokenizer, stopFilter);
	}
	
}
