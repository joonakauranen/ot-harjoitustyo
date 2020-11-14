package blockchainexperiment;

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

    public void printAllBlocks() {
        for (Block b : this.chain) {
            System.out.println(b.toString());
        }
    }

}
