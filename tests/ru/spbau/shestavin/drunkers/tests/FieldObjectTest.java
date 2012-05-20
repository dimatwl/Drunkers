package ru.spbau.shestavin.drunkers.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import ru.spbau.shestavin.drunkers.abstraction.Cell;
import ru.spbau.shestavin.drunkers.abstraction.FieldObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class FieldObjectTest {

    private static class TestObject extends FieldObject {

        public TestObject(Cell inpCell) {
            super(inpCell);
        }

        @Override
        public void doTurn() {

        }

        @Override
        public char getSymbolRepresentation() {
            return ' ';
        }

        @Override
        public void moveTo(Cell inpCell) {
            super.moveTo(inpCell);
        }

        public static Queue<Cell> findPath(Cell inpSource, Cell inpDestination) {
            return FieldObject.findPath(inpSource, inpDestination);
        }

    }

    private Cell mockedCell = mock(Cell.class);
    private Cell anotherMockedCell = mock(Cell.class);
    TestObject testObject = new TestObject(mockedCell);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPosition() throws Exception {
        assertSame(this.mockedCell, this.testObject.getPosition());
    }

    @Test
    public void testIsOnField() throws Exception {
        assertTrue(this.testObject.isOnField());
    }

    @Test
    public void testRemoveFromField() throws Exception {
        this.testObject.removeFromField();
        assertNull(this.testObject.getPosition());
    }

    @Test
    public void testMoveTo0() throws Exception {
        this.testObject.moveTo(this.anotherMockedCell);
        assertSame(this.anotherMockedCell, this.testObject.getPosition());
        verify(this.mockedCell, times(1)).removeObject();
        InOrder inOrder = inOrder(anotherMockedCell);
        inOrder.verify(anotherMockedCell, times(1)).removeObject();
        inOrder.verify(anotherMockedCell, times(1)).putObject(this.testObject);
    }

    @Test
    public void testMoveTo1() throws Exception {
        this.testObject.moveTo(null);
        assertSame(this.mockedCell, this.testObject.getPosition());
        verify(this.mockedCell, never()).removeObject();
        verify(anotherMockedCell, never()).removeObject();
        verify(anotherMockedCell, never()).putObject(this.testObject);
    }

    @Test
    public void testMoveTo2() throws Exception {
        this.testObject.removeFromField();
        this.testObject.moveTo(anotherMockedCell);
        assertSame(null, this.testObject.getPosition());
        verify(this.mockedCell, never()).removeObject();
        verify(anotherMockedCell, never()).removeObject();
        verify(anotherMockedCell, never()).putObject(this.testObject);
    }

    @Test
    public void testFindPath0() throws Exception {
        Cell begin = mock(Cell.class);
        Cell end = mock(Cell.class);
        List<Cell> neighboursBegin = new ArrayList<Cell>();
        neighboursBegin.add(end);
        List<Cell> neighboursEnd = new ArrayList<Cell>();
        neighboursEnd.add(begin);
        when(begin.getNeighbours()).thenReturn(neighboursBegin);
        when(end.getNeighbours()).thenReturn(neighboursEnd);
        Queue<Cell> path = TestObject.findPath(begin, end);
        verify(begin, times(1)).getNeighbours();
        verify(end, times(1)).getNeighbours();
        assertNotNull(path);
        assertFalse(path.contains(begin));
        assertSame(end, path.poll());
    }

    @Test
    public void testFindPath1() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        Queue<Cell> path = TestObject.findPath(line.get(0), line.get(lineLength - 1));
        for (Cell c : line) {
            verify(c, times(1)).getNeighbours();
        }
        assertNotNull(path);
        int i = 1;
        while (!path.isEmpty()) {
            assertSame(line.get(i++), path.poll());
        }
    }

    @Test
    public void testFindPath2() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        InOrder inOrder = inOrder(line.toArray());
        Queue<Cell> path = TestObject.findPath(line.get(30), line.get(50));
        for (Cell c : line) {
            verify(c, times(1)).getNeighbours();
        }
        assertNotNull(path);
        int i = 31;
        while (!path.isEmpty()) {
            assertSame(line.get(i++), path.poll());
        }
    }

    @Test
    public void testFindPath3() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        when(line.get(lineLength / 2).getNeighbours()).thenReturn(new ArrayList<Cell>());
        Queue<Cell> path = TestObject.findPath(line.get(0), line.get(lineLength - 1));
        for (int i = 0; i <= lineLength / 2; ++i) {
            verify(line.get(i), times(1)).getNeighbours();
        }
        for (int i = lineLength / 2 + 1; i < lineLength; ++i) {
            verify(line.get(i), never()).getNeighbours();
        }
        assertNull(path);
    }

    @Test
    public void testFindPath4() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        Queue<Cell> path = TestObject.findPath(line.get(0), line.get(0));
        for (Cell c : line) {
            verify(c, times(1)).getNeighbours();
        }
        assertNotNull(path);
        assertTrue(path.isEmpty());
    }

    @Test
    public void testFindPath5() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        Queue<Cell> path = TestObject.findPath(line.get(lineLength - 1), line.get(0));
        for (Cell c : line) {
            verify(c, times(1)).getNeighbours();
        }
        assertNotNull(path);
        int i = lineLength - 2;
        while (!path.isEmpty()) {
            assertSame(line.get(i--), path.poll());
        }
    }
}
