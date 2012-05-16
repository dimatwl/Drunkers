package ru.spbau.shestavin.drunkers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Queue;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Classname:
 * User: dimatwl
 * Date: 5/13/12
 * Time: 10:23 AM
 */
public class FieldObjectTest {

    private class TestObject extends FieldObject{

        public TestObject(Cell inpCell){
            super(inpCell);
        }

        @Override
        public void doTurn(){

        }

        @Override
        public char getSymbolRepresentation(){
            return  ' ';
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

    private List<Cell> getLineGraph(int inpVertexNum){
        List<Cell> line = new ArrayList<Cell>();
        line.add(mock(Cell.class));
        line.add(mock(Cell.class));
        List<Cell> neighboursForFirst = new ArrayList<Cell>();
        neighboursForFirst.add(line.get(1));
        when(line.get(0).getNeighbours()).thenReturn(neighboursForFirst);
        when(line.get(0).isEmpty()).thenReturn(true);
        for (int i = 1; i < inpVertexNum - 1; ++i){
            line.add(mock(Cell.class));
            List<Cell> neighbours = new ArrayList<Cell>();
            neighbours.add(line.get(i+1));
            neighbours.add(line.get(i-1));
            when(line.get(i).getNeighbours()).thenReturn(neighbours);
            when(line.get(i).isEmpty()).thenReturn(true);
        }
        List<Cell> neighboursForLast = new ArrayList<Cell>();
        neighboursForLast.add(line.get(inpVertexNum - 2));
        when(line.get(inpVertexNum - 1).getNeighbours()).thenReturn(neighboursForLast);
        when(line.get(inpVertexNum - 1).isEmpty()).thenReturn(true);
        return line;
    }

    @Test
    public void testFindPath1() throws Exception {
        final int lineLength = 100;
        List<Cell> line = this.getLineGraph(lineLength);
        Queue<Cell> path = TestObject.findPath(line.get(0), line.get(lineLength - 1));
        for (Cell c : line){
            verify(c, times(1)).getNeighbours();
        }
        assertNotNull(path);
        int i = 1;
        while (!path.isEmpty()){
            assertSame(line.get(i++), path.poll());
        }
    }

    @Test
    public void testFindPath2() throws Exception {
        final int lineLength = 100;
        List<Cell> line = this.getLineGraph(lineLength);
        InOrder inOrder = inOrder(line.toArray());
        Queue<Cell> path = TestObject.findPath(line.get(30), line.get(50));
        for (Cell c : line){
            verify(c, times(1)).getNeighbours();
        }
        assertNotNull(path);
        int i = 31;
        while (!path.isEmpty()){
            assertSame(line.get(i++), path.poll());
        }
    }

    @Test
    public void testFindPath3() throws Exception {
        final int lineLength = 100;
        List<Cell> line = this.getLineGraph(lineLength);
        when(line.get(lineLength/2).getNeighbours()).thenReturn(new ArrayList<Cell>());
        Queue<Cell> path = TestObject.findPath(line.get(0), line.get(lineLength - 1));
        for (int i = 0; i <= lineLength/2; ++i){
            verify(line.get(i), times(1)).getNeighbours();
        }
        for (int i = lineLength/2 + 1; i < lineLength; ++i){
            verify(line.get(i), never()).getNeighbours();
        }
        assertNull(path);
    }

    @Test
    public void testFindPath4() throws Exception {
        final int lineLength = 100;
        List<Cell> line = this.getLineGraph(lineLength);
        Queue<Cell> path = TestObject.findPath(line.get(0), line.get(0));
        for (Cell c : line){
            verify(c, times(1)).getNeighbours();
        }
        assertNotNull(path);
        assertTrue(path.isEmpty());
    }

    @Test
    public void testFindPath5() throws Exception {
        final int lineLength = 100;
        List<Cell> line = this.getLineGraph(lineLength);
        Queue<Cell> path = TestObject.findPath(line.get(lineLength-1), line.get(0));
        for (Cell c : line){
            verify(c, times(1)).getNeighbours();
        }
        assertNotNull(path);
        int i = lineLength - 2;
        while (!path.isEmpty()){
            assertSame(line.get(i--), path.poll());
        }
    }
}
