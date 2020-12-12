package fi.koululainenjoona.ui;

import fi.koululainenjoona.googlesheets.SheetsChain;
import fi.koululainenjoona.logic.Block;
import fi.koululainenjoona.logic.Chain;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Scanner;

/**
 * This class forms the user interface for all the other classes
 */
public class UI {

    private final Scanner scanner;
    private final Chain chain;
    private SheetsChain sheets;
    private Boolean useSheets;
    private Boolean verificationSuccesful;

    /**
     * The constructor sets a Chain object and a Scanner to be used in the UI
     *
     * @param chain
     * @param scanner
     */
    public UI(Chain chain, Scanner scanner) {
        this.scanner = scanner;
        this.chain = chain;
        this.useSheets = false;
        this.verificationSuccesful = false;
    }

    /**
     * This method starts the application, it presents the availability for all
     * the functionality accessible to the user
     *
     * @throws IOException
     * @throws GeneralSecurityException
     */
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
                    this.confrimSheetsUsage();
                    if (!this.verificationSuccesful) {
                        continue;
                    }
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

        this.createGenesisBlock();

        while (true) {

            this.printInstructions();

            String command = String.valueOf(scanner.nextLine());
            command = command.toUpperCase();

            if (command.equals("1")) {
                this.createNewBlock();
                continue;
            }

            if (command.equals("2")) {
                this.printAllBlocks();
                continue;
            }

            if (command.equals("3")) {
                if (this.chain.checkValidity()) {
                    System.out.println("\nThe local copy of the chain is valid");
                } else {
                    System.out.println("\nThe local copy of the chain is not valid");
                }
                continue;
            }

            if (command.equals("4") && this.useSheets == true) {
                System.out.println(this.sheets.checkSheetsChainValidity(this.chain));
                continue;
            }

            if (command.equals("X")) {
                break;
            }

            System.out.println("Invalid command: " + "'" + command + "'");
        }
    }

    /**
     * This method iterates through all the Block objects in this.chain and
     * calls toString() for each
     */
    public void printAllBlocks() {

        List<Block> allBlocks = this.chain.getChain();

        System.out.println("");

        for (Block b : allBlocks) {
            System.out.println(b.toString());
        }
    }

    /**
     * This method prints out the instructions for using this application
     */
    public void printInstructions() {

        System.out.println("\nPress '1' to mine a new block");
        System.out.println("Press '2' to print out all blocks");
        System.out.println("Press '3' to check chain's validity");

        if (useSheets) {
            System.out.println("Press '4' to check validity of the chain on Google Sheets");
        }

        System.out.println("Press 'x' to exit");
        System.out.print(">");
    }

    /**
     * If the user want to use Google Sheets this method prepares the
     * application for it. It creates a SheetsChain object and opens the
     * standard sheet
     *
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public void confrimSheetsUsage() throws IOException, GeneralSecurityException {
        try {
            this.sheets = new SheetsChain();
            this.sheets.clearSheet();
            this.sheets.openStandardSheetInBrowser();
            verificationSuccesful = true;
            this.useSheets = true;
        } catch (GeneralSecurityException | IOException ex) {
            System.out.println("\nVerification failed. Try again or continue without Google Sheets\n");
        }
    }

    /**
     * This method creates the first block of the chain. The hash needs to be
     * calculated based on the previous blocks hash and since this one doesn't
     * have it the previous hash is manually set to 0.
     *
     * @throws IOException
     */
    public void createGenesisBlock() throws IOException {
        Block genesisBlock = new Block("This automatically created block is the beginning of the blockchain", "0");
        this.chain.writeOnChain(genesisBlock);
        if (useSheets == true) {
            this.sheets.writeSheet(genesisBlock);
        }
    }

    /**
     * This method handles creating and adding new Blocks to the chain. If the
     * user has opt in for Google Sheets usage it writes to Google Sheets as
     * well in addition to the local chain
     *
     * @throws IOException
     */
    public void createNewBlock() throws IOException {
        System.out.println("\nType a message to save on the chain and press ENTER: ");
        System.out.print(">");
        String dataToInsert = scanner.nextLine();
        Block blockToAdd = new Block(dataToInsert, chain.getPreviousBlock().getHash());
        System.out.println("\nMining a block, this takes a few seconds");
        blockToAdd.mineBlock();
        this.chain.writeOnChain(blockToAdd);

        if (useSheets == true) {
            this.sheets.appendToSheet(blockToAdd);
        }
        System.out.println("Block succesfully mined!");
    }
}
