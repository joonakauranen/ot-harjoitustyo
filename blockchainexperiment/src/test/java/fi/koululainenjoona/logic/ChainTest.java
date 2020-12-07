
package fi.koululainenjoona.logic;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChainTest {

    Block testBlock;
    Chain testChain;

    public ChainTest() {
    }

    @Before
    public void setUp() {
        testChain = new Chain();
        testChain.writeOnChain(new Block("This automatically created block is the beginning of the blockchain", "0"));
        testBlock = new Block("Here's some data", testChain.getPreviousBlock().getHash());
        testChain.writeOnChain(testBlock);
    }

    @Test
    public void getChainWorks() {
        assertEquals(ArrayList.class, testChain.getChain().getClass());
    }
    
    @Test
    public void writingOnChainWorks() {
        this.testChain.writeOnChain(testBlock);
        assertEquals("Here's some data", testChain.getBlock(2).getData());
    }
    
    @Test
    public void getPreviousBlockWorks() {
        assertEquals("Here's some data", testChain.getPreviousBlock().getData());
    }
    
    @Test
    public void getBlockWorks() {
        assertEquals("This automatically created block is the beginning of the blockchain", testChain.getBlock(0).getData());
    }
    
    @Test
    public void getChainSizeWorks() {
        assertEquals(2, testChain.getChainSize());
    }
    
    @Test
    public void validityCheckWorks() {
        assertEquals(true, testChain.checkValidity());
    }
    
}
