# Architecture 


## Package Structure

The following diagram illustrates the package structure of the application:

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/packagediagram.png)

### ui

The package called ![ui](https://github.com/joonakauranen/ot-harjoitustyo/tree/master/blockchainexperiment/src/main/java/fi/koululainenjoona/ui) contains a command-line based user interface. The ui consists of a single class _UI_.

### logic

Package ![logic](https://github.com/joonakauranen/ot-harjoitustyo/tree/master/blockchainexperiment/src/main/java/fi/koululainenjoona/logic) contains the classes that form the core functionality of the program. It contains two classes; _Block_ and _Chain_.

### googlesheets

The package ![googlesheets](https://github.com/joonakauranen/ot-harjoitustyo/tree/master/blockchainexperiment/src/main/java/fi/koululainenjoona/googlesheets) connects the application to Google's spreadsheet program Google Sheets. It consists of two classes. _SheetsChain_ and _SheetsService_.



## Class Structure

The following diagram illustrates the class structure of the application:

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/classdiagram(1).png)

### UI

![UI](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/main/java/fi/koululainenjoona/ui/UI.java) class presents all the functionality that is available to the user. The basic functionality of the UI is presented as a simple interface that prompts the user for a command and executes parts of the program accordingly.

The UI only presents the options the user has opted in to use.

The UI is isolated from the rest of the software's logic. Logic wise the sole thing, in addition to making calls to the logic part of the software and printing out messages, it does is handling the calls to a toString() method of certain Objects.

### Block

As the name suggests ![Block](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/main/java/fi/koululainenjoona/logic/Block.java)s are the building blocks of this program. The main functionality in this class has to do with saving and securing the data this application handles. The most important methods; applyHashFuntion() and mineBlock() are explained shortly in the following paragraph: 

Blocks contain messages and a cryptographic fingerprint is created based on this message, a timestamp and the previous blocks hash. The method applyHashFunction() creates the hash by using the SHA3-256 hashing function. A computation task is added by the mineBlock(), in a real world implementation participating in the system as a miner would be rewarded and completing this task would be difficult enough to create proper game theoretical incentives. In this implementation, for demonstrative purposes, the task is easy enough for it to be solved in a couple of seconds on a single device.

### Chain

[Chain](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/main/java/fi/koululainenjoona/logic/Chain.java) stores the individual blocks and combines them together into an immutable chain of entries. When a new block is added the hash of a previous block is used in creating a new hash for the latest entry. This serves the purpose of connecting the blocks to each other. The method checkValidity() makes sure that none of the data that has been stored on the chain has been tampered with. This is done by recalculating all the hashes for all of the blocks.

### SheetsService

[SheetsService](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/main/java/fi/koululainenjoona/googlesheets/SheetsService.java) connects this program to Google's spreadsheet program Google Sheets. SheetsService class authorizes the user the access to the Google API and the standard sheet used by this application.

The user can choose to use the application without Google Sheets.

### SheetsChain

[SheetsChain](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/blockchainexperiment/src/main/java/fi/koululainenjoona/googlesheets/SheetsChain.java) reads, writes, clears and verifies the entries made to a Google Sheets spreadsheet. SheetsChain interacts with classes Block and Chain, so the same Blocks that are written to the local copy of the chain are written to a spreadsheet.

The method checkSheetsChainValidity() compares the local copy to the publicly editable spreadsheet. Modifying the spreadsheet from outside of the application (ie. without mining the blocks and without connecting the new entry to the previous ones) is noticed by this validity checking function. When this method is called the application will tell the user what data was tampered with, clear the cells and rewrite the sheet according to the cryptographically verified version.



## Main functionality

Sequence diagrams illustrating the main functionality of the application.

### Creating a new block

A new block is created when the user types _1_ in the command-line UI. The following sequence diagram represents the logic in a case where a user has selected to use Google Sheets. 

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/createNewBlock.png)

The variable _dataToAdd_ contains the message a user wants to save. Methods getPreviousBlock() and getHash() are used to get the hash of the previous Block. That hash is then used in calculating a new hash for a new Block. Then the newly created Block is returned to the UI. UI proceeds to mine the Block. The mineBlock() method computes a hash that's identical to the original hash except that it starts with four zeros. This has replaces the Blocks original hash. Now the block is ready to added to the Chain, writeOnChain() is called.

Next an if-statement checks if the variable useSheets is set to true (meaning should Google Sheets be used). In this case it is set to true and the latest Block will be written to a spreasheet. Method appendToSheet() in GoogleSheets class is called. Similarly to writing on Chain, the users message (data variable) and the Blocks hash are needed. They are fetched from the Block that was given as a parameter. Google API's execute() method is set to write the values starting from after the last entry and the entry is then made.

### Checking the Chain's validity

A user can verify the validity of the locally stored Chain by choosing _3_ in the command-line UI. The following sequence diagram represents a single iteration of a for loop that goes through all of the Blocks stored in a Chain:

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/checkValidity.png)

The method checkValidity() will return true if the Chain is valid and false if the Chain is not valid. The if statements inside the method will return false if a mismatch is found and the execution of the method will be stopped. In the above sequence diagram everything matches and the method returns true.

The actual checks are done by first taking the hash that was created using the original message the user has saved and then comparing creating a hash based on the message as it is at the time of running the method. Then a comparison is made between the two variables that both store the previous Block's hash, those being the variable _hash_ of the second last Block in the Chain and latest Blocks variable _previousHash_. If no data has been tampered with these two hashes will be identical.

Finally the last if statement makes sure the Block has been adequately mined, ie it's hash starts with four zeros.

