package fi.koululainenjoona.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class contains the functionality for managing the data in this app (the
 * blocks) as it relates to the larger structure of the app (the chain)
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
     *
     * @param block block to be added to the chain
     */
    public void writeOnChain(Block block) {
        this.chain.add(block);
    }

    /**
     * This function gets the second last element from the chain.
     *
     * @return Block second last block object from the chain
     */
    public Block getPreviousBlock() {
        return this.chain.get(chain.size() - 1);
    }

    /**
     * Gets n:th element from the chain. N is determined by the parameter index
     *
     * @param index n:th index to return
     * @return Block return the n:th block from the chain
     */
    public Block getBlock(int index) {
        return this.chain.get(index);
    }

    public List<Block> getChain() {
        return chain;
    }

    /**
     * Simply returns the size of the ArrayList called chain
     *
     * @return int amount of blocks in the chain
     */
    public int getChainSize() {
        return this.chain.size();
    }

    /**
     * This method recalculates a hash for each of the blocks.The hash is
     * calculated based on the data that is stored in the block, if the data has
     * been changed the hash as it was calculated when the block was first mined
     * won't match with the hash that this function calculated. This mismatch
     * means the original entry has been manipulated and the chain is not valid.
     *
     * @return Boolean true if the chain is valid, false if not
     */
    public boolean checkValidity() {
        Block currentBlock;
        Block previousBlock;
        String toMatch = new String(new char[4]).replace('\0', '0');

        for (int i = 1; i < this.chain.size(); i++) {
            currentBlock = this.chain.get(i);
            previousBlock = this.chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.createHash())) {
                return false;
            }

            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                return false;
            }

            if (!currentBlock.getHash().substring(0, 4).equals(toMatch)) {
                return false;
            }

        }
        return true;
    }

}
