import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.stream.XMLStreamException;
import quotes.*;


/**
 * @author William Tiffany
 *         Date: Feb, 2018
 *         Adapted from quoteserve.java, written by
 *         Jeff Offutt & Mongkoldech Rajapakdee
 *         Date: Nov, 2009
 *
 * Run with: java QuoteCLI
 *
 * Wiring the pieces together:
 *    QuoteCLI.java -- The CLI frontend (entry point)
 *    QuoteList.java -- A list of quotes, representing what's read from the XML file
 *                      Used by quoteserve.java
 *    Quote.java -- A simple Quote bean; two entries, author and quote-text
 *                      Used by QuoteList.java
 *    QuoteSaxHandler.java -- Callback methods for the parser, populates QuoteList
 *                      Used by QuoteSaxParser
 *    QuoteSaxParser.java -- Parses the XML file
 *                      Used by quoteserve.java
 *    quotes.xml -- Data file, read by QuoteSaxParser
 */
public class QuoteCLI 
{

   // Data file
   private static final String quoteFileName = "quotes.xml";
   // Search history size
   private static final int SEARCH_HISTORY_SIZE = 5;
   // Stores all the quotes from the xml file
   private static QuoteList quoteList;
   private static Scanner in;
   private static ArrayList<String> searchList;

// Main entry point
public static void main(String[] args)
{
   QuoteSaxParser qParser = new QuoteSaxParser (quoteFileName);
   quoteList = qParser.getQuoteList();
   searchList = new ArrayList<String>();
   in = new Scanner(System.in);
   int searchScopeInt = Constants.SearchBothVal; // Default
   String buffer = "";
   // Initial output
   System.out.println("The GMU Quote Generator");
   Quote quoteTmp = quoteList.getRandomQuote();
   printQuote(quoteTmp);
   int selection = 0;
   boolean flag = true, modified = false;
   // Main loop
   while(flag){
      printMenu();
      buffer = in.nextLine();
      try{
         selection = Integer.parseInt(buffer);
      }catch(NumberFormatException e){
         // the default case prints an error prompt
         selection = 0;
      }
      switch(selection){
         // 1: random quote 2: search 3: search history 4: quit
         case 1:
            quoteTmp = quoteList.getRandomQuote();
            System.out.println("");
            printQuote(quoteTmp);
            break;
         case 2:
            search();
            break;
         case 3:
            printSearchHistory(searchList);
            break;
         case 4:
            // add new quote
            modified = addQuote();
            break;
         case 5:
            System.out.println("Exiting");
            flag = false;
            break;
         default:
            System.out.println("Please enter a number from 1-5");
      }
   }
   if(modified)
      writeQuoteFile();
}

// Prints a single quote
private static void printQuote(Quote quote)
{
   System.out.println('"' + quote.getQuoteText() + '"' + "\n  --" + quote.getAuthor());
   String[] keywords = quote.getKeywords();
   if(keywords.length > 0)
   {
      System.out.print("Keywords:");
      boolean first = true;
      for(String keyword : keywords)
      {
         if(first)
         {
            System.out.print(" " + keyword);
            first = false;
         }
         else
            System.out.print(", " + keyword);
      }
      System.out.print("\n");
   }
}

// Prints a simple menu for the user to select from
private static void printMenu()
{
   System.out.print("1: Get another random quote\n2: Search\n"
         +"3: See search history\n4: Add new quote\n"
         +"5: Quit\n> ");
}

private static void search()
{
   System.out.print("Select scope of search:\n1: quote\n"
         + "2: author\n3: both text and author\n"
         + "4: keyword\n5: all categories\n> ");
   String buffer = in.nextLine();
   int selection = 0, searchScopeInt = 0;
   try{
      selection = Integer.parseInt(buffer);
   }catch(NumberFormatException e){
      System.out.println("Invalid entry, using both");
      selection = 0;
   }
   switch(selection){
      // 1: quote body 2: author 3: both 4: keyword 5: all
      case 1:
         searchScopeInt = Constants.SearchTextVal;
         break;
      case 2:
         searchScopeInt = Constants.SearchAuthorVal;
         break;
      case 3:
         searchScopeInt = Constants.SearchBothVal;
         break;
      case 4:
         searchScopeInt = Constants.SearchKeywordVal;
         break;
      case 5:
      default:
         searchScopeInt = Constants.SearchAllVal;
   }
   System.out.print("Enter search query: ");
   String searchText = in.nextLine();
   searchList.add(searchText);
   // limit search history size
   if(searchList.size() > SEARCH_HISTORY_SIZE)
      searchList.remove(0);
   System.out.print("\n");
   searchQuote(searchText, searchScopeInt);
}
private static boolean addQuote()
{
   System.out.print("Quotation: ");
   String buffer = in.nextLine();
   if(!valid(buffer)){
      System.out.println("Invalid quotation, do not include XML escape sequences");
      return false;
   }
   Quote quoteTmp = new Quote();
   quoteTmp.setQuoteText(buffer);
   System.out.print("Author: ");
   buffer = in.nextLine();
   if(!valid(buffer)){
      System.out.println("Invalid author, do not include XML escape sequences");
      quoteTmp = null;
      return false;
   }
   quoteTmp.setAuthor(buffer);
   // prompt to add keywords
   System.out.print("Keyword for quote (blank to exit): ");
   buffer = in.nextLine();
   while(!buffer.isEmpty())
   {
      if(!valid(buffer)){
         System.out.println("Invalid keyword, do not include XML escape sequences");
         quoteTmp = null;
         return false;
      }
      quoteTmp.addKeyword(buffer);
      System.out.print("Keyword for quote (blank to exit): ");
      buffer = in.nextLine();
   }
   printQuote(quoteTmp);
   int selection = 0;
   while(!(selection == 1 || selection == 2)){
      System.out.print("add quote to list?\n1: yes\n2: no\n> ");
      buffer = in.nextLine();
      try{
         selection = Integer.parseInt(buffer);
      }catch(NumberFormatException e){
         System.out.println("Invalid entry, press 1 or 2");
         selection = 0;
      }
   }
   System.out.println(selection);
   if(selection == 1){
      quoteList.setQuote(quoteTmp);
      return true;
   }
   return false;
}

// Print all searches in searchList (should be <= 5 total)
private static void printSearchHistory(ArrayList<String> searchList)
{
   String searchTmp = "";
   System.out.println("\nSearch history:");
   for(int i = 0; i < searchList.size(); i++)
   {
      searchTmp = searchList.get(i);
      System.out.println(i+1 + ". " + searchTmp);
   }
   System.out.println("");
}

// search for and print quotes
private static void searchQuote(String query, int scope)
{
   if (query != null && !query.equals(""))
   {  // Received a search request
      QuoteList searchRes = quoteList.search (query, scope);
      Quote quoteTmp;
      if (searchRes.getSize() == 0)
      {
         System.out.println("Your search \"" + query + "\" did not match any quotes");
      }
      else
      {
         for (int i = 0; i < searchRes.getSize() ; i++)
         {
            quoteTmp = searchRes.getQuote(i);
            printQuote(quoteTmp);
         }
      }
   }
}

// validate quotation. Searches the string for xml escape sequences
// and returns false if it finds any. If no escape sequences are
// found, returns true
private static boolean valid(String quotation)
{
   if(quotation.indexOf("&amp;") != -1)
      return false;
   if(quotation.indexOf("&lt;") != -1)
      return false;
   if(quotation.indexOf("&gt;") != -1)
      return false;
   if(quotation.indexOf("&quot;") != -1)
      return false;
   if(quotation.indexOf("&apos;") != -1)
      return false;
   return true;
}

// write quoteLlst out to XML file
private static void writeQuoteFile()
{
   QuoteXMLWriter writer = new QuoteXMLWriter(quoteFileName);
   try{
      writer.writeFile(quoteList);
      writer.closeFile();
   }catch(XMLStreamException e){
      System.out.println("Error writing XML file");
   }catch(IOException e){
      System.out.println("Error writing XML file");
   }
}
} // end QuoteCLI class
