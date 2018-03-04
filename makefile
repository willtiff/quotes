default: cli

all: QuoteCLI.class quotes tests

cli: QuoteCLI.class

QuoteCLI.class: QuoteCLI.java quotes
	javac QuoteCLI.java

quotes: quotes/Quote.class quotes/QuoteList.class quotes/QuoteSaxHandler.class quotes/QuoteSaxParser.class quotes/QuoteXMLWriter.class quotes/Constants.class

quotes/Quote.class: quotes/Quote.java
	javac quotes/Quote.java

quotes/QuoteList.class: quotes/QuoteList.java
	javac quotes/QuoteList.java

quotes/QuoteSaxHandler.class: quotes/QuoteSaxHandler.java
	javac quotes/QuoteSaxHandler.java
	
quotes/QuoteSaxParser.class: quotes/QuoteSaxParser.java
	javac quotes/QuoteSaxParser.java

quotes/QuoteXMLWriter.class: quotes/QuoteXMLWriter.java
	javac quotes/QuoteXMLWriter.java

quotes/Constants.class: quotes/Constants.java
	javac quotes/Constants.java

test: tests
tests: AllTests.class QuoteListTest.class KeywordTest.class

AllTests.class: AllTests.java
	javac -cp .:junit.jar AllTests.java

QuoteListTest.class: QuoteListTest.java
	javac -cp .:junit.jar QuoteListTest.java

KeywordTest.class: KeywordTest.java
	javac -cp .:junit.jar KeywordTest.java

run:
	java QuoteCLI

runtest: runtests
runtests:
	java -cp .:junit.jar:hamcrest-core-1.3.jar AllTests
