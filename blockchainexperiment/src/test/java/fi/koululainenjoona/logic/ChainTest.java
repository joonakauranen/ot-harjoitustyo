/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.koululainenjoona.logic;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
}
