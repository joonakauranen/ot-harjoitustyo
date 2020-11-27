package fi.koululainenjoona.ui;

import fi.koululainenjoona.googlesheets.SheetsChain;
import fi.koululainenjoona.logic.Chain;
import fi.koululainenjoona.logic.Block;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

public class UI {

    private Scanner scanner;
    private Chain chain;
    private SheetsChain sheets;
    private Boolean useSheets = false;

    public UI(Chain chain, Scanner scanner) {
        this.scanner = scanner;
        this.chain = chain;
    }

    public void run() throws IOException, GeneralSecurityException {

        while (true) {
            System.out.println("Do you want to save your blockchain on Google Sheets? Y/N");
            String command = String.valueOf(scanner.nextLine());
            command = command.toUpperCase();
            
            if (command.equals("Y")) {
                System.out.println("Using Google Sheets requires logging in to a Google account and granting the app a permission to see, edit, create, and delete spreadsheets in Google Drive.");
                this.sheets = new SheetsChain();
                //For test purposes
                this.sheets.clearSheet();
                useSheets = true;
                break;
            }
            
            if (!command.equals("N")) {
                System.out.println("Type 'Y' for yes and 'N' for no");
                continue;
            }

            break;
        }

        Block genesisBlock = new Block("This automatically created block is the beginning of the blockchain", "0");
        this.chain.writeOnChain(genesisBlock);
        if (useSheets == true) {
            this.sheets.writeSheet(genesisBlock);
        }

        while (true) {

            System.out.println("Press '1' to create a new block");
            System.out.println("Press '2' to print out all blocks");
            System.out.print(">");

            String command = String.valueOf(scanner.nextLine());
            command = command.toUpperCase();

            if (command.equals("1")) {
                System.out.println("Type a message to save on the chain and press ENTER: ");
                System.out.print(">");
                String dataToInsert = scanner.nextLine();
                Block blockToAdd = new Block(dataToInsert, chain.getPreviousBlock().getHash());
                this.chain.writeOnChain(blockToAdd);
                
                if (useSheets == true) {
                    this.sheets.appendToSheet(blockToAdd);
                }
                continue;

            }

            if (command.equals("2")) {
                chain.printAllBlocks();
                continue;
            }
            
            System.out.println("Invalid command: " + "'"+ command + "'");

        }
    }
}
