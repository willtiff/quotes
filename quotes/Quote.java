package quotes;

import java.util.HashSet;

/**
 * Quote data object.
 * @author Mongkoldech Rajapakdee & Jeff offutt
 *         Date: Nov 2009
 * A quote has two parts, an author and a quoteText.
 * This bean class provides getters and setters for both, plus a toString()
 */
public class Quote
{
   private String author;
   private String quoteText;
   private HashSet<String> keywords;

   // Default constructor does nothing
   public Quote ()
   {
      author="";
      quoteText = "";
      keywords = new HashSet<String>();
   }

   // Constructor that assigns both strings
   public Quote (String author, String quoteText)
   {
      this.author = author;
      this.quoteText = quoteText;
      this.keywords = new HashSet<String>();
   }

   // Getter and setter for author
   public String getAuthor ()
   {
      return author;
   }
   public void setAuthor (String author)
   {
      this.author = author;
   }

   // Getter and setter for quoteText
   public String getQuoteText ()
   {
      return quoteText;
   }
   public void setQuoteText (String quoteText)
   {
      this.quoteText = quoteText;
   }

   public void addKeyword (String newKeyword)
   {
      keywords.add(newKeyword);
   }
   public boolean hasKeyword (String keyword)
   {
      return keywords.contains(keyword);
   }

   @Override
   public String toString ()
   {
      return "Quote {" + "author='" + author + '\'' + ", quoteText='" + quoteText + '\'' + '}';
   }
}
