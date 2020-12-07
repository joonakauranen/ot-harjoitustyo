
# blockchainexperiment

The purpose of this application is to demonstrate how a very basic implementation of a blockchain system works. Blocks are stored locally with the possibility of also writing on Google docs spreadsheet. The user can write a message and store it on chain. The program will calculate a hash for each entry and those messages and hashes will form a cryptographically secured chain of Blocks, a Blockchain. The user can print out the messages in the chain and check it's validity by having the hashes recalculated and compared to the original entries. Blocks can be written to a Google Sheets spreadsheet. The sheet is publicly editable. If the sheet is modified from outside of this application the chain will become corrupted and the program will notice it and do a rewrite according to the local state of the chain.

## Documentation

[Requirements analysis](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/requirements_analysis.md)

[Working hours report](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/working_hours_report.md)

[Architecture](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/architecture.md)

[User Instructions](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/user_instructions.md)

## Testing

Run tests with the following command:

mvn test

Create a test coverage report with:

mvn jacoco:report

After running the above command find the report from _target/site/jacoco/index.html_ and open in browser.

## Checkstyle

Run style checks as defined in  [checkstyle.xml](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/checkstyle.xml) with the following command:

mvn jxr:jxr checkstyle:checkstyle

To open the report navigate to _target/site/_ and open _checkstyle.html_ in your browser

## Creating an executable JAR file

Run the following command to generate the JAR file:

mvn package

Now a file named _blockchainexperiment-1.0-SNAPSHOT.jar_ can be found in the project's _target_ folder

## JavaDoc

Generate JavaDoc with the following command:

mvn javadoc:javadoc

The documentation can be found in _target/site/apidocs/index.html_

## Releases

Week five [release](https://github.com/joonakauranen/ot-harjoitustyo/releases/tag/viikko5)

Week six [release](https://github.com/joonakauranen/ot-harjoitustyo/releases/tag/week6)

