package ru.spbau.shestavin.drunkers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Classname:
 * User: dimatwl
 * Date: 5/15/12
 * Time: 12:50 PM
 */
public class PillarTest {
    private Cell mockedCell = mock(Cell.class);
    private Pillar testPillar = new Pillar(mockedCell);

    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('#', this.testPillar.getSymbolRepresentation());
    }
}
