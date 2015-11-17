package search;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;


public class ChAnalyser extends Analyzer{
	
	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		// TODO Auto-generated method stub
		Tokenizer tokenizer = new ChTokenizer();
		//TokenStream filter = new LowerCaseFilter(tokenizer);
		return new TokenStreamComponents(tokenizer);
	}
	
}
