package fi.koululainenjoona.logic;

import java.util.ArrayList;
import java.util.List;

public class Chain {

    private final List<Block> chain;

    public Chain() {
        this.chain = new ArrayList<>();
    }

    public void writeOnChain(Block block) {
        this.chain.add(block);
    }

    public Block getPreviousBlock() {
        return this.chain.get(chain.size() - 1);
    }

    public Block getBlock(int indeksi) {
        return this.chain.get(indeksi);
    }

    public List<Block> getChain() {
        return chain;
    }

    public void checkValidity() {
        Block currentBlock;
        Block previousBlock;

        for (int i = 1; i < this.chain.size(); i++) {
            currentBlock = this.chain.get(i);
            previousBlock = this.chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.createHash())) {
                System.out.println("Not valid");
            }

            if (!previousBlock.getHash().equals(previousBlock.createHash())) {
                System.out.println("Not valid");
            }

        }
        System.out.println("Valid");
    }

}
