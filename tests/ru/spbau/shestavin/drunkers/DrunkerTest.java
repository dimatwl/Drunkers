package ru.spbau.shestavin.drunkers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;


/**
 * Classname:
 * User: dimatwl
 * Date: 5/15/12
 * Time: 9:12 AM
 */
public class DrunkerTest {

    private Cell mockedCell = mock(Cell.class);
    private Cell anotherMockedCell = mock(Cell.class);
    private Bottle mockedBottle = mock(Bottle.class);
    private Pillar mockedPillar = mock(Pillar.class);
    private Drunker mockedDrunker = mock(Drunker.class);
    private Drunker testDrunker = new Drunker(mockedCell);


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsStandingSleeping0() throws Exception {
        assertFalse(this.testDrunker.isStandingSleeping());
        Cell neighbour = mock(Cell.class);
        when(neighbour.isEmpty()).thenReturn(true);
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(neighbour));
        this.testDrunker.doTurn();
        assertFalse(this.testDrunker.isStandingSleeping());
    }

    @Test
    public void testIsStandingSleeping1() throws Exception {
        assertFalse(this.testDrunker.isStandingSleeping());
        Cell neighbour = mock(Cell.class);
        when(neighbour.isEmpty()).thenReturn(false);
        when(neighbour.getObject()).thenReturn(mock(Pillar.class));
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(neighbour));
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isStandingSleeping());
    }


    @Test
    public void testIsIllegal0() throws Exception {
        assertFalse(this.testDrunker.isIllegal());
        Cell neighbour = mock(Cell.class);
        when(neighbour.isEmpty()).thenReturn(true);
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(neighbour));
        this.testDrunker.doTurn();
        assertFalse(this.testDrunker.isIllegal());
    }

    @Test
    public void testIsIllegal1() throws Exception {
        assertFalse(this.testDrunker.isIllegal());
        Cell neighbour = mock(Cell.class);
        when(neighbour.isEmpty()).thenReturn(false);
        when(neighbour.getObject()).thenReturn(mock(Pillar.class));
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(neighbour));
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
    }


    @Test
    public void testIsIllegal2() throws Exception {
        assertFalse(this.testDrunker.isIllegal());
        Cell neighbour = mock(Cell.class);
        when(neighbour.isEmpty()).thenReturn(false);
        when(neighbour.getObject()).thenReturn(mock(Bottle.class));
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(neighbour));
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
    }


    private List<Cell> getOneWayCircle(int inpVertexNum){
        List<Cell> circle = new ArrayList<Cell>();
        circle.add(mock(Cell.class));
        when(circle.get(0).isEmpty()).thenReturn(true);
        for (int i = 0; i < inpVertexNum - 1; ++i){
            circle.add(mock(Cell.class));
            when(circle.get(i).getNeighbours()).thenReturn(Arrays.asList(circle.get(i+1)));
            when(circle.get(i).isEmpty()).thenReturn(true);
        }
        when(circle.get(inpVertexNum - 1).getNeighbours()).thenReturn(Arrays.asList(circle.get(0)));
        when(circle.get(inpVertexNum - 1).isEmpty()).thenReturn(true);
        return circle;
    }


    @Test
    public void testDoTurn0() throws Exception {
        final int lineLength = 100;
        List<Cell> line = LineGraph.get(lineLength);
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(line.get(0)));
        assertFalse(this.testDrunker.isIllegal());
        for (int i = 0; i < 1000; ++i){
            this.testDrunker.doTurn();
        }
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();
        verify(line.get(0), atLeastOnce()).getNeighbours();
        verify(line.get(0), atLeastOnce()).removeObject();
        verify(line.get(0), atLeastOnce()).putObject(this.testDrunker);
    }

    @Test
    public void testDoTurn1() throws Exception {
        final int circleLength = 100;
        List<Cell> circle = this.getOneWayCircle(circleLength);
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(circle.get(0)));
        assertFalse(this.testDrunker.isIllegal());
        this.testDrunker.doTurn();
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();
        verify(circle.get(0), never()).getNeighbours();
        verify(circle.get(0), times(1)).removeObject();
        verify(circle.get(0), times(1)).putObject(this.testDrunker);
        for (int i = 0; i < circleLength; ++i){
            this.testDrunker.doTurn();
        }
        assertEquals(circle.get(0), this.testDrunker.getPosition());
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();

        verify(circle.get(0), times(1)).getNeighbours();
        verify(circle.get(0), times(3)).removeObject();
        verify(circle.get(0), times(2)).putObject(this.testDrunker);
        for (int i = 1; i < circleLength; ++i){
            verify(circle.get(i), times(1)).getNeighbours();
            verify(circle.get(i), times(2)).removeObject();
            verify(circle.get(i), times(1)).putObject(this.testDrunker);
        }
    }

    @Test
    public void testDoTurn2() throws Exception {
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(this.anotherMockedCell));
        when(this.anotherMockedCell.getNeighbours()).thenReturn(Arrays.asList(this.mockedCell));
        when(this.anotherMockedCell.isEmpty()).thenReturn(false);
        when(this.anotherMockedCell.getObject()).thenReturn(this.mockedBottle);
        assertFalse(this.testDrunker.isIllegal());
        assertEquals(this.mockedCell, this.testDrunker.getPosition());
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
        assertFalse(this.testDrunker.isStandingSleeping());
        assertEquals(this.anotherMockedCell, this.testDrunker.getPosition());
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();
        verify(this.anotherMockedCell, never()).getNeighbours();
        verify(this.anotherMockedCell, times(1)).removeObject();
        verify(this.anotherMockedCell, times(1)).putObject(this.testDrunker);
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
        assertFalse(this.testDrunker.isStandingSleeping());
        assertEquals(this.anotherMockedCell, this.testDrunker.getPosition());
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();
        verify(this.anotherMockedCell, never()).getNeighbours();
        verify(this.anotherMockedCell, times(1)).removeObject();
        verify(this.anotherMockedCell, times(1)).putObject(this.testDrunker);

    }

    @Test
    public void testDoTurn3() throws Exception {
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(this.anotherMockedCell));
        when(this.anotherMockedCell.getNeighbours()).thenReturn(Arrays.asList(this.mockedCell));
        when(this.anotherMockedCell.isEmpty()).thenReturn(false);
        when(this.anotherMockedCell.getObject()).thenReturn(this.mockedPillar);
        assertFalse(this.testDrunker.isIllegal());
        assertEquals(this.mockedCell, this.testDrunker.getPosition());
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
        assertTrue(this.testDrunker.isStandingSleeping());
        assertEquals(this.mockedCell, this.testDrunker.getPosition());
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).getNeighbours();
        verify(this.anotherMockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).putObject(this.testDrunker);
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
        assertTrue(this.testDrunker.isStandingSleeping());
        assertEquals(this.mockedCell, this.testDrunker.getPosition());
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).getNeighbours();
        verify(this.anotherMockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).putObject(this.testDrunker);
    }

    @Test
    public void testDoTurn4() throws Exception {
        when(this.mockedDrunker.isStandingSleeping()).thenReturn(true);
        when(this.mockedDrunker.isIllegal()).thenReturn(true);
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(this.anotherMockedCell));
        when(this.anotherMockedCell.getNeighbours()).thenReturn(Arrays.asList(this.mockedCell));
        when(this.anotherMockedCell.isEmpty()).thenReturn(false);
        when(this.anotherMockedCell.getObject()).thenReturn(this.mockedDrunker);
        assertFalse(this.testDrunker.isIllegal());
        assertEquals(this.mockedCell, this.testDrunker.getPosition());
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
        assertTrue(this.testDrunker.isStandingSleeping());
        assertEquals(this.mockedCell, this.testDrunker.getPosition());
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).getNeighbours();
        verify(this.anotherMockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).putObject(this.testDrunker);
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
        assertTrue(this.testDrunker.isStandingSleeping());
        assertEquals(this.mockedCell, this.testDrunker.getPosition());
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).getNeighbours();
        verify(this.anotherMockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).putObject(this.testDrunker);
    }


    @Test
    public void testDoTurn5() throws Exception {
        when(this.mockedDrunker.isStandingSleeping()).thenReturn(false);
        when(this.mockedDrunker.isIllegal()).thenReturn(false);
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(this.anotherMockedCell));
        when(this.anotherMockedCell.getNeighbours()).thenReturn(Arrays.asList(this.mockedCell));
        when(this.anotherMockedCell.isEmpty()).thenReturn(false);
        when(this.anotherMockedCell.getObject()).thenReturn(this.mockedDrunker);
        assertFalse(this.testDrunker.isIllegal());
        assertEquals(this.mockedCell, this.testDrunker.getPosition());
        this.testDrunker.doTurn();
        assertFalse(this.testDrunker.isIllegal());
        assertEquals(this.mockedCell, this.testDrunker.getPosition());
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.mockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).getNeighbours();
        verify(this.anotherMockedCell, never()).removeObject();
        verify(this.anotherMockedCell, never()).putObject(this.testDrunker);
        when(this.anotherMockedCell.isEmpty()).thenReturn(true);
        this.testDrunker.doTurn();
        assertFalse(this.testDrunker.isIllegal());
        assertEquals(this.anotherMockedCell, this.testDrunker.getPosition());
        verify(this.mockedCell, times(2)).getNeighbours();
        verify(this.mockedCell, times(1)).removeObject();
        verify(this.anotherMockedCell, never()).getNeighbours();
        verify(this.anotherMockedCell, times(1)).removeObject();
        verify(this.anotherMockedCell, times(1)).putObject(this.testDrunker);
    }

    @Test
    public void testGetSymbolRepresentation0() throws Exception {
        assertFalse(this.testDrunker.isIllegal());
        assertEquals('@', this.testDrunker.getSymbolRepresentation());
    }

    @Test
    public void testGetSymbolRepresentation1() throws Exception {
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(this.anotherMockedCell));
        when(this.anotherMockedCell.getNeighbours()).thenReturn(Arrays.asList(this.mockedCell));
        when(this.anotherMockedCell.isEmpty()).thenReturn(false);
        when(this.anotherMockedCell.getObject()).thenReturn(this.mockedBottle);
        assertFalse(this.testDrunker.isIllegal());
        assertEquals('@', this.testDrunker.getSymbolRepresentation());
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
        assertFalse(this.testDrunker.isStandingSleeping());
        assertEquals('&', this.testDrunker.getSymbolRepresentation());
    }

    @Test
    public void testGetSymbolRepresentation2() throws Exception {
        when(this.mockedCell.getNeighbours()).thenReturn(Arrays.asList(this.anotherMockedCell));
        when(this.anotherMockedCell.getNeighbours()).thenReturn(Arrays.asList(this.mockedCell));
        when(this.anotherMockedCell.isEmpty()).thenReturn(false);
        when(this.anotherMockedCell.getObject()).thenReturn(this.mockedPillar);
        assertFalse(this.testDrunker.isIllegal());
        assertEquals('@', this.testDrunker.getSymbolRepresentation());
        this.testDrunker.doTurn();
        assertTrue(this.testDrunker.isIllegal());
        assertTrue(this.testDrunker.isStandingSleeping());
        assertEquals('1', this.testDrunker.getSymbolRepresentation());
    }
}
