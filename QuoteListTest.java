import org.junit.*;
import static org.junit.Assert.*;

public class QuoteListTest
{
   // Search constants
   /* package */ static final int SearchAuthorVal = 0;
   /* package */ static final int SearchTextVal   = 1;
   /* package */ static final int SearchBothVal   = 2;

    private quotes.QuoteList testList;
    private quotes.QuoteList returnList;
    private quotes.Quote tmpQuote;
    @Before
    public void initialize()
    {
        testList = new quotes.QuoteList();
        tmpQuote = new quotes.Quote();
    }

    @After public void deinitialize()
    {
        // garbage collect objects
        testList = null;
        tmpQuote = null;
    }

    @Test public void testSearchEmptyList()
    {
        returnList = testList.search("Nixon", SearchAuthorVal);
        assertTrue("Author search of empty list", returnList.getSize() == 0);
        returnList = testList.search("Nixon", SearchTextVal);
        assertTrue("Quote text search of empty list", returnList.getSize() == 0);
        returnList = testList.search("Nixon", SearchBothVal);
        assertTrue("Both search of empty list", returnList.getSize() == 0);

    }

    @Test public void testSearchEmptyString() throws Exception
    {
        tmpQuote.setAuthor("Nixon");
        tmpQuote.setQuoteText("I am not a crook");
        testList.setQuote(tmpQuote);
        tmpQuote.setAuthor("Armstrong");
        tmpQuote.setQuoteText("That's one small step for man, one giant leap for mankind");
        testList.setQuote(tmpQuote);
        returnList = testList.search("", SearchAuthorVal);
        assertTrue("Author search with empty string", returnList.getSize() == 2);
        returnList = testList.search("", SearchTextVal);
        assertTrue("Quote text search with empty string", returnList.getSize() == 2);
        returnList = testList.search("", SearchBothVal);
        assertTrue("Both search with empty string", returnList.getSize() == 2);
    }

    @Test public void testSearchAuthorFound()
    {
        tmpQuote.setAuthor("Nixon");
        tmpQuote.setQuoteText("I am not a crook");
        testList.setQuote(tmpQuote);
        returnList = testList.search("Nixon", SearchAuthorVal);
        assertTrue("Author search found", returnList.getSize() == 1);
        tmpQuote = returnList.getQuote(0);
        assertTrue("Author search found, wrong quote returned", tmpQuote.getAuthor().equals("Nixon"));
        assertTrue("Author search found, wrong quote returned", tmpQuote.getQuoteText().equals("I am not a crook"));
    }

    @Test public void testSearchAuthorNotFound()
    {
        tmpQuote.setAuthor("Nixon");
        tmpQuote.setQuoteText("I am not a crook");
        testList.setQuote(tmpQuote);
        returnList = testList.search("Jefferson", SearchAuthorVal);
        assertTrue("Author search not found", returnList.getSize() == 0);
    }

    @Test public void testSearchQuoteFound()
    {
        tmpQuote.setAuthor("Nixon");
        tmpQuote.setQuoteText("I am not a crook");
        testList.setQuote(tmpQuote);
        returnList = testList.search("I am not a crook", SearchTextVal);
        assertTrue("Quote text search found", returnList.getSize() == 1);
        tmpQuote = returnList.getQuote(0);
        assertTrue("Quote text search found, wrong quote returned", tmpQuote.getAuthor().equals("Nixon"));
        assertTrue("Quote text search found, wrong quote returned", tmpQuote.getQuoteText().equals("I am not a crook"));
    }

    @Test public void testSearchQuoteNotFound()
    {
        tmpQuote.setAuthor("Nixon");
        tmpQuote.setQuoteText("I am not a crook");
        testList.setQuote(tmpQuote);
        returnList = testList.search("We have nothing to fear but fear itself", SearchTextVal);
        assertTrue("Quote text search not found", returnList.getSize() == 0);
    }

    @Test public void testSearchBothFound()
    {
        tmpQuote.setAuthor("Nixon");
        tmpQuote.setQuoteText("I am not a crook");
        testList.setQuote(tmpQuote);
        returnList = testList.search("I am not a crook", SearchBothVal);
        assertTrue("Quote text search found", returnList.getSize() == 1);
        tmpQuote = returnList.getQuote(0);
        assertTrue("Both search found, wrong quote returned", tmpQuote.getAuthor().equals("Nixon"));
        assertTrue("Both search found, wrong quote returned", tmpQuote.getQuoteText().equals("I am not a crook"));
        returnList = testList.search("Nixon", SearchBothVal);
        assertTrue("Quote text search found", returnList.getSize() == 1);
        tmpQuote = returnList.getQuote(0);
        assertTrue("Both search found, wrong quote returned", tmpQuote.getAuthor().equals("Nixon"));
        assertTrue("Both search found, wrong quote returned", tmpQuote.getQuoteText().equals("I am not a crook"));
    }

    @Test public void testSearchBothNotFound()
    {
        tmpQuote.setAuthor("Nixon");
        tmpQuote.setQuoteText("I am not a crook");
        testList.setQuote(tmpQuote);
        returnList = testList.search("We have nothing to fear but fear itself", SearchBothVal);
        assertTrue("Both search not found", returnList.getSize() == 0);
    }
}
