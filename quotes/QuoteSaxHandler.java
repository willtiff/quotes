package quotes;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

/**
 * SAX handler for SAX Parser
 * @author Mongkoldech Rajapakdee & Jeff Offutt
 *         Date: Nov 2009
 * Uses the sax parser to parse an XML file into a list of quotes
*/

public class QuoteSaxHandler extends DefaultHandler
{
   private QuoteList quoteList = new QuoteList();
   private Quote quoteTmp = null; // temporary Quote
   private String currentElement = null; // current element name

public QuoteSaxHandler()
{
   super();
}

public QuoteList getQuoteList()
{
   return quoteList;
}

@Override
public void startDocument ()
{
   // System.out.println ("Start document"); // For testing
}

@Override
public void endDocument ()
{
   // System.out.println ("End document"); // For testing
}

@Override
public void startElement (String uri, String name, String qName, Attributes atts)
{
   if (qName.equalsIgnoreCase (Constants.QuoteListElem))
   {
      currentElement = Constants.QuoteListElem;
   }
   else if (qName.equalsIgnoreCase(Constants.QuoteElem))
   {
      currentElement = Constants.QuoteElem;
      quoteTmp = new Quote();
   }
   else if (qName.equalsIgnoreCase (Constants.QuoteAuthorElem))
   {
      currentElement = Constants.QuoteAuthorElem;
   }
   else if (qName.equalsIgnoreCase (Constants.QuoteTextElem))
   {
      currentElement = Constants.QuoteTextElem;
   }
   else if (qName.equalsIgnoreCase (Constants.QuoteKeywordElem))
   {
      currentElement = Constants.QuoteKeywordElem;
   }
}

@Override
public void endElement (String uri, String name, String qName)
{
   if (qName.equalsIgnoreCase (Constants.QuoteElem))
   {
      quoteList.setQuote (quoteTmp);
      quoteTmp = null;
   }
}

@Override
public void characters (char ch[], int start, int length)
{
   String value = new String (ch, start, length);
   if (!value.trim().equals(""))
   {
      if (currentElement.equalsIgnoreCase (Constants.QuoteTextElem))
      {
         String temp = quoteTmp.getQuoteText();
         if(temp == null){
            quoteTmp.setQuoteText (value);
         }else{
            quoteTmp.setQuoteText(temp + value);
         }
      }
      else if (currentElement.equalsIgnoreCase (Constants.QuoteAuthorElem))
      {
         String temp = quoteTmp.getAuthor();
         if(temp == null){
            quoteTmp.setAuthor (value);
         }else{
            quoteTmp.setAuthor(temp + value);
         }
      }
      else if (currentElement.equalsIgnoreCase (Constants.QuoteKeywordElem))
      {
         quoteTmp.addKeyword(value);
      }
   }
}

}
