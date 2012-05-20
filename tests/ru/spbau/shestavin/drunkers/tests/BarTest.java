package ru.spbau.shestavin.drunkers.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import ru.spbau.shestavin.drunkers.abstraction.Cell;
import ru.spbau.shestavin.drunkers.buildings.Bar;
import ru.spbau.shestavin.drunkers.characters.Drunker;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class BarTest {
    private Cell mockedCell = mock(Cell.class);
    private Cell freeCell = mock(Cell.class);
    private Bar testBar = new Bar(mockedCell);

    @Before
    public void setUp() throws Exception {
        when(this.mockedCell.isEmpty()).thenReturn(false);
        when(this.freeCell.isEmpty()).thenReturn(true);
        List<Cell> neighbours = new ArrayList<Cell>();
        neighbours.add(freeCell);
        when(this.mockedCell.getNeighbours()).thenReturn(neighbours);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoTurn0() throws Exception {
        this.testBar.doTurn();
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.freeCell, times(1)).isEmpty();
        verify(this.freeCell, times(1)).putObject(argThat(new ArgumentMatcher<Drunker>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Drunker;
            }
        }));
    }

    @Test
    public void testDoTurn1() throws Exception {
        when(this.freeCell.isEmpty()).thenReturn(false);
        this.testBar.doTurn();
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.freeCell, times(1)).isEmpty();
        verify(this.freeCell, never()).putObject(argThat(new ArgumentMatcher<Drunker>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Drunker;
            }
        }));
    }

    @Test
    public void testDoTurn2() throws Exception {
        when(this.freeCell.isEmpty()).thenReturn(false);
        this.testBar.doTurn();
        when(this.freeCell.isEmpty()).thenReturn(true);
        this.testBar.doTurn();
        verify(this.mockedCell, times(2)).getNeighbours();
        verify(this.freeCell, times(2)).isEmpty();
        verify(this.freeCell, times(1)).putObject(argThat(new ArgumentMatcher<Drunker>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Drunker;
            }
        }));
    }

    @Test
    public void testDoTurn3() throws Exception {
        this.testBar.doTurn();
        this.testBar.doTurn();
        verify(this.mockedCell, times(1)).getNeighbours();
        verify(this.freeCell, times(1)).isEmpty();
        verify(this.freeCell, times(1)).putObject(argThat(new ArgumentMatcher<Drunker>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Drunker;
            }
        }));
    }

    @Test
    public void testDoTurn4() throws Exception {
        final int numberOfTurnns = 98;
        when(this.freeCell.isEmpty()).thenReturn(false);
        for (int i = 0; i < numberOfTurnns; ++i) {
            this.testBar.doTurn();
        }
        verify(this.mockedCell, times(numberOfTurnns + 4)).getNeighbours();
        verify(this.freeCell, times(numberOfTurnns + 4)).isEmpty();
        verify(this.freeCell, never()).putObject(argThat(new ArgumentMatcher<Drunker>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Drunker;
            }
        }));
        when(this.freeCell.isEmpty()).thenReturn(true);
        this.testBar.doTurn();
        verify(this.mockedCell, times(numberOfTurnns + 9)).getNeighbours();
        verify(this.freeCell, times(numberOfTurnns + 9)).isEmpty();
        verify(this.freeCell, times(5)).putObject(argThat(new ArgumentMatcher<Drunker>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Drunker;
            }
        }));
        this.testBar.doTurn();
        this.testBar.doTurn();
        verify(this.mockedCell, times(numberOfTurnns + 10)).getNeighbours();
        verify(this.freeCell, times(numberOfTurnns + 10)).isEmpty();
        verify(this.freeCell, times(6)).putObject(argThat(new ArgumentMatcher<Drunker>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Drunker;
            }
        }));
    }

    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('T', this.testBar.getSymbolRepresentation());
    }

}
