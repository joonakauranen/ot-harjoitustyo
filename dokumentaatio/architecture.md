# Architecture 

## Package Structure

The following diagram illustrates the package structure of the application:

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/packagediagram.png)

## ui

The package called ![ui](https://github.com/joonakauranen/ot-harjoitustyo/tree/master/blockchainexperiment/src/main/java/fi/koululainenjoona/ui) contains a command-line based user interface. The ui consists of a single class _UI_.

## logic

Package ![logic](https://github.com/joonakauranen/ot-harjoitustyo/tree/master/blockchainexperiment/src/main/java/fi/koululainenjoona/logic) contains the classes that form the core functionality of the program. It contains two classes; _Block_ and _Chain_.

## googlesheets

The package ![googlesheets](https://github.com/joonakauranen/ot-harjoitustyo/tree/master/blockchainexperiment/src/main/java/fi/koululainenjoona/googlesheets) connects the application to Google's spreadsheet program Google Sheets. It consists of two classes. _SheetsChain_ and _SheetsService_.


## Class Structure

The following diagram illustrates the class structure of the application:

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/classdiagram(1).png)

## UI

![UI](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/main/java/fi/koululainenjoona/ui/UI.java) class presents all the functionality that is available to the user. The basic functionality of the UI is presented as a simple interface that prompts the user for a command and executes parts of the program accordingly.

The UI only presents the options the user has opted in to use.

The UI is isolated from the rest of the software's logic. Logic wise the sole thing, in addition to making calls to the logic part of the software and printing out messages, it does is handling the calls to a toString() method of certain Objects.

## Block

As the name suggests ![Block](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/main/java/fi/koululainenjoona/logic/Block.java)s are the building blocks of this program. The main functionality in this class has to do with saving and securing the data this application handles. The most important methods; applyHashFuntion() and mineBlock() are explained shortly in the following paragraph: 

Blocks contain messages and a cryptographic fingerprint is created based on this message, a timestamp and the previous blocks hash. The method applyHashFunction() creates the hash by using the SHA3-256 hashing function. A computation task is added by the mineBlock(), in a real world implementation participating in the system as a miner would be rewarded and completing this task would be difficult enough to create proper game theoretical incentives. In this implementation, for demonstrative purposes, the task is easy enough for it to be solved in a couple of seconds on a single device.

## Chain

[Chain](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/main/java/fi/koululainenjoona/logic/Chain.java) stores the individual blocks and combines them together into an immutable chain of entries. When a new block is added the hash of a previous block is used in creating a new hash for the latest entry. This serves the purpose of connecting the blocks to each other. The method checkValidity() makes sure all the data that has been stored has not been tampered with. This is done by recalculating all the hashes for all of the blocks.

## SheetsService

[SheetsService](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/main/java/fi/koululainenjoona/googlesheets/SheetsService.java) 

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/writeSheet_diagram.png)

