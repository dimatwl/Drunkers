package ru.spbau.shestavin.drunkers.tests;

import org.junit.Test;
import ru.spbau.shestavin.drunkers.core.Cell;
import ru.spbau.shestavin.drunkers.characters.Bottle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


public class BottleTest {
    private Cell mockedCell = mock(Cell.class);
    private Bottle testBottle = new Bottle(mockedCell);

    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('B', this.testBottle.getSymbolRepresentation());
    }
}
