package ru.spbau.shestavin.drunkers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Classname:
 * User: dimatwl
 * Date: 5/14/12
 * Time: 4:18 PM
 */
public class BottleTest {
    private Cell mockedCell = mock(Cell.class);
    private Bottle testBottle = new Bottle(mockedCell);

    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('B', this.testBottle.getSymbolRepresentation());
    }
}
