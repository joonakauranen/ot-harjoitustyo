package blockchainexperiment;

public class Block {

    private String data;

    public Block(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Block data: " + this.data;
    }

    

}
