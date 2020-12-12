# Requirements analysis

## About
 The purpose of this application is to demonstrate the basic functionality of a blockchain.
## Functionality
A command-line based UI has the following functionality, a user can:

- mine a new block and store a message in it
  - the block will be added to a chain of blocks and the message will be cryptographically secured
  - print out all the messages and their hashes

- check the validity of the chain
  - the underlying cryptography makes sure no one can modify any of the entries after they have been written to the chain

- choose to store the blocks and their messages on the internet in a Google Sheets spreadsheet
  - the mining and adding the blocks is done similarly and simultaneously with the local chain
  - authenticating with a Google account is required for using using Google Sheets

- check the validity of the online version of the chain
  - the spreadsheet is public so anyone can edit it, but to make a lasting entry the messages have to be a part of the correct chain
  - the program notices any modifications that are made without using the application's mining functionality
  - all entries/modifications made without using the required protocol are erased and corrected

