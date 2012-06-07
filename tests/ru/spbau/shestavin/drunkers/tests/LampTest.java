package ru.spbau.shestavin.drunkers.tests;

import org.junit.Test;
import ru.spbau.shestavin.drunkers.core.Cell;
import ru.spbau.shestavin.drunkers.buildings.Lamp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


public class LampTest {
    private Cell mockedCell = mock(Cell.class);
    private Lamp testLamp = new Lamp(3);

    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('Ф', this.testLamp.getSymbolRepresentation());
    }
}
