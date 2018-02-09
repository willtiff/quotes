package quotes;

import java.io.*;
import javax.xml.stream.*;

/**
 * Quote XML Writer
 * @author William Tiffany
 *			  Date: Feb 2018
 */


public class QuoteXMLWriter
{
	XMLOutputFactory factory;
	XMLStreamWriter writer;
	File outfile;
	FileOutputStream outstream;

   // Node names in XML file
   private final String QuoteListElem   = "quote-list";
   private final String QuoteElem       = "quote";
   private final String QuoteAuthorElem = "author";
   private final String QuoteTextElem   = "quote-text";
	// Indentation in XML file
	private final String QuoteOuterIndent = "   ";
	private final String QuoteInnerIndent = "      ";

	public QuoteXMLWriter(String filename){
		outfile = new File(filename);
		try{
			outstream = new FileOutputStream(outfile);
		}catch(FileNotFoundException e){
			System.out.printf("Error opening %s for writing\n", filename);
		}
		factory = XMLOutputFactory.newFactory();
		try{
			writer = factory.createXMLStreamWriter(outstream);
		}catch(XMLStreamException e){
			System.out.println("Error creating XML stream writer");
		}
	}

	public void writeFile(QuoteList quotes) throws XMLStreamException{
		Quote quoteTmp;
		writer.writeStartDocument();
		writer.writeCharacters("\n");
		writer.writeStartElement(QuoteListElem);
		writer.writeCharacters("\n");
		int listSize = quotes.getSize();
		for(int i = 0; i < listSize; i++){
			quoteTmp = quotes.getQuote(i);
			writer.writeCharacters(QuoteOuterIndent);
			writer.writeStartElement(QuoteElem);
			writer.writeCharacters("\n");
			writer.writeCharacters(QuoteInnerIndent);
			writer.writeStartElement(QuoteTextElem);
			writer.writeCharacters(quoteTmp.getQuoteText());
			writer.writeEndElement();
			writer.writeCharacters("\n");
			writer.writeCharacters(QuoteInnerIndent);
			writer.writeStartElement(QuoteAuthorElem);
			writer.writeCharacters(quoteTmp.getAuthor());
			writer.writeEndElement();
			writer.writeCharacters("\n");
			writer.writeCharacters(QuoteOuterIndent);
			writer.writeEndElement();
			writer.writeCharacters("\n");
		}
		// write </quote-list>
		writer.writeEndElement();
		// end file with newline (POSIX standard)
		// http://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap03.html#tag_03_206
		writer.writeCharacters("\n");
		writer.flush();
	}

	public void closeFile() throws XMLStreamException, IOException{
		writer.close();
		outstream.close();
	}
}
