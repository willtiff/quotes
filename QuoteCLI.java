package quotes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author William Tiffany
 *         Date: Feb, 2018
 *         Adapted from quoteserve.java, written by
 *         Jeff Offutt & Mongkoldech Rajapakdee
 *         Date: Nov, 2009
 *
 * Run with: java -cp ../ quotes.QuoteCLI
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

public static void main(String[] args)
{
	QuoteSaxParser qParser = new QuoteSaxParser (quoteFileName);
	quoteList = qParser.getQuoteList();
	ArrayList<String> searchList = new ArrayList<String>();
	Scanner in = new Scanner(System.in);
	int searchScopeInt = QuoteList.SearchBothVal; // Default
	String searchText = "";
	String buffer = "";
	System.out.println("The GMU Quote Generator");
	Quote quoteTmp = quoteList.getRandomQuote();
	printQuote(quoteTmp);
	int selection = 0;
	boolean flag = true;
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
				System.out.print("Select scope of search:\n1: quote\n"
						+ "2: author\n3: both\n>");
				buffer = in.nextLine();
				try{
					selection = Integer.parseInt(buffer);
				}catch(NumberFormatException e){
					System.out.println("Invalid entry, using both");
					selection = 0;
				}
				switch(selection){
					// 1: quote body 2: author 3: both
					case 1:
						searchScopeInt = QuoteList.SearchTextVal;
						break;
					case 2:
						searchScopeInt = QuoteList.SearchAuthorVal;
						break;
					default:
						searchScopeInt = QuoteList.SearchBothVal;
				}
				System.out.println("Enter search query: ");
				searchText = in.nextLine();
				searchList.add(searchText);
				// limit search history size
				if(searchList.size() > SEARCH_HISTORY_SIZE)
					searchList.remove(0);
				System.out.println("");
				searchQuote(searchText, searchScopeInt);
				break;
			case 3:
				printSearchHistory(searchList);
				break;
			case 4:
				System.out.println("Exiting");
				flag = false;
				break;
			default:
				System.out.println("Please enter a number from 1-4");
		}
	}
	
}

// Prints a single quote
private static void printQuote(Quote quote)
{
	System.out.println('"' + quote.getQuoteText() + "\n  --" + quote.getAuthor());
}

// Prints a simple menu for the user to select from
private static void printMenu()
{
	System.out.print("1: Get another random quote\n2: Search\n"
			+"3: See search history\n4: Quit\n>");
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
} // end QuoteCLI class
