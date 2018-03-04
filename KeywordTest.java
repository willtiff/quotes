import org.junit.*;
import static org.junit.Assert.*;
//import quotes.Constants;
import quotes.*;

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
      tmpQuote.setAuthor("Nixon");
      tmpQuote.setQuoteText("I am not a crook");
      tmpQuote.addKeyword("presidents");
      tmpQuote.addKeyword("law");
      testList.setQuote(tmpQuote);
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
      returnList = testList.search("presidents", Constants.SearchKeywordVal);
      assertEquals("Keyword search found", 1, returnList.getSize());
   }

   @Test
   public void keywordListSearchNotFoundTest()
   {
      returnList = testList.search("basketball", Constants.SearchKeywordVal);
      assertEquals("Keyword search found", 0, returnList.getSize());
   }

   @Test
   public void keywordListSearchAllTest()
   {
      returnList = testList.search("presidents", Constants.SearchAllVal);
      assertEquals("Keyword search found", 1, returnList.getSize());
   }

   @Test
   public void keywordListSearchAllNotFoundTest()
   {
      returnList = testList.search("basketball", Constants.SearchAllVal);
      assertEquals("Keyword search found", 0, returnList.getSize());
   }

   @Test
   public void readWriteXMLTest()
   {
      QuoteXMLWriter out = new QuoteXMLWriter("test.xml");
      try{
         out.writeFile(testList);
         out.closeFile();
      }catch(Exception e)
      {
         fail("error writing XML file");
      }
      QuoteSaxParser qParser = new QuoteSaxParser("test.xml");
      testList = qParser.getQuoteList();
      returnList = testList.search("presidents", Constants.SearchKeywordVal);
      assertEquals("Keyword search found", 1, returnList.getSize());
   }
}
