package search;

import kevin.zhang.*;

public class TestICT {
	public static void main(String[] args) {
		try {
			NLPIR nlpir = new NLPIR();
			if (NLPIR.NLPIR_Init("/home/yaokai/Work/LearnJava/Search Engine/".getBytes(), 1, "1".getBytes("utf-8")) == false) {
				System.out.println("Something wrong");
			}
			
			String input = "我的魔法会把你撕成碎片，\n你好旅行者\n抱歉，你选择死亡";
			byte[] output = nlpir.NLPIR_ParagraphProcess(input.getBytes("gbk"), 0);
			System.out.println(new String(output, 0, output.length));
			
			
			//String inString = "我的魔法会把你撕成碎片，\n你好旅行者\n抱歉，你选择死亡";
			
//			String inString = "\n\n\n";
//			Reader input = new StringReader(inString);
//			
//			Tokenizer tokenizer = new ChTokenizer();
//			tokenizer.setReader(input);
//			tokenizer.reset();
//			int i = 0;
//			while (tokenizer.incrementToken()) {
//				String result = tokenizer.reflectAsString(false);
//				System.out.println(result);
//			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
