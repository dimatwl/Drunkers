package ru.spbau.shestavin.drunkers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Classname:
 * User: dimatwl
 * Date: 5/15/12
 * Time: 1:14 PM
 */
public class PolicemanTest {
    private Cell freeCell = mock(Cell.class);
    private Cell stationCell = mock(Cell.class);
    private PoliceStation mockedStation = mock(PoliceStation.class);
    private Drunker mockedDrunker = mock(Drunker.class);
    private Policeman testPoliceman = null;  // should be specified

    @Before
    public void setUp() throws Exception {
        when(this.freeCell.getNeighbours()).thenReturn(Arrays.asList(this.stationCell));
        when(this.stationCell.getNeighbours()).thenReturn(Arrays.asList(this.freeCell));
        when(this.mockedStation.getPosition()).thenReturn(stationCell);
        when(this.mockedDrunker.getPosition()).thenReturn(null); // should be specified.
    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testDoTurn0() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        when(this.freeCell.getNeighbours()).thenReturn(Arrays.asList(this.stationCell, line.get(0)));
        when(line.get(0).getNeighbours()).thenReturn(Arrays.asList(this.freeCell, line.get(1)));
        when(this.mockedDrunker.getPosition()).thenReturn(line.get(lineLength - 1));
        when(line.get(lineLength-1).getObject()).thenReturn(this.mockedDrunker);
        when(line.get(lineLength-1).isEmpty()).thenReturn(false);
        this.testPoliceman = new Policeman(this.freeCell, this.mockedDrunker, this.mockedStation);
        when(this.freeCell.getObject()).thenReturn(this.testPoliceman);
        when(this.freeCell.isEmpty()).thenReturn(false);
        assertSame(this.freeCell, this.testPoliceman.getPosition());
        for (int i = 0; i < lineLength; ++i){
            this.testPoliceman.doTurn();
            assertSame(line.get(i), this.testPoliceman.getPosition());
        }
        when(this.freeCell.getObject()).thenReturn(null);
        when(this.freeCell.isEmpty()).thenReturn(true);
        verify(line.get(lineLength-1), times(1)).removeObject();
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength-1), this.testPoliceman.getPosition());
        for (int i = lineLength - 2; i >= 0; --i){
            this.testPoliceman.doTurn();
            assertSame(line.get(i), this.testPoliceman.getPosition());
        }
        verify(this.freeCell, times(1)).removeObject();
        this.testPoliceman.doTurn();
        verify(this.freeCell, times(2)).removeObject();
        assertSame(this.freeCell, this.testPoliceman.getPosition());
        this.testPoliceman.doTurn();
        verify(this.mockedStation, times(1)).policemanReturned();
        verify(this.freeCell, times(3)).removeObject();
    }

    @Test
    public void testDoTurn1() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        when(this.freeCell.getNeighbours()).thenReturn(Arrays.asList(this.stationCell, line.get(0)));
        when(line.get(0).getNeighbours()).thenReturn(Arrays.asList(this.freeCell, line.get(1)));
        when(this.mockedDrunker.getPosition()).thenReturn(line.get(lineLength - 1));
        when(line.get(lineLength-1).getObject()).thenReturn(this.mockedDrunker);
        when(line.get(lineLength-1).isEmpty()).thenReturn(false);
        when(line.get(lineLength/2).getObject()).thenReturn(mock(FieldObject.class));
        when(line.get(lineLength/2).isEmpty()).thenReturn(false);
        this.testPoliceman = new Policeman(this.freeCell, this.mockedDrunker, this.mockedStation);
        when(this.freeCell.getObject()).thenReturn(this.testPoliceman);
        when(this.freeCell.isEmpty()).thenReturn(false);
        assertSame(this.freeCell, this.testPoliceman.getPosition());
        this.testPoliceman.doTurn();
        assertSame(this.freeCell, this.testPoliceman.getPosition());
        this.testPoliceman.doTurn();
        assertSame(this.freeCell, this.testPoliceman.getPosition());
        when(line.get(lineLength/2).getObject()).thenReturn(null);
        when(line.get(lineLength/2).isEmpty()).thenReturn(true);
        this.testPoliceman.doTurn();
        assertSame(this.freeCell, this.testPoliceman.getPosition());
        for (int i = 0; i < lineLength; ++i){
            this.testPoliceman.doTurn();
            assertSame(line.get(i), this.testPoliceman.getPosition());
        }
        when(this.freeCell.getObject()).thenReturn(null);
        when(this.freeCell.isEmpty()).thenReturn(true);
        verify(line.get(lineLength-1), times(1)).removeObject();
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength-1), this.testPoliceman.getPosition());
        for (int i = lineLength - 2; i >= 0; --i){
            this.testPoliceman.doTurn();
            assertSame(line.get(i), this.testPoliceman.getPosition());
        }
        verify(this.freeCell, times(1)).removeObject();
        this.testPoliceman.doTurn();
        verify(this.freeCell, times(2)).removeObject();
        assertSame(this.freeCell, this.testPoliceman.getPosition());
        this.testPoliceman.doTurn();
        verify(this.mockedStation, times(1)).policemanReturned();
        verify(this.freeCell, times(3)).removeObject();
    }

    @Test
    public void testDoTurn2() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        when(this.freeCell.getNeighbours()).thenReturn(Arrays.asList(this.stationCell, line.get(0)));
        when(line.get(0).getNeighbours()).thenReturn(Arrays.asList(this.freeCell, line.get(1)));
        when(this.mockedDrunker.getPosition()).thenReturn(line.get(lineLength - 1));
        when(line.get(lineLength-1).getObject()).thenReturn(this.mockedDrunker);
        when(line.get(lineLength-1).isEmpty()).thenReturn(false);
        this.testPoliceman = new Policeman(this.freeCell, this.mockedDrunker, this.mockedStation);
        when(this.freeCell.getObject()).thenReturn(this.testPoliceman);
        when(this.freeCell.isEmpty()).thenReturn(false);
        assertSame(this.freeCell, this.testPoliceman.getPosition());
        for (int i = 0; i < lineLength/2 - 2; ++i){
            this.testPoliceman.doTurn();
            assertSame(line.get(i), this.testPoliceman.getPosition());
        }
        when(line.get(lineLength/2).getObject()).thenReturn(mock(FieldObject.class));
        when(line.get(lineLength/2).isEmpty()).thenReturn(false);
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength/2 - 2), this.testPoliceman.getPosition());
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength/2 - 1), this.testPoliceman.getPosition());
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength/2 - 1), this.testPoliceman.getPosition());
        when(line.get(lineLength/2).getObject()).thenReturn(null);
        when(line.get(lineLength/2).isEmpty()).thenReturn(true);
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength/2 - 1), this.testPoliceman.getPosition());
        for (int i = lineLength/2; i < lineLength; ++i){
            this.testPoliceman.doTurn();
            assertSame(line.get(i), this.testPoliceman.getPosition());
        }

        when(this.freeCell.getObject()).thenReturn(null);
        when(this.freeCell.isEmpty()).thenReturn(true);
        verify(line.get(lineLength-1), times(1)).removeObject();
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength-1), this.testPoliceman.getPosition());
        for (int i = lineLength - 2; i >= lineLength/2 + 3; --i){
            this.testPoliceman.doTurn();
            assertSame(line.get(i), this.testPoliceman.getPosition());
        }
        when(line.get(lineLength/2).getObject()).thenReturn(mock(FieldObject.class));
        when(line.get(lineLength/2).isEmpty()).thenReturn(false);
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength/2 + 2), this.testPoliceman.getPosition());
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength/2 + 1), this.testPoliceman.getPosition());
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength/2 + 1), this.testPoliceman.getPosition());
        when(line.get(lineLength/2).getObject()).thenReturn(null);
        when(line.get(lineLength/2).isEmpty()).thenReturn(true);
        this.testPoliceman.doTurn();
        assertSame(line.get(lineLength/2 + 1), this.testPoliceman.getPosition());
        for (int i = lineLength/2; i >= 0; --i){
            this.testPoliceman.doTurn();
            assertSame(line.get(i), this.testPoliceman.getPosition());
        }
        verify(this.freeCell, times(1)).removeObject();
        this.testPoliceman.doTurn();
        verify(this.freeCell, times(2)).removeObject();
        assertSame(this.freeCell, this.testPoliceman.getPosition());
        this.testPoliceman.doTurn();
        verify(this.mockedStation, times(1)).policemanReturned();
        verify(this.freeCell, times(3)).removeObject();


    }

    @Test
    public void testGetSymbolRepresentation() throws Exception {
        this.testPoliceman = new Policeman(this.freeCell, this.mockedDrunker, this.mockedStation);
        assertEquals('!', this.testPoliceman.getSymbolRepresentation());
    }
}
