package fi.koululainenjoona.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class contains the functionality for managing the data in this app (the blocks) as it relates to the larger structure of the app (the chain)
 */
public class Chain {

    private final List<Block> chain;

    /**
     * Constructor creates an ArrayList for storing the blocks
     */
    public Chain() {
        this.chain = new ArrayList<>();
    }

    /**
     * Adds a Block object into the ArrayList
     * @param block
     */
    public void writeOnChain(Block block) {
        this.chain.add(block);
    }

    /**
     * This function gets the second last element from the chain.
     * @return Block
     */
    public Block getPreviousBlock() {
        return this.chain.get(chain.size() - 1);
    }

    /**
     * Gets n:th element from the chain. N is determined by the parameter index
     * @param index
     * @return Block
     */
    public Block getBlock(int index) {
        return this.chain.get(index);
    }

    public List<Block> getChain() {
        return chain;
    }

    /**
     * Simply returns the size of the ArrayList called chain
     * @return int
     */
    public int getChainSize() {
        return this.chain.size();
    }

    /**
     * This method recalculates all the hashes for each block. The hash is calculated
     * based on the data that is stored in the block, if the data has been changed
     * the hash as it was calculated when the block was first mined won't match with
     * the hash that this function calculated. This mismatch means the original entry
     * has been manipulated and the chain is not valid.
     */
    public void checkValidity() {
        Block currentBlock;
        Block previousBlock;

        System.out.println("");

        for (int i = 1; i < this.chain.size(); i++) {
            currentBlock = this.chain.get(i);
            previousBlock = this.chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.createHash())) {
                System.out.println("Not valid");
            }

            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                System.out.println("Not valid");
            }

        }
        System.out.println("Valid");
    }

}
