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

    public void printAllBlocks() {
        if (chain.isEmpty()) {
            System.out.println("You haven't generated any blocks");
        }
        for (Block b : this.chain) {
            System.out.println(b.toString());
        }
    }
    
    public Block getBlock(int indeksi) {
        return this.chain.get(indeksi);
    }

}
