package ru.spbau.shestavin.drunkers;

import com.sun.tools.javac.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Classname:
 * User: dimatwl
 * Date: 5/12/12
 * Time: 1:03 AM
 */
public class CellTest {
    Cell testCell = new Cell(new Pair<Integer, Integer>(0, 0));
    FieldObject mockedObject = mock(FieldObject.class);
    FieldObject anotherMockedObject = mock(FieldObject.class);

    @Before
    public void setUp() throws Exception {
        when(this.mockedObject.getSymbolRepresentation()).thenReturn('m');
        when(this.anotherMockedObject.getSymbolRepresentation()).thenReturn('a');
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddNeighbour() throws Exception {
        Cell neighbour0 = new Cell(new Pair<Integer, Integer>(1, 1));
        Cell neighbour1 = new Cell(new Pair<Integer, Integer>(2, 2));
        assertTrue(this.testCell.getNeighbours().isEmpty());
        assertFalse(this.testCell.getNeighbours().contains(neighbour0));
        this.testCell.addNeighbour(neighbour0);
        assertFalse(this.testCell.getNeighbours().isEmpty());
        assertTrue(this.testCell.getNeighbours().contains(neighbour0));
        assertFalse(this.testCell.getNeighbours().contains(neighbour1));
        this.testCell.addNeighbour(neighbour1);
        assertTrue(this.testCell.getNeighbours().contains(neighbour1));
    }

    @Test
    public void testGetNeighbours() throws Exception {
        assertTrue(this.testCell.getNeighbours() instanceof ArrayList);
        Cell neighbour0 = new Cell(new Pair<Integer, Integer>(1, 1));
        this.testCell.addNeighbour(neighbour0);
        assertTrue(this.testCell.getNeighbours() instanceof ArrayList);
        Cell neighbour1 = new Cell(new Pair<Integer, Integer>(2, 2));
        this.testCell.addNeighbour(neighbour1);
        assertTrue(this.testCell.getNeighbours() instanceof ArrayList);
    }

    @Test
    public void testGetCoordinates() throws Exception {
        assertTrue(this.testCell.getCoordinates() instanceof Pair);
        assertEquals(this.testCell.getCoordinates(), new Pair<Integer, Integer>(0, 0));
        Cell another = new Cell(new Pair<Integer, Integer>(1, 1));
        assertTrue(another.getCoordinates() instanceof Pair);
        assertEquals(another.getCoordinates(), new Pair<Integer, Integer>(1, 1));
    }

    @Test
    public void testGetObject() throws Exception {
        assertNull(this.testCell.getObject());
        this.testCell.putObject(this.mockedObject);
        assertSame(this.mockedObject, this.testCell.getObject());
    }

    @Test
    public void testRemoveObject() throws Exception {
        assertNull(this.testCell.getObject());
        this.testCell.removeObject();
        assertNull(this.testCell.getObject());
        this.testCell.putObject(this.mockedObject);
        assertSame(this.mockedObject, this.testCell.getObject());
        verify(this.mockedObject, never()).removeFromField();
        this.testCell.removeObject();
        verify(this.mockedObject, times(1)).removeFromField();
        assertNull(this.testCell.getObject());
        this.testCell.removeObject();
        verify(this.mockedObject, times(1)).removeFromField();
        assertNull(this.testCell.getObject());
    }

    @Test
    public void testPutObject() throws Exception {
        assertNull(this.testCell.getObject());
        this.testCell.putObject(this.mockedObject);
        assertSame(this.mockedObject, this.testCell.getObject());
        verify(this.mockedObject, never()).removeFromField();
        this.testCell.putObject(this.anotherMockedObject);
        assertSame(this.anotherMockedObject, this.testCell.getObject());
        verify(this.mockedObject, times(1)).removeFromField();
        verify(this.anotherMockedObject, never()).removeFromField();
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(this.testCell.isEmpty());
        this.testCell.putObject(this.mockedObject);
        assertFalse(this.testCell.isEmpty());
    }

    @Test
    public void testGetSymbol() throws Exception {
        assertEquals(this.testCell.getSymbol(), ' ');
        this.testCell.putObject(this.mockedObject);
        verify(this.mockedObject, never()).getSymbolRepresentation();
        assertEquals(this.testCell.getSymbol(), 'm');
        this.testCell.putObject(this.anotherMockedObject);
        verify(this.anotherMockedObject, never()).getSymbolRepresentation();
        assertEquals(this.testCell.getSymbol(), 'a');
        this.testCell.removeObject();
        assertEquals(this.testCell.getSymbol(), ' ');
        InOrder inOrder = inOrder(this.mockedObject, this.anotherMockedObject);
        inOrder.verify(this.mockedObject, times(1)).getSymbolRepresentation();
        inOrder.verify(this.anotherMockedObject, times(1)).getSymbolRepresentation();
    }

    @Test
    public void testIsIlluminated() throws Exception {
        assertFalse(this.testCell.isIlluminated());
        this.testCell.setIllumination(true);
        assertTrue(this.testCell.isIlluminated());
        this.testCell.setIllumination(false);
        assertFalse(this.testCell.isIlluminated());

    }

    @Test
    public void testSetIllumination() throws Exception {
        this.testCell.setIllumination(true);
        assertTrue(this.testCell.isIlluminated());
        this.testCell.setIllumination(false);
        assertFalse(this.testCell.isIlluminated());
    }

    @Test
    public void testDistTo() throws Exception {
        assertEquals(0, this.testCell.distTo(new Cell(new Pair<Integer, Integer>(0,0))));
        assertEquals(1, this.testCell.distTo(new Cell(new Pair<Integer, Integer>(1,0))));
        assertEquals(1, this.testCell.distTo(new Cell(new Pair<Integer, Integer>(0,1))));
        assertEquals(2, this.testCell.distTo(new Cell(new Pair<Integer, Integer>(1,1))));
        assertEquals(10, this.testCell.distTo(new Cell(new Pair<Integer, Integer>(10,0))));
        assertEquals(10, this.testCell.distTo(new Cell(new Pair<Integer, Integer>(0,10))));
        assertEquals(20, this.testCell.distTo(new Cell(new Pair<Integer, Integer>(10,10))));
        assertEquals(11, this.testCell.distTo(new Cell(new Pair<Integer, Integer>(4,7))));
    }
}
