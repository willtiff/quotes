QuoteCLI.class: QuoteCLI.java
	javac QuoteCLI.java

quotes: quotes/Quote.java quotes/QuoteList.java quotes/QuoteSaxHandler.java quotes/QuoteSaxParser.java quotes/QuoteXMLWriter.java
	javac quotes/Quote.java quotes/QuoteList.java quotes/QuoteSaxHandler.java quotes/QuoteSaxParser.java quotes/QuoteXMLWriter.java

test: tests
tests:
	javac -cp .:junit.jar AllTests.java QuoteListTest.java

run:
	java QuoteCLI

runtests:
	java -cp .:junit.jar:hamcrest-core-1.3.jar AllTests
