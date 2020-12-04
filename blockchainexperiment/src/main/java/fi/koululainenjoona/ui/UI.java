package fi.koululainenjoona.ui;

import fi.koululainenjoona.googlesheets.SheetsChain;
import fi.koululainenjoona.logic.Chain;
import fi.koululainenjoona.logic.Block;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
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
            System.out.print(">");
            String command = String.valueOf(scanner.nextLine());
            command = command.toUpperCase();

            if (command.equals("Y")) {
                System.out.println("Using Google Sheets requires logging in to a Google account and granting this app a permission to see, edit, create, and delete spreadsheets in Google Drive.");
                System.out.println("Are you sure you want to continue? 'Y' to confirm, any other key to continue without Google Sheets");
                System.out.print(">");
                String confirm = String.valueOf(this.scanner.nextLine());
                confirm = confirm.toUpperCase();
                if (confirm.equals("Y")) {
                    this.sheets = new SheetsChain();
                    //For test purposes
                    this.sheets.clearSheet();
                    this.sheets.openStandardSheetInBrowser();
                    useSheets = true;
                    break;
                }
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
            
            this.printInstructions();

            String command = String.valueOf(scanner.nextLine());
            command = command.toUpperCase();

            if (command.equals("1")) {
                System.out.println("\nType a message to save on the chain and press ENTER: ");
                System.out.print(">");
                String dataToInsert = scanner.nextLine();
                Block blockToAdd = new Block(dataToInsert, chain.getPreviousBlock().getHash());
                blockToAdd.mineBlock();
                this.chain.writeOnChain(blockToAdd);

                if (useSheets == true) {
                    this.sheets.appendToSheet(blockToAdd);
                }
                continue;

            }

            if (command.equals("2")) {
                this.printAllBlocks();
                continue;
            }

            if (command.equals("3")) {
                this.chain.checkValidity();
                continue;
            }

            if (command.equals("4")) {
                this.sheets.checkSheetsChainValidity(this.chain);
                continue;
            }

            if (command.equals("X")) {
                break;
            }

            System.out.println("Invalid command: " + "'" + command + "'");

        }
    }

    public void printAllBlocks() {

        List<Block> allBlocks = this.chain.getChain();

        System.out.println("");
        
        for (Block b : allBlocks) {
            System.out.println(b.toString());
        }
    }

    public void defaultInstructions() {
        System.out.println("\nPress '1' to create a new block");
        System.out.println("Press '2' to print out all blocks");
        System.out.println("Press '3' to check chain's validity");
        System.out.println("Press 'x' to exit");
        System.out.print(">");
    }
    
    public void printInstructions() {
        if (useSheets) {
            this.defaultInstructions();
            System.out.println("Press '4' to check validity of the chain on Google Sheets");  
        } else {
            this.defaultInstructions();
        }
    }
}
