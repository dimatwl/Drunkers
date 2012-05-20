package ru.spbau.shestavin.drunkers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Classname:
 * User: dimatwl
 * Date: 5/16/12
 * Time: 1:13 PM
 */
public class PoliceStationTest {
    private Cell stationCell = mock(Cell.class);
    private Cell freeCell = mock(Cell.class);
    private Drunker mockedDrunker = mock(Drunker.class);
    private Drunker mockedDrunker2 = mock(Drunker.class);
    private PoliceStation testStation = new PoliceStation(stationCell);

    private Policeman policeman = null;

    @Before
    public void setUp() throws Exception {
        when(this.freeCell.getNeighbours()).thenReturn(Arrays.asList(this.stationCell));
        when(this.stationCell.getNeighbours()).thenReturn(Arrays.asList(this.freeCell));
        when(this.freeCell.isEmpty()).thenReturn(true);
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
        verify(this.freeCell, never()).putObject((FieldObject) anyObject());
        for (int i = 0; i < 100; ++i) {
            this.testStation.doTurn();
        }
        verify(this.freeCell, never()).putObject((FieldObject) anyObject());

        when(this.mockedDrunker.getPosition()).thenReturn(line.get(lineLength - 1));
        when(line.get(lineLength - 1).getObject()).thenReturn(this.mockedDrunker);
        when(line.get(lineLength - 1).isEmpty()).thenReturn(false);
        this.testStation.doTurn();
        verify(this.freeCell, never()).putObject((FieldObject) anyObject());
        when(this.mockedDrunker.isIllegal()).thenReturn(true);
        this.testStation.doTurn();
        verify(this.freeCell, never()).putObject((FieldObject) anyObject());
        when(line.get(lineLength - 1).isIlluminated()).thenReturn(true);
        this.testStation.doTurn();
        verify(this.freeCell, times(1)).putObject(argThat(new ArgumentMatcher<Policeman>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Policeman;
            }
        }));
    }

    @Test
    public void testDoTurn1() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        when(this.freeCell.getNeighbours()).thenReturn(Arrays.asList(this.stationCell, line.get(0)));
        when(line.get(0).getNeighbours()).thenReturn(Arrays.asList(this.freeCell, line.get(1)));
        when(this.mockedDrunker.getPosition()).thenReturn(line.get(lineLength - 1));
        when(line.get(lineLength - 1).getObject()).thenReturn(this.mockedDrunker);
        when(line.get(lineLength - 1).isEmpty()).thenReturn(false);
        when(this.mockedDrunker.isIllegal()).thenReturn(true);
        when(line.get(lineLength - 1).isIlluminated()).thenReturn(true);
        when(this.freeCell.isEmpty()).thenReturn(false);
        Drunker blockingDrunker = mock(Drunker.class);
        when(blockingDrunker.isIllegal()).thenReturn(false);
        when(this.freeCell.getObject()).thenReturn(blockingDrunker);

        this.testStation.doTurn();
        verify(this.freeCell, never()).putObject(argThat(new ArgumentMatcher<Policeman>() {
            @Override
            public boolean matches(Object argument) {
                return true;
            }
        }));
        when(blockingDrunker.isIllegal()).thenReturn(true);
        this.testStation.doTurn();
        verify(this.freeCell, times(1)).removeObject();
        when(this.freeCell.isEmpty()).thenReturn(true);
        this.testStation.doTurn();
        verify(this.freeCell, times(1)).putObject(argThat(new ArgumentMatcher<Policeman>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Policeman;
            }
        }));
    }

    @Test
    public void testDoTurn2() throws Exception {
        final int lineLength = 100;
        List<Cell> line = MockGraph.getLine(lineLength);
        when(this.freeCell.getNeighbours()).thenReturn(Arrays.asList(this.stationCell, line.get(0)));
        when(line.get(0).getNeighbours()).thenReturn(Arrays.asList(this.freeCell, line.get(1)));
        when(this.mockedDrunker.getPosition()).thenReturn(line.get(lineLength - 1));
        when(line.get(lineLength - 1).getObject()).thenReturn(this.mockedDrunker);
        when(line.get(lineLength - 1).isEmpty()).thenReturn(false);
        when(this.mockedDrunker.isIllegal()).thenReturn(true);
        when(line.get(lineLength - 1).isIlluminated()).thenReturn(true);

        when(line.get(lineLength / 2).isEmpty()).thenReturn(false);
        when(line.get(lineLength / 2).getObject()).thenReturn(mock(Wall.class));

        this.testStation.doTurn();
        verify(this.freeCell, never()).putObject(argThat(new ArgumentMatcher<Policeman>() {
            @Override
            public boolean matches(Object argument) {
                return true;
            }
        }));

        when(line.get(lineLength / 2).isEmpty()).thenReturn(true);
        when(line.get(lineLength / 2).getObject()).thenReturn(null);
        this.testStation.doTurn();
        verify(this.freeCell, times(1)).putObject(argThat(new ArgumentMatcher<Policeman>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Policeman;
            }
        }));
    }

    @Test
    public void testDoTurn3() throws Exception {
        final int line1Length = 100;
        final int line2Length = 50;
        List<Cell> line1 = MockGraph.getLine(line1Length);
        List<Cell> line2 = MockGraph.getLine(line2Length);
        when(this.freeCell.getNeighbours()).thenReturn(Arrays.asList(this.stationCell, line1.get(0), line2.get(0)));
        when(line1.get(0).getNeighbours()).thenReturn(Arrays.asList(this.freeCell, line1.get(1)));
        when(line2.get(0).getNeighbours()).thenReturn(Arrays.asList(this.freeCell, line2.get(1)));
        when(this.mockedDrunker.getPosition()).thenReturn(line1.get(line1Length - 1));
        when(this.mockedDrunker2.getPosition()).thenReturn(line2.get(line2Length - 1));
        when(line1.get(line1Length - 1).getObject()).thenReturn(this.mockedDrunker);
        when(line1.get(line1Length - 1).isEmpty()).thenReturn(false);
        when(line2.get(line2Length - 1).getObject()).thenReturn(this.mockedDrunker2);
        when(line2.get(line2Length - 1).isEmpty()).thenReturn(false);
        when(this.mockedDrunker.isIllegal()).thenReturn(true);
        when(this.mockedDrunker2.isIllegal()).thenReturn(true);
        when(line1.get(line1Length - 1).isIlluminated()).thenReturn(true);
        when(line2.get(line2Length - 1).isIlluminated()).thenReturn(true);
        when(stationCell.distTo(line1.get(line1Length - 1))).thenReturn(101);
        when(stationCell.distTo(line2.get(line2Length - 1))).thenReturn(51);

        doAnswer(new Answer<Policeman>() {
            public Policeman answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                PoliceStationTest.this.policeman = (Policeman) args[0];
                return null;
            }
        }).when(freeCell).putObject(argThat(new ArgumentMatcher<Policeman>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Policeman;
            }
        }));

        this.testStation.doTurn();
        verify(this.freeCell, times(1)).putObject(argThat(new ArgumentMatcher<Policeman>() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof Policeman;
            }
        }));
        assertNotNull(this.policeman);

        this.policeman.doTurn(); //Здесь должен быть ход полицейского

        verify(line2.get(0), times(1)).putObject(this.policeman);

        verify(line1.get(0), never()).putObject(this.policeman);
    }


    @Test
    public void testGetSymbolRepresentation() throws Exception {
        assertEquals('П', this.testStation.getSymbolRepresentation());
    }
}
