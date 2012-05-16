package ru.spbau.shestavin.drunkers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Classname:
 * User: dimatwl
 * Date: 5/15/12
 * Time: 12:47 PM
 */
public class LampTest {
    private Cell mockedCell = mock(Cell.class);
    private Lamp testLamp = new Lamp(mockedCell);

    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('Ф', this.testLamp.getSymbolRepresentation());
    }
}
