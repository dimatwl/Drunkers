package ru.spbau.shestavin.drunkers.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.spbau.shestavin.drunkers.abstraction.Cell;
import ru.spbau.shestavin.drunkers.abstraction.FieldObject;
import ru.spbau.shestavin.drunkers.buildings.PointForGlass;
import ru.spbau.shestavin.drunkers.characters.Beggar;
import ru.spbau.shestavin.drunkers.characters.Policeman;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class PointForGlassTest {
    private Cell pointCell = mock(Cell.class);
    private PointForGlass testPoint = new PointForGlass();
    private Cell freeCell = mock(Cell.class);

    @Before
    public void setUp() throws Exception {
        when(this.freeCell.getNeighbours()).thenReturn(Arrays.asList(this.pointCell));
        when(this.pointCell.getNeighbours()).thenReturn(Arrays.asList(this.freeCell));
        when(this.freeCell.isEmpty()).thenReturn(true);
        doAnswer(new Answer<PointForGlass>() {
            public PointForGlass answer(InvocationOnMock invocation) throws Throwable {
                invocation.callRealMethod();
                return null;
            }
        }).when(pointCell).putObject(argThat(new ArgumentMatcher<PointForGlass>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof PointForGlass;
            }
        }));
        pointCell.putObject(this.testPoint);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoTurn0() throws Exception {
        verify(this.freeCell, never()).putObject((FieldObject) anyObject());
        for (int i = 0; i < 40; ++i) {
            this.testPoint.doTurn();
            verify(this.freeCell, never()).putObject((FieldObject) anyObject());
        }
        this.testPoint.doTurn();
        verify(this.freeCell, times(1)).putObject(argThat(new ArgumentMatcher<Beggar>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Beggar;
            }
        }));
        for (int i = 0; i < 100; ++i) {
            this.testPoint.doTurn();
            verify(this.freeCell, times(1)).putObject((FieldObject) anyObject());
        }
        this.testPoint.beggarReturned();
        for (int i = 0; i < 40; ++i) {
            this.testPoint.doTurn();
            verify(this.freeCell, times(1)).putObject((FieldObject) anyObject());
        }
        this.testPoint.doTurn();
        verify(this.freeCell, times(2)).putObject(argThat(new ArgumentMatcher<Beggar>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Beggar;
            }
        }));
    }

    @Test
    public void testDoTurn1() throws Exception {
        when(this.freeCell.isEmpty()).thenReturn(false);
        verify(this.freeCell, never()).putObject((FieldObject) anyObject());
        for (int i = 0; i < 100; ++i) {
            this.testPoint.doTurn();
            verify(this.freeCell, never()).putObject((FieldObject) anyObject());
        }
        when(this.freeCell.isEmpty()).thenReturn(true);
        this.testPoint.doTurn();
        verify(this.freeCell, times(1)).putObject(argThat(new ArgumentMatcher<Policeman>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Beggar;
            }
        }));
    }


    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('B', this.testPoint.getSymbolRepresentation());
    }
}
