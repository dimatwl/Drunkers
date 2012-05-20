package ru.spbau.shestavin.drunkers.tests;

import org.junit.Test;
import ru.spbau.shestavin.drunkers.abstraction.Cell;
import ru.spbau.shestavin.drunkers.buildings.Pillar;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


public class PillarTest {
    private Cell mockedCell = mock(Cell.class);
    private Pillar testPillar = new Pillar(mockedCell);

    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('#', this.testPillar.getSymbolRepresentation());
    }
}
