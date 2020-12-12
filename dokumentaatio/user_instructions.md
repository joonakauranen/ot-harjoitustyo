# User Instructions

## Running the application

First download the [jar file](https://github.com/joonakauranen/ot-harjoitustyo/releases/tag/week6).
Then run the command java -jar blockchainexperiment-1.0-SNAPSHOT.jar

A command-line based user interface will start running.


## Decide whether to opt in for using Google Sheets 

The first thing the UI does is it asks the user if Google Sheets should be used. Disclaimer: The app hasn't been verified by Google and is a school project so use at your own risk. If you decide to proceed to use Google Sheets you will need to log in to a Google account.
The app hasn't been verified by Google so you will need to open the _advanced_ section and click _Go to Quickstart (unsafe)_.

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/app_verification.png)

After granting the necessary rights a Google Sheets spreadsheet will open in your browser.


## Main view

Now you should see the main view in your command-line. If you chose not to use Google Sheets option 4 will be excluded from the command-line instructions.

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/ui_1.png)

For the most part the functionality is self-explanatory and the UI straightforward to use.

Worth mentioning is the ability to check and correct the data on Google Spreadsheets.


## Is the spreadsheet valid?

User has typed in _1_, written a message ("Here's some data"), a block is mined and written to a spreadsheet:

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/blockmined.png)

Now some entries are modified by wiriting directly on the spreadsheet:

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/chain_edited.png)

User runs the validity checks, option _4_:

![](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/pictures/corrected_sheet.png)

Incorrect entries are replaced with the cryptographically validated original ones.


