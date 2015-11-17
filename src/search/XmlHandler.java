package search;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;


public class XmlHandler extends DefaultHandler {
	
	private IndexWriter writer;
	private Document doc;
	private StringBuilder totalContent;
	private String currentContent;
	private boolean inTitle;
	private boolean inUrl;
	
	
	public XmlHandler(IndexWriter writer) {
		// TODO Auto-generated constructor stub
		this.writer = writer;
		doc = null;
		totalContent = new StringBuilder();
		currentContent = "";
		inTitle = false;
		inUrl = false;
	}
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("Start parsing:");
		
		super.startDocument();
	}
	
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("End parsing.");
		doc = null;
		totalContent = new StringBuilder();
		currentContent = "";
		inTitle = false;
		inUrl = false;
		super.endDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		
		//debug
//		//System.out.println("---Enter: " + qName);
//		for (int i = 0; i < attributes.getLength(); i++) {
//			System.out.print(attributes.getQName(i) + "=" + attributes.getValue(i));
//		}
//		System.out.println();
		
		switch (qName) {
		case "doc":
			doc = new Document();
			break;
			
		case "title":
			inTitle = true;
			break;
			
		case "url":
			inUrl = true;
			break;
			
		case "meta":
			String attName = attributes.getValue(0);
			String attContent = attributes.getValue(1);
			if (attName.equals("keywords"))
				doc.add(new TextField("keywords", attContent,Field.Store.YES));
			else if (attName.equals("description"))
				doc.add(new TextField("description", attContent, Field.Store.YES));
			else if (attName.equals("publishid"))
				doc.add(new StringField("publishid", attContent, Field.Store.YES));
			else if (attName.equals("subjectid"))
				doc.add(new StringField("subjectid", attContent, Field.Store.YES));
			else
				break;
			break;
			
		default:
			break;
		}
		
		super.startElement(uri, localName, qName, attributes);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		//System.out.println("---Exit: " + qName);
		
		switch (qName) {
		case "doc":
			doc.add(new TextField("content", totalContent.substring(1), Field.Store.YES));
			totalContent = new StringBuilder();
			try {
				writer.addDocument(doc);
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			doc = null;
			break;
		case "title":
			doc.add(new TextField("title", currentContent, Field.Store.YES));
			currentContent = "";
			inTitle = false;
			break;
		case "url":
			doc.add(new StringField("url", currentContent, Field.Store.YES));
			currentContent = "";
			inUrl = false;
			break;

		default:
			break;
		}
		
		super.endElement(uri, localName, qName);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		String content = new String(ch, start, length);
		currentContent = content;
		
		if (!inTitle && !inUrl && !currentContent.equals("\n")) {
			
			totalContent.append(currentContent);
			currentContent = "";
		}
		
		super.characters(ch, start, length);
	}
	
}
