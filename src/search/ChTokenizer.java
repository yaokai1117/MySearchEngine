package search;

import kevin.zhang.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;



public class ChTokenizer extends Tokenizer{

	protected CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);
	private StringTokenizer leftWords;
	private boolean eofFlag;
	private boolean firstFlag;	
	private NLPIR nlpir;
	
	public ChTokenizer() {
		// TODO Auto-generated constructor stub
		super();
		eofFlag = false;
		
		
		try {
			nlpir = new NLPIR();
			if (NLPIR.NLPIR_Init("/home/yaokai/workspace/WebInfoLab/".getBytes(), 1, "1".getBytes("utf-8")) == false) {
				System.err.println("NLPIR init fialed!");
			}
		} catch (UnsupportedEncodingException e){
			// this cannot happen.....
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean incrementToken() throws IOException {
		// TODO Auto-generated method stub
		charTermAttribute.setEmpty();
		clearAttributes();
		
		if (leftWords == null || !leftWords.hasMoreTokens()) {
			if (eofFlag) {
				eofFlag = false;
				return false;
			}
			
			// if not at the end of input, tokenize next paragraph
			leftWords = lexNextParagraph();
		}
		
		String debug = leftWords.nextToken();
		while (debug.isEmpty() || debug.equals("，") || debug.equals("。")) {
			if (leftWords.hasMoreTokens()) {
				debug = leftWords.nextToken();
			}
			else if (eofFlag) {
				return false;
			}
			else {
				leftWords = lexNextParagraph();
			}
		}
		
		//System.out.println(debug);
			
		charTermAttribute.append(debug);
		
		return true;
	}
	
	private StringTokenizer lexNextParagraph() throws IOException {
		// read next paragraph and tokenize it
		final int limit = 5000;
		int index = 0;
		char[] buffer = new char[limit];
		
		int current = input.read();
		while (current != -1){
			char c = (char)current;

			if (c == '\n' || index == limit) 
				break;
			
			// not the end of a paragraph, continue
			buffer[index++] = c;
			current = input.read();
		}
		
		if (current == -1)
			eofFlag = true;
		
		// if a new paragraph is read in
		String paragraph = new String(buffer, 0, index);
		
		paragraph = paragraph.trim();
		if (paragraph.isEmpty())
			return new StringTokenizer("，");
		
		// use NILIR to tokenize the paragraph
		byte[] words = nlpir.NLPIR_ParagraphProcess(paragraph.getBytes("gbk"), 0);
		String wordsString = new String(words, 0, words.length-1);
		
		return new StringTokenizer(wordsString);
	}
	
	@Override
	public void reset() throws IOException {
		// TODO Auto-generated method stub
		super.reset();
	}

}
