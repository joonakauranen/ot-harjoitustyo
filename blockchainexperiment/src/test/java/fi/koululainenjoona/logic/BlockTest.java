package fi.koululainenjoona.logic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlockTest {

    Block testBlock;
    Chain testChain;

    public BlockTest() {
    }

    @Before
    public void setUp() {
        testChain = new Chain();
        testChain.writeOnChain(new Block("This automatically created block is the beginning of the blockchain", "0"));
        testBlock = new Block("Here's some data", testChain.getPreviousBlock().getHash());
        testChain.writeOnChain(testBlock);
    }

    @Test
    public void newBlockHasCorrectData() {
        assertEquals("Here's some data", testBlock.getData());
    }

    @Test
    public void firstBlockHasCorrectDataWhenFetchedFromTheChain() {
        assertEquals("This automatically created block is the beginning of the blockchain", testChain.getBlock(0).getData());
    }

    @Test
    public void newBlockHasCorrectDataWhenFetchedFromTheChain() {
        assertEquals("Here's some data", testChain.getBlock(1).getData());
    }

    @Test
    public void getPreviousBlockReturnsABlock() {
        assertEquals(Block.class, testChain.getPreviousBlock().getClass());
    }
    
    @Test
    public void getPreviousHashReturnsTheCorrectHash() {
        assertEquals(testChain.getBlock(0).getHash(), testChain.getBlock(1).getPreviousHash());
    }

}
