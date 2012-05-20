package ru.spbau.shestavin.drunkers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Classname:
 * User: dimatwl
 * Date: 5/16/12
 * Time: 1:38 PM
 */
public class MockGraph {
    public static List<Cell> getLine(int inpVertexNum) {
        List<Cell> line = new ArrayList<Cell>();
        line.add(mock(Cell.class));
        line.add(mock(Cell.class));
        List<Cell> neighboursForFirst = new ArrayList<Cell>();
        neighboursForFirst.add(line.get(1));
        when(line.get(0).getNeighbours()).thenReturn(neighboursForFirst);
        when(line.get(0).isEmpty()).thenReturn(true);
        for (int i = 1; i < inpVertexNum - 1; ++i) {
            line.add(mock(Cell.class));
            List<Cell> neighbours = new ArrayList<Cell>();
            neighbours.add(line.get(i + 1));
            neighbours.add(line.get(i - 1));
            when(line.get(i).getNeighbours()).thenReturn(neighbours);
            when(line.get(i).isEmpty()).thenReturn(true);
        }
        List<Cell> neighboursForLast = new ArrayList<Cell>();
        neighboursForLast.add(line.get(inpVertexNum - 2));
        when(line.get(inpVertexNum - 1).getNeighbours()).thenReturn(neighboursForLast);
        when(line.get(inpVertexNum - 1).isEmpty()).thenReturn(true);
        return line;
    }

    public static List<Cell> getOneWayCircle(int inpVertexNum) {
        List<Cell> circle = new ArrayList<Cell>();
        circle.add(mock(Cell.class));
        when(circle.get(0).isEmpty()).thenReturn(true);
        for (int i = 0; i < inpVertexNum - 1; ++i) {
            circle.add(mock(Cell.class));
            when(circle.get(i).getNeighbours()).thenReturn(Arrays.asList(circle.get(i + 1)));
            when(circle.get(i).isEmpty()).thenReturn(true);
        }
        when(circle.get(inpVertexNum - 1).getNeighbours()).thenReturn(Arrays.asList(circle.get(0)));
        when(circle.get(inpVertexNum - 1).isEmpty()).thenReturn(true);
        return circle;
    }
}
