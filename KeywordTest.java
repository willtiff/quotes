import org.junit.*;
import static org.junit.Assert.*;
import quotes.SearchValues;

public class KeywordTest
{

   private quotes.QuoteList testList;
   private quotes.QuoteList returnList;
   private quotes.Quote tmpQuote;
   @Before
   public void initialize()
   {
      testList = new quotes.QuoteList();
      tmpQuote = new quotes.Quote();
   }

   @After
   public void deinitialize()
   {
      // garbage collect objects
      testList = null;
      tmpQuote = null;
   }

   @Test
   public void keywordQuoteFoundTest()
   {
      tmpQuote.addKeyword("test");
      assertTrue("Failed to add keyword", tmpQuote.hasKeyword("test"));
   }

   @Test
   public void keywordQuoteNotFoundTest()
   {
      // tmpQuote's keyword set should be empty on initialization
      assertEquals("Keyword found erroneously", false, tmpQuote.hasKeyword("test"));
   }

   @Test
   public void keywordListSearchTest()
   {
      tmpQuote.setAuthor("Nixon");
      tmpQuote.setQuoteText("I am not a crook");
      tmpQuote.addKeyword("presidents");
      testList.setQuote(tmpQuote);
      returnList = testList.search("presidents", SearchValues.SearchKeywordVal);
      assertTrue("Keyword search found", returnList.getSize() == 1);
   }
}
