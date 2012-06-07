package ru.spbau.shestavin.drunkers.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.spbau.shestavin.drunkers.core.Cell;
import ru.spbau.shestavin.drunkers.core.FieldObject;
import ru.spbau.shestavin.drunkers.buildings.PointForGlass;
import ru.spbau.shestavin.drunkers.characters.Beggar;
import ru.spbau.shestavin.drunkers.characters.Bottle;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;


public class BeggarTest {

    private Cell mockedCell = mock(Cell.class);
    private Cell anotherMockedCell = mock(Cell.class);
    private Bottle mockedBottle = mock(Bottle.class);
    private PointForGlass mockedPoint = mock(PointForGlass.class);
    private Cell pointCell = mock(Cell.class);
    private Beggar testBeggar = new Beggar(mockedCell, mockedPoint);

    @Before
    public void setUp() throws Exception {
        when(this.mockedPoint.getPosition()).thenReturn(this.pointCell);
        when(this.mockedCell.getObject()).thenReturn(this.testBeggar);
        when(this.mockedCell.isEmpty()).thenReturn(false);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoTurn0() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(line.get(0)));
        for (int i = 0; i < 10; ++i) {
            this.testBeggar.doTurn();
        }
        verify(this.mockedCell, atLeastOnce()).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();
        verify(line.get(0), atLeastOnce()).getNeighbours();
        verify(line.get(0), atLeastOnce()).removeObject();
        verify(line.get(0), atLeastOnce()).putObject(this.testBeggar);
    }

    @Test
    public void testDoTurn1() throws Exception {
        final int circleLength = 10;
        List<Cell> circle = MockGraph.getOneWayCircle(circleLength);
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(circle.get(0)));
        this.testBeggar.doTurn();
        verify(this.mockedCell, atLeastOnce()).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();
        verify(circle.get(0), times(1)).removeObject();
        verify(circle.get(0), times(1)).putObject(this.testBeggar);
        for (int i = 0; i < circleLength; ++i) {
            this.testBeggar.doTurn();
        }
        assertEquals(circle.get(0), this.testBeggar.getPosition());
        verify(this.mockedCell, atLeastOnce()).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();

        verify(circle.get(0), atLeastOnce()).getNeighbours();
        verify(circle.get(0), times(3)).removeObject();
        verify(circle.get(0), times(2)).putObject(this.testBeggar);
        for (int i = 1; i < circleLength; ++i) {
            verify(circle.get(i), atLeastOnce()).getNeighbours();
            verify(circle.get(i), times(2)).removeObject();
            verify(circle.get(i), times(1)).putObject(this.testBeggar);
        }
    }

    @Test
    public void testDoTurn2() throws Exception {
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(this.anotherMockedCell));
        when(this.anotherMockedCell.getNeighbours()).thenReturn(Arrays.asList(this.mockedCell));
        when(this.anotherMockedCell.isEmpty()).thenReturn(false);
        when(this.anotherMockedCell.getObject()).thenReturn(mock(FieldObject.class));
        assertEquals(this.mockedCell, this.testBeggar.getPosition());
        this.testBeggar.doTurn();
        assertEquals(this.mockedCell, this.testBeggar.getPosition());
        verify(this.mockedCell, atLeastOnce()).getNeighbours();
        verify(this.mockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).putObject(this.testBeggar);
        when(this.anotherMockedCell.isEmpty()).thenReturn(true);
        this.testBeggar.doTurn();
        assertEquals(this.anotherMockedCell, this.testBeggar.getPosition());
        verify(this.mockedCell, atLeastOnce()).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();
        verify(this.anotherMockedCell, times(1)).removeObject();
        verify(this.anotherMockedCell, times(1)).putObject(this.testBeggar);
    }

    @Test
    public void testDoTurn3() throws Exception {
        final int lineLength = 10;
        List<Cell> line = MockGraph.getLine(lineLength);
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(this.pointCell, line.get(0)));
        when(line.get(0).getNeighbours()).thenReturn(Arrays.asList(this.mockedCell, line.get(1)));
        when(this.mockedBottle.getPosition()).thenReturn(line.get(lineLength - 1));
        when(line.get(lineLength - 1).getObject()).thenReturn(this.mockedBottle);
        when(line.get(lineLength - 1).isEmpty()).thenReturn(false);
        when(this.mockedBottle.isOnField()).thenReturn(true);

        assertSame(this.mockedCell, this.testBeggar.getPosition());
        this.testBeggar.doTurn();
        for (int i = 0; i < lineLength; ++i) {
            this.testBeggar.doTurn();
            assertSame(line.get(i), this.testBeggar.getPosition());
        }
        when(this.mockedCell.getObject()).thenReturn(null);
        when(this.mockedCell.isEmpty()).thenReturn(true);
        verify(line.get(lineLength - 1), times(1)).removeObject();
        this.testBeggar.doTurn();
        assertSame(line.get(lineLength - 1), this.testBeggar.getPosition());
        for (int i = lineLength - 2; i >= 0; --i) {
            this.testBeggar.doTurn();
            assertSame(line.get(i), this.testBeggar.getPosition());
        }
        verify(this.mockedCell, times(1)).removeObject();
        this.testBeggar.doTurn();
        verify(this.mockedCell, times(2)).removeObject();
        assertSame(this.mockedCell, this.testBeggar.getPosition());
        this.testBeggar.doTurn();
        verify(this.mockedPoint, times(1)).beggarReturned();
        verify(this.mockedCell, times(3)).removeObject();
    }


    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('Z', this.testBeggar.getSymbolRepresentation());
    }
}
