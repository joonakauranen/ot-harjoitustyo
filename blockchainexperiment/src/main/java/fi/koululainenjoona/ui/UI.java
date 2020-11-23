package fi.koululainenjoona.ui;

import fi.koululainenjoona.logic.Chain;
import fi.koululainenjoona.logic.Block;
import java.util.Scanner;

public class UI {

    private Scanner scanner;
    private Chain chain;

    public UI(Chain chain, Scanner scanner) {
        this.scanner = scanner;
        this.chain = chain;
    }

    public void run() {
        
        this.chain.writeOnChain(new Block("This automatically created block is the beginning of the blockchain", "0"));

        while (true) {

            System.out.println("Press '1' to create a new block");
            System.out.println("Press '2' to print out all blocks");
            System.out.print(">");

            String command = scanner.nextLine();

            if (Integer.valueOf(command) == 1) {
                System.out.println("Type a message to save on the chain and press ENTER: ");
                System.out.print(">");
                String dataToInsert = scanner.nextLine();
                Block blockToAdd = new Block(dataToInsert, chain.getPreviousBlock().getHash());
                this.chain.writeOnChain(blockToAdd);
            }

            if (Integer.valueOf(command) == 2) {
                chain.printAllBlocks();
            }

        }
    }
}
