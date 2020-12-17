# Test documentation
The program has been tested with unit-, integration- and system testing. Unit testing has been used for testing specific sections of code, integration tests test the interaction between classes and the system level tests make sure the system as a whole meets it's requirements.

## Overview

Most of the applications functionality is in or based around the [logic](https://github.com/joonakauranen/ot-harjoitustyo/tree/master/blockchainexperiment/src/main/java/fi/koululainenjoona/logic) package. Tests for the core functionality are in classes [BlockTest](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/test/java/fi/koululainenjoona/logic/BlockTest.java) and [ChainTest](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/test/java/fi/koululainenjoona/logic/ChainTest.java). These two classes contain tests for the majority of the functionality that is accessible to the user via the user interface. The user interface itself is not tested. Google Sheets integration is not tested with JUnit tests either. I decided to exclude the integration from the tests because the purpose of the application itself is to make sure the spreadsheet is written to and read from correctly. I also couldn't find a meaningful way to test the integration without basically recreating the application in the test classes.

## Test coverage

Test coverage statistics:
![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/testcoverage.png)
[README.md](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/README.md) has the instructions for running the tests and creating the test coverage report.

## System testing
System testing was done manually.
### Installation
Installation has been tested on devices with Cubbli and Ubuntu as well as on a remote desktop. Instructions for installation can be found in a separate [User Instructions](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/user_instructions.md)-file.
### Functionality
All the functionality described in the [Requirements Analysis](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/requirements_analysis.md) has been manually tested. Cases where a user inputs "incorrect" commands have been tested as well as aborting the Google authorization flow.

## Quality defects
The way the validity of the blockchain is done is not optimal.
When connecting to Google Sheets a Google service class could be used instead of always verifying with a Google account. That would make authorization easier for users and writing test for the integration easier. 

