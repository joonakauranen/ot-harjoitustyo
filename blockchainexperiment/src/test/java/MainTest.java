
import blockchainexperiment.Block;
import blockchainexperiment.Chain;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class MainTest {

    Block testBlock;
    Chain testChain;

    @Before
    public void setUp() {
        testBlock = new Block("Here's some data");
        testChain = new Chain();
        testChain.writeOnChain(testBlock);
    }

    @Test
    public void blockConstructorSetsCorrectValue() {
        assertEquals("Block data: Here's some data", testBlock.toString());
    }

}